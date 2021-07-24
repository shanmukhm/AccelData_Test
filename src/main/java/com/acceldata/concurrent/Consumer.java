package com.acceldata.concurrent;

import com.acceldata.dto.KeyValue;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<KeyValue> queue;

    public Consumer(BlockingQueue<KeyValue> q){
        this.queue=q;
    }

    @Override
    public void run() {
        try{
            //consuming messages until exit message is received
            while(true){
                Thread.sleep(10);
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

