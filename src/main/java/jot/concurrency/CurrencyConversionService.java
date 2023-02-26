package jot.concurrency;

import jot.concurrency.advanced.ConversionResult;

public class CurrencyConversionService {

    public Double getConvertionRate(Currency currency) throws InterruptedException{
        Thread.sleep(500l);
        return currency.getRate();
    }

    public ConversionResult getEuroConvertionResult(Double amount, Currency target) throws InterruptedException {
        return new ConversionResult(Double.valueOf(amount*getConvertionRate(target)), target, amount);
    }

    public enum Currency { 
        USD(1.2d) , EUR(1d) , JPY(144d) , CAD(1.44d) , GBP(0.88d); 
        private Double rate;

        private Currency(Double rate){ this.rate = rate;}

        public Double getRate(){ return rate; }
    };
}