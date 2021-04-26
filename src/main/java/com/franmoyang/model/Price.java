package com.franmoyang.model;

import java.math.BigDecimal;

/**
 * Price Model.
 * @author franmoyang
 *
 */
public class Price {

	/**
	 * Internal ID.
	 */
	protected long _id;

	/**
	 * Currency pair - USD/EUR
	 */
	protected String currency;

	/**
	 * Last bid value.
	 */
	protected BigDecimal bid;

	/**
	 * Last ask value.
	 */
	protected BigDecimal ask;

	/**
	 * Timestamp - value discarded.
	 */
	protected String _timestamp;

	/**
	 * Decimal precision
	 */
	public static int DECIMAL_PRECISION = 4;

	// Constants.
	public static final int _ID = 0;
	public static final int CURRENCY = 1;
	public static final int BID = 2;
	public static final int ASK = 3;
	public static final int _TIMESTAMP = 4;

	public Price (long _id, String currency, BigDecimal bid, BigDecimal ask, String _timestamp) {
		this._id = _id;
		this.currency = currency;
		this.bid = bid;
		this.ask = ask;
		this._timestamp = _timestamp;
	}

	public long getId () {
		return _id;
	}

	public void setId (long _id) {
		this._id = _id;
	}

	public String getCurrency () {
		return currency;
	}

	public void setCurrency (String currency) {
		this.currency = currency;
	}

	public BigDecimal getBid () {
		return bid;
	}

	public void setBid (BigDecimal bid) {
		this.bid = bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk (BigDecimal ask) {
		this.ask = ask;
	}

	public String getTimestamp (String _timestamp) {
		return _timestamp;
	}

	public void setTimestamp (String _timestamp) {
		this._timestamp = _timestamp;
	}

}
