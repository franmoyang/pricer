package com.franmoyang.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.franmoyang.model.Price;

@Service
public class PriceHandlerServiceImpl implements PriceHandlerService, Runnable {

	public static final String NEWLINE = System.getProperty("line.separator");

	/**
	 * Concurrent Queue for prices.
	 */
	protected ConcurrentLinkedQueue<String> _queue = new ConcurrentLinkedQueue<String>();

	/**
	 * Thread for events.
	 */
	protected Thread worker;

	protected final AtomicBoolean running = new AtomicBoolean(false);

	/**
	 * Logger.
	 */
	final static Logger logger = Logger.getLogger(PriceHandlerServiceImpl.class);

	/**
	 * Concurrent hashMap with the last prices for currencies.
	 */
	protected ConcurrentHashMap<String, Price> lastPrices = new ConcurrentHashMap<String, Price>();

	@Autowired
	public PriceHandlerServiceImpl(){
		worker = new Thread();
		if(logger.isDebugEnabled())
			logger.debug("PriceHandlerServiceImpl: Starting the thread...");
		start();
	}

	@Override
	public void start() {
		worker = new Thread(this);
		running.set(true);
        worker.start();
	}

	@Override
	public void stop() {
		running.set(false);
	}

	@Override
	public void run() {
		while(running.get()) {
			if(logger.isDebugEnabled())
				logger.debug("PriceHandlerServiceImpl.run: Thread running...");
			if (!_queue.isEmpty()){
				// Get the message.
				if(logger.isInfoEnabled())
					logger.info("PriceHandlerServiceImpl.run: Get the message.");
				addPricesFromMessage (_queue.poll());
			}
		}
	}

	private void addPricesFromMessage (String message) {

		try {
			String[] lines = message.split(NEWLINE);
			String[] line;
			Price price;
			long _id;
			String currency, _timestamp;
			BigDecimal bid, ask;

			BigDecimal bidMultiplier = BigDecimal.valueOf(0.999);
			BigDecimal askMultiplier = BigDecimal.valueOf(1.001);

			for (int i=0;i<lines.length;i++) {
				line = lines[i].split(",");
				if(logger.isInfoEnabled())
					logger.info("PriceHandlerServiceImpl.addPricesFromMessage: Reading line: " + line);
				try{
					_id = Long.valueOf(line[Price._ID]);
					currency = line[Price.CURRENCY];
					// Read the bid/ask as BigDecimal.
					bid = BigDecimal.valueOf(Double.valueOf(line[Price.BID]));
					// 4 decimals precision, rounding even. 110.35451 --> 110.3545 : 110.35456 --> 110.3546
					bid = bid.multiply(bidMultiplier).setScale(Price.DECIMAL_PRECISION, RoundingMode.HALF_EVEN);
					// Same for ask one.
					ask = BigDecimal.valueOf(Double.valueOf(line[Price.ASK]));
					ask = ask.multiply(askMultiplier).setScale(Price.DECIMAL_PRECISION, RoundingMode.HALF_EVEN);
					_timestamp = line[Price._TIMESTAMP];
					price = new Price(_id, currency, bid, ask, _timestamp);
					// Insert the last price.
					lastPrices.put(currency, price);
				} catch(Exception e){
					logger.error("PriceHandlerServiceImpl.addPricesFromMessage" + e.toString());
				}
			}
		}
		catch(Exception e) {
			// Do nothing.
		}
	}

	@Override
	public void addMessage (String message) {
		_queue.add(message);
	}

	@Override
	public Price getPrice(String currency) {
		while(!_queue.isEmpty()) {
			addPricesFromMessage (_queue.poll());
		}
		return lastPrices.get(currency);
	}

	@Override
	public ArrayList<Price> getAllPrices() {
		while(!_queue.isEmpty()) {
			addPricesFromMessage (_queue.poll());
		}
		Collection<Price> allPrices = lastPrices.values();
		return new ArrayList<Price>(allPrices);
	}

}
