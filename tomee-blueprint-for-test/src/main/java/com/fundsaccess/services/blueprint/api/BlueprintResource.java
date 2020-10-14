package com.fundsaccess.services.blueprint.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fundsaccess.services.blueprint.data.ExchangeCurrencies;
import com.fundsaccess.services.blueprint.data.Person;
import com.fundsaccess.services.blueprint.data.parser.DataParser;
import com.fundsaccess.services.blueprint.database.CurrencyDataEntity;
import com.fundsaccess.services.blueprint.database.H2MemoryDatabaseExample;

/**
 * A sample REST resource.
 */
@Path("blueprint")
@RequestScoped
public class BlueprintResource {
	
	private static Logger logger = LoggerFactory.getLogger(BlueprintResource.class);
	DataParser parser = DataParser.getParser();
    /**
     * A sample rest endpoint. Returns an empty array.
     *
     * @return exchange currencies
     */
	@GET
	@Path("/blueprints1")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ExchangeCurrencies> getBlueprints() {
		List<ExchangeCurrencies> currencies = new ArrayList<ExchangeCurrencies>();
		try {
			currencies = new CurrencyDataEntity().getAllRecords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currencies;
//        return parser.getExchangeCurrencies();
    }
	
	@GET
	@Path("/currencies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllCurrenciesType() {
      //  return Arrays.asList(1,2,3);
	 //	return parser.getExchangeCurrenciesMap();		
		List<String> allType = new CurrencyDataEntity().getAllEuroCurrenyType();
		return allType;
	}
    
	@GET
	@Path("/currencies/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExchangeCurrencies> getCurrenciesAll() {
       // return parser.getExchangeCurrencies();
		List<ExchangeCurrencies> currencies = new ArrayList<ExchangeCurrencies>();
		try {
			currencies = new CurrencyDataEntity().getAllRecords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currencies;
    }
  
	@GET
	@Path("/currencies/date")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCurrenciesDate(@QueryParam("date") String date) {
		logger.info("data requested for date: "+date);
		List<String> allDate = new CurrencyDataEntity().getCurrentDateExchange(date);
		return allDate;
    }
	
	@GET
	@Path("/currencies/convert")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCurrencyConvert(@QueryParam("amount") String amount, @QueryParam("currency") String currency, @QueryParam("date") String date) {
//		System.out.println("amount testing "+amount);
//		System.out.println("currency testing "+currency);
//		return Arrays.asList(10,11,12, amount, currency);
		List<String> allValue = new CurrencyDataEntity().getEuroCurrentDateExchange(amount, date, currency);
		return allValue;
    }
  

}
