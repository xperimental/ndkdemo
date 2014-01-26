package net.sourcewalker.ndkdemo.calc;

import android.os.Handler;

import net.sourcewalker.ndkdemo.Constants;

public class NativeCalculation implements Calculation {

    private final int limit;

    public NativeCalculation(int limit) {
        this.limit = limit;
    }

    @Override
    public void start(final Handler statusHandler) {
        Thread calcThread = new Thread("NativeCalculation") {
            @Override
            public void run() {
                statusHandler.sendEmptyMessage(Constants.MSG_START);
                int primeCount = countPrimes(limit);
                String msg = String.format("%d primes found.", primeCount);
                statusHandler.obtainMessage(Constants.MSG_STATUS, msg).sendToTarget();
                statusHandler.sendEmptyMessage(Constants.MSG_END);
            }
        };
        calcThread.start();
    }

    public native int countPrimes(int limit);

    static {
        System.loadLibrary("x86-demo");
    }

}
