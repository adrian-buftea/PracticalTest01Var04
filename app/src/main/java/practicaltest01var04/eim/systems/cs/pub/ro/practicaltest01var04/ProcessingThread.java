package practicaltest01var04.eim.systems.cs.pub.ro.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Denis on 02/04/2018.
 */

class ProcessingThread extends Thread {

    private Context context = null;
    private double arithmeticMean = 0;
    private double geometricMean = 0;
    private boolean isRunning = true;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        arithmeticMean = (firstNumber + secondNumber) / 2;
        geometricMean = Math.sqrt(firstNumber * secondNumber);
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("medii");
        intent.putExtra("message", arithmeticMean + " " + geometricMean);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
