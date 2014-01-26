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
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                statusHandler.sendEmptyMessage(Constants.MSG_END);
            }
        };
        calcThread.start();
    }

}
