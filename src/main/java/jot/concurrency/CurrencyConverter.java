package jot.concurrency;

import static jot.concurrency.CurrencyService.Currency.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;
import jot.concurrency.CurrencyService.Currency;
import jot.concurrency.advanced.CurrencyConverterScope;

public class CurrencyConverter {

    private CurrencyService currencyService = new CurrencyService();

    public void getCurrencyConversionRates() throws InterruptedException, ExecutionException{

        try(var scope = new StructuredTaskScope<Double>()){

            Map<Currency,Future<Double>> results = new HashMap<>();

            results.put(USD, scope.fork( () -> currencyService.getConvertionRate(USD) ));

            results.put(JPY, scope.fork( () -> currencyService.getConvertionRate(JPY) ));

            results.put(EUR, scope.fork( () -> currencyService.getConvertionRate(EUR) ));

            results.put(CAD, scope.fork( () -> currencyService.getConvertionRate(CAD) ));

            results.put(GBP, scope.fork( () -> currencyService.getConvertionRate(GBP) ));

            scope.join();

            for(Currency currency : results.keySet()) {
                System.out.println("Conversion rate for "+currency + " is "+results.get(currency).get() );
            }
        }
    }

    public void getCurrencyConversions() throws InterruptedException, ExecutionException{

        try(var scope = new CurrencyConverterScope()){


            scope.fork( () -> currencyService.getConvertionResult(100d, USD) );

            scope.fork( () -> currencyService.getConvertionResult(100d,JPY) );

            scope.fork( () -> currencyService.getConvertionResult(100d,EUR) );

            scope.fork( () -> currencyService.getConvertionResult(100d,CAD) );

            scope.fork( () -> currencyService.getConvertionResult(100d,GBP) );

            scope.join();

            for(ConversionResult result : scope.getResults()) {
                System.out.println(result);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        var conv = new CurrencyConverter();

        conv.getCurrencyConversionRates();
        System.out.println("-----------------------------");
        conv.getCurrencyConversions();
    }
}
