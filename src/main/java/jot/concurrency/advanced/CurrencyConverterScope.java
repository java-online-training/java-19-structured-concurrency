package jot.concurrency.advanced;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

import jdk.incubator.concurrent.StructuredTaskScope;
import jot.concurrency.ConversionResult;

public class CurrencyConverterScope extends StructuredTaskScope<ConversionResult> {

    Queue<ConversionResult> queue = new ConcurrentLinkedQueue<ConversionResult>();

    @Override
    protected void handleComplete(Future<ConversionResult> future) {
        if (future.isDone()){
            try {
                queue.add(future.get());
            } catch (Exception e) {
                System.out.println("Exception converting.");
            }
        }
    }

    public Queue<ConversionResult> getResults(){ return queue;}
}
