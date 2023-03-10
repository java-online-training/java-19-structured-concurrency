package jot.concurrency.advanced;

import jot.concurrency.CurrencyConversionService;
import jot.concurrency.CurrencyConversionService.Currency;

/**
 * Result of the conversion of amount EUR into target currency
 */
public record ConversionResult (Double amount, Currency target, Double euroAmount) {

    @Override
    public String toString(){
        return "Converted value for "+ target + "  is "+ amount;
    }

}
