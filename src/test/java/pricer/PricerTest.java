package pricer;

import java.util.ArrayList;

import com.franmoyang.model.Price;
import com.franmoyang.services.PriceHandlerService;
import com.franmoyang.services.PriceHandlerServiceImpl;
import com.franmoyang.services.PriceListenerService;
import com.franmoyang.services.PriceListenerServiceImpl;

public class PricerTest {

	public static final String NEWLINE = System.getProperty("line.separator");

	public static void main(String[] args) throws InterruptedException {

		// Listener and handler services.
		PriceHandlerService priceHandlerService = new PriceHandlerServiceImpl();
		PriceListenerService priceListenerService = new PriceListenerServiceImpl(priceHandlerService);

		// Message
		String message ="106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001" + NEWLINE +
			"107,EUR/JPY,119.60,119.90,01-06-2020 12:01:01:002" + NEWLINE +
			"108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:01:002" + NEWLINE +
			"109,GBP/USD,1.2499,1.2561,01-06-2020 12:01:01:100" + NEWLINE +
			"110,EUR/JPY,119.61,119.91,01-06-2020 12:01:01:110" + NEWLINE +
			"111,EUR/USD,1.1050,1.2004,01-06-2020 12:01:01:201" + NEWLINE +
			"112,GBP/USD,1.2496,1.2541,01-06-2020 12:01:01:220";

		try {
			// onMessageEvent.
			priceListenerService.onMessage(message);
			// get all prices.
			ArrayList<Price> prices = priceHandlerService.getAllPrices();
			Price price;
			for(int i = 0;i < prices.size();i++) {
				price = prices.get(i);
				System.out.println("ID " + price.getId() + " Currency " + price.getCurrency() + " Bid - Ask " + price.getBid() + " " + price.getAsk());
			}
			priceHandlerService.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
