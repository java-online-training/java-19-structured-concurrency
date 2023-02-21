package jot.concurrency;

public class CurrencyService {

    enum Currency { USD , EUR , JPY , CAD , GBP  };

    public double getConvertionRate(Currency currency){
        try {
            Thread.sleep(500l);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return Math.random();
    }
    
}
