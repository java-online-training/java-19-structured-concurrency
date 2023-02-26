package jot.concurrency;

import static jot.concurrency.CurrencyConversionService.Currency.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;
import jot.concurrency.CurrencyConversionService.Currency;
import jot.concurrency.advanced.ConversionResult;
import jot.concurrency.advanced.CurrencyConverterScope;

public class CurrencyConverter {

    private CurrencyConversionService currencyService = new CurrencyConversionService();

     /**
     * Example of structured concurrency with a StructuredTaskScope.
     */
    public void getCurrencyConversionRates() throws InterruptedException, ExecutionException{

        try(var scope = new StructuredTaskScope<Double>()){

            // map to store the results. Key Currency, Value convertion rate
            Map<Currency,Future<Double>> results = new HashMap<>();

            results.put(USD, scope.fork( () -> currencyService.getConvertionRate(USD) ));

            results.put(JPY, scope.fork( () -> currencyService.getConvertionRate(JPY) ));

            results.put(EUR, scope.fork( () -> currencyService.getConvertionRate(EUR) ));

            results.put(CAD, scope.fork( () -> currencyService.getConvertionRate(CAD) ));

            results.put(GBP, scope.fork( () -> currencyService.getConvertionRate(GBP) ));

            scope.join();

            for(Currency currency : results.keySet()) {
                System.out.println("Conversion rate for "+ currency + " is "+ results.get(currency).get() );
            }
        }
    }

    /**
     * Example of structured concurrency with a custom scope.
     */
    public void getCurrencyConversions() throws InterruptedException, ExecutionException{

        try(var scope = new CurrencyConverterScope()){

            scope.fork( () -> currencyService.getEuroConvertionResult(100d, USD) );

            scope.fork( () -> currencyService.getEuroConvertionResult(100d,JPY) );

            scope.fork( () -> currencyService.getEuroConvertionResult(100d,EUR) );

            scope.fork( () -> currencyService.getEuroConvertionResult(100d,CAD) );

            scope.fork( () -> currencyService.getEuroConvertionResult(100d,GBP) );

            scope.join();

            for(ConversionResult result : scope.getResults()) {
                System.out.println(result);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        var conv = new CurrencyConverter();

        // get the euro conversion rates
        conv.getCurrencyConversionRates();

        System.out.println("-----------------------------");
        
        // convert a speficic amount
        conv.getCurrencyConversions();
    }
}
