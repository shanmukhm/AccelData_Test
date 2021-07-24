package com.acceldata.jms;

import com.acceldata.dto.KeyValue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

//@Component
public class EventListener {

    @JmsListener(destination = "create", containerFactory = "myFactory")
    public void createKVPair(KeyValue keyValue) {
        System.out.println("Received <" + keyValue + ">");
    }

    @JmsListener(destination = "update", containerFactory = "myFactory")
    public void updateKVPair(KeyValue keyValue) {
        System.out.println("Received <" + keyValue + ">");
    }

    @JmsListener(destination = "delete", containerFactory = "myFactory")
    public void deleteKVPair(KeyValue keyValue) {
        System.out.println("Received <" + keyValue + ">");
    }

}
