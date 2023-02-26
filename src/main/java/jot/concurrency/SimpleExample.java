package jot.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;

public class SimpleExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope<String>()){
            Callable<String> callable = () -> "Running ...";

            Future<String> result = scope.fork(callable);
            
            scope.join();
            
            System.out.println(result.get());
        }

        
    }
    
}
