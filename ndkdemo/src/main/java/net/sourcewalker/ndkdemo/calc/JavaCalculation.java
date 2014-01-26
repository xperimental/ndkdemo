package net.sourcewalker.ndkdemo.calc;

import android.os.Handler;

import net.sourcewalker.ndkdemo.Constants;

public class JavaCalculation implements Calculation {

    private final int limit;

    public JavaCalculation(int limit) {
        this.limit = limit;
    }

    @Override
    public void start(final Handler statusHandler) {
        Thread calcThread = new Thread("calculation") {
            @Override
            public void run() {
                statusHandler.sendEmptyMessage(Constants.MSG_START);
                calculate(statusHandler);
                statusHandler.sendEmptyMessage(Constants.MSG_END);
            }
        };
        calcThread.start();
    }

    private void calculate(Handler statusHandler) {
        boolean[] numbers = new boolean[limit + 1];
        numbers[0] = true;
        numbers[1] = true;
        numbers[2] = true;
        int p = 2;
        while (p < limit) {
            markNonPrimes(numbers, p);
            p = findNextPrime(numbers, p);
        }
        String primeList = getPrimes(numbers);
        statusHandler.obtainMessage(Constants.MSG_STATUS, primeList).sendToTarget();
    }

    private String getPrimes(boolean[] numbers) {
        if (limit > 1000) {
            int count = 0;
            for (boolean b : numbers) {
                if (!b) {
                    count++;
                }
            }
            return String.format("%d primes found.", count);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= limit; i++) {
                if (!numbers[i]) {
                    sb.append(i);
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    private void markNonPrimes(boolean[] numbers, int p) {
        for (int i = 2 * p; i <= limit; i += p) {
            numbers[i] = true;
        }
    }

    private int findNextPrime(boolean[] numbers, int p) {
        for (int i = p + 1; i <= limit; i++) {
            if (!numbers[i]) {
                return i;
            }
        }
        return limit;
    }

}
