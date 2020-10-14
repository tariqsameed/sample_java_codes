package com.fundsaccess.services.blueprint.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fundsaccess.services.blueprint.api.BlueprintResource;
import com.fundsaccess.services.blueprint.data.ExchangeCurrencies;
import com.fundsaccess.services.blueprint.data.Person;
import com.fundsaccess.services.blueprint.data.parser.DataParser;

// H2 In-Memory Database Example shows about storing the database contents into memory.

public class CurrencyDataEntity {

	private static Logger logger = LoggerFactory.getLogger(CurrencyDataEntity.class);
	
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    
   
    private static final String TABLE_NAME = "ForeignCurrency";
    private static final String DATE_COL = "date";
    private static final String OBS_VALUE_COL = "obs_value";
    private static final String UNIT_COL = "unit";
    

	static DataParser parser = DataParser.getParser();
	private static Connection connection;
    static {
    	
        try {
            connection = getDBConnection();
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ExchangeCurrencies> getAllRecords() {
    	try {
            String SelectQuery = "select * from "+TABLE_NAME;

            PreparedStatement selectPreparedStatement = null;

            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();

            logger.info("Total Records Count:  "+rs.getFetchSize());
            List<ExchangeCurrencies> people = new ArrayList<ExchangeCurrencies>();
            while (rs.next()) {
                people.add(new ExchangeCurrencies(rs.getString(DATE_COL),rs.getString(OBS_VALUE_COL), rs.getString(UNIT_COL), ""));
            }
            selectPreparedStatement.close();
            return people;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    public List<String> getAllEuroCurrenyType(){
    	try {
        String SelectQuery = "select DISTINCT "+UNIT_COL+" from "+TABLE_NAME;
        
        PreparedStatement selectPreparedStatement = null;

        selectPreparedStatement = connection.prepareStatement(SelectQuery);
        ResultSet rs = selectPreparedStatement.executeQuery();
        List<String> currencyType = new ArrayList<String>();
        while (rs.next()) {
        	currencyType.add(rs.getString(UNIT_COL));
        }
        selectPreparedStatement.close();
        return currencyType;
        
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    public List<String> getCurrentDateExchange(String date){
    	try {
        String SelectQuery = "select "+OBS_VALUE_COL+","+UNIT_COL+" from "+TABLE_NAME+" WHERE "+DATE_COL+"='"+date+"'";
        
        logger.info("Select By Date Query: "+SelectQuery);
        
        PreparedStatement selectPreparedStatement = null;

        selectPreparedStatement = connection.prepareStatement(SelectQuery);
        ResultSet rs = selectPreparedStatement.executeQuery();
        logger.info("Getting Date Exchange "+rs.getFetchSize());
        List<String> currencyDate = new ArrayList<String>();
        while (rs.next()) {
        	currencyDate.add(rs.getString(OBS_VALUE_COL)+" "+rs.getString(UNIT_COL));
        }
        selectPreparedStatement.close();
        return currencyDate;
        
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }
    
    public List<String> getEuroCurrentDateExchange(String amount, String date, String currency){
    	try {
        String SelectQuery = "select "+OBS_VALUE_COL+","+UNIT_COL+" from "+TABLE_NAME+" WHERE "+DATE_COL+"='"+date+" and "+UNIT_COL+"='"+currency+"'";
        
        logger.info("Select By Date Query: "+SelectQuery);
        
        PreparedStatement selectPreparedStatement = null;

        selectPreparedStatement = connection.prepareStatement(SelectQuery);
        ResultSet rs = selectPreparedStatement.executeQuery();
        logger.info("Getting Date Exchange "+rs.getFetchSize());
        List<String> currencyDate = new ArrayList<String>();
        while (rs.next()) {
        	float value = (1 / Float.valueOf(rs.getString(OBS_VALUE_COL))) * (Float.valueOf(amount));
        	currencyDate.add(value+"");
        }
        selectPreparedStatement.close();
        return currencyDate;
        
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return Collections.emptyList();
    	}
    }

    private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;

        String CreateQuery = "CREATE TABLE "+TABLE_NAME+"(id int primary key, "+DATE_COL+" varchar(255), "+OBS_VALUE_COL+" varchar(255), "+UNIT_COL+" varchar(255))";
        String InsertQuery = "INSERT INTO "+TABLE_NAME + "(id, "+DATE_COL+", "+OBS_VALUE_COL+", "+UNIT_COL+") values" + "(?,?,?,?)";

        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(CreateQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();

            List<ExchangeCurrencies> all_currencies = parser.getExchangeCurrencies();
            int i = 0;
            for(ExchangeCurrencies currency:all_currencies) {
            	insertPreparedStatement = connection.prepareStatement(InsertQuery);
                
                insertPreparedStatement.setInt(1, i++);
                insertPreparedStatement.setString(2, currency.getTimePeriod());
                insertPreparedStatement.setString(3, currency.getObsValue());
                insertPreparedStatement.setString(4, currency.getUnit());
                insertPreparedStatement.executeUpdate();
                insertPreparedStatement.close();
                
//                if(i>=10) {
//                	break;
//                }
            }
            
            
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}