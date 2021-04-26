package com.franmoyang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.franmoyang.services.PriceHandlerService;
import com.franmoyang.services.PriceListenerService;

@Configuration
@SpringBootApplication
@ImportResource("application-context.xml")
public class Application implements CommandLineRunner {

	@Autowired
	private PriceListenerService priceListenerService;

	@Autowired
	private PriceHandlerService priceHandlerService;

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {

	}

	public void stop() {
		priceHandlerService.stop();
		System.exit(0);
	}

	public PriceListenerService getPriceListenerService() {
		return priceListenerService;
	}

	public PriceHandlerService getPriceHandlerService() {
		return priceHandlerService;
	}

}