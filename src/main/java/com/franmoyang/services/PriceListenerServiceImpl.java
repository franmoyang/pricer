package com.franmoyang.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListenerServiceImpl implements PriceListenerService {

	@Autowired
	protected PriceHandlerService priceHandlerService;

	public PriceListenerServiceImpl(PriceHandlerService priceHandlerService){
		this.priceHandlerService = priceHandlerService;
	}

	@Override
	public void onMessage(String message) {
		priceHandlerService.addMessage(message);
	}

}
