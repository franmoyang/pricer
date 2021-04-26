package com.franmoyang.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.franmoyang.model.Price;
import com.franmoyang.services.PriceHandlerService;

@RestController
public class PricePublisherController {

    @Autowired
    private PriceHandlerService priceHandlerService;

    PricePublisherController(PriceHandlerService priceHandlerService) {
        this.priceHandlerService = priceHandlerService;
    }

    @GetMapping("/getallprices")
    ArrayList<Price> all() {
    	return priceHandlerService.getAllPrices();
    }
    // All prices.

    @GetMapping("/getprice/{id}")
    Price one(@PathVariable String currency) {
      return priceHandlerService.getPrice(currency);
    }
    // Single price.

}
