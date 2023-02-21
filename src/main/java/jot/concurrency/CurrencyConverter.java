package jot.concurrency;

import static jot.concurrency.CurrencyService.Currency.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;

public class CurrencyConverter {

    private CurrencyService currencyService = new CurrencyService();

    public void getAllCurrencyConversions() throws InterruptedException, ExecutionException{

        try(var scope = new StructuredTaskScope<Double>()){

            List<Future<Double>> results = new ArrayList<>();

            results.add(scope.fork( () -> currencyService.getConvertionRate(USD) ));

            results.add(scope.fork( () -> currencyService.getConvertionRate(JPY) ));

            results.add(scope.fork( () -> currencyService.getConvertionRate(EUR) ));

            results.add(scope.fork( () -> currencyService.getConvertionRate(CAD) ));

            results.add(scope.fork( () -> currencyService.getConvertionRate(GBP) ));

            scope.join();

            for(Future f : results){
                System.out.println("Conversion rate: "+f.get());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        var conv = new CurrencyConverter();
        conv.getAllCurrencyConversions();
    }
}
