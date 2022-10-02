package TickerInfo;

import com.opencsv.bean.CsvBindByName;

public class Ticker {

    @CsvBindByName(column = "Symbol")
    private String ticker;

//    @CsvBindByName(column = "Name")
//    private String companyName;

    @CsvBindByName(column = "Market Cap")
    private double marketCap;

//    @CsvBindByName(column = "Volume")
//    private long volume;

    public String getTicker() {
        return ticker;
    }

    public long getMarketCap() {
        return (long) marketCap;
    }
}
