package com.franmoyang.services;

import java.util.ArrayList;

import com.franmoyang.model.Price;

public interface PriceHandlerService {

	public void addMessage (String message);

	public Price getPrice (String currency);

	public ArrayList<Price> getAllPrices ();

	public void start();

	public void stop();

}
