package jot.concurrency;

import jot.concurrency.CurrencyService.Currency;


public record ConversionResult (Double amount, Currency target) {

    @Override
    public String toString(){
        return "Converted value for "+ target + "  is "+ amount;
    }

}
