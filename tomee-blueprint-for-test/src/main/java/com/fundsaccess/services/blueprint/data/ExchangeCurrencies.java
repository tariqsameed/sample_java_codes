package com.fundsaccess.services.blueprint.data;

public class ExchangeCurrencies {

	private String timePeriod;
	private String obsValue;
	private String unit;
	private String bbkDiff;
	
	public ExchangeCurrencies() {
		
	}
	
	
	public ExchangeCurrencies(String timePeriod, String obsValue, String unit, String bbkDiff) {
		super();
		this.timePeriod = timePeriod;
		this.obsValue = obsValue;
		this.unit = unit;
		this.bbkDiff = bbkDiff;
	}


	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	public String getObsValue() {
		return obsValue;
	}
	public void setObsValue(String obsValue) {
		this.obsValue = obsValue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBbkDiff() {
		return bbkDiff;
	}
	public void setBbkDiff(String bbkDiff) {
		this.bbkDiff = bbkDiff;
	}


}
