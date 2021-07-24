package com.acceldata.service;

import com.acceldata.dto.AccelResponse;
import com.acceldata.dto.KeyValue;
import com.acceldata.exception.AccelDataException;
import com.acceldata.exception.AccelDataInvalidRequestException;
import com.acceldata.exception.AccelDataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;

@Service
public class KeyValueService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    private final HashMap<String, String> cache = new HashMap<>();

    public KeyValue create(KeyValue keyValue) throws AccelDataException {
        log.info("Creating a key value pair..");
        if(cache.containsKey(keyValue.getKey())) {
            throw new AccelDataInvalidRequestException("Key {" + keyValue.getKey() + "}  already exists!");
        }
        cache.put(keyValue.getKey(), keyValue.getValue());
        fileService.addEntry(keyValue);
        log.info("Created a key value pair successfully.");
        return keyValue;
    }

    public KeyValue get(String key) throws AccelDataException {
        log.info("getting a key value pair for key: " + key);
        if(!cache.containsKey(key)) {
            throw new AccelDataNotFoundException("Key {" + key + "} does not exist!");
        }

        log.info("Got a key value pair successfully.");
        return new KeyValue(key, cache.get(key));
    }

    public KeyValue update(KeyValue keyValue) throws AccelDataException {
        log.info("Updating a key value pair..");
        if(!cache.containsKey(keyValue.getKey())) {
            throw new AccelDataInvalidRequestException("Key {" + keyValue.getKey() + "}  does not exist!");
        }
        cache.put(keyValue.getKey(), keyValue.getValue());
        fileService.addEntry(keyValue);
        log.info("Updated a key value pair.");
        return keyValue;
    }

    public AccelResponse delete(String key) throws AccelDataException {
        log.info("Deleting a key value pair...");
        if(!cache.containsKey(key)) {
            throw new AccelDataNotFoundException("Key {" + key + "} does not exist!");
        }
        cache.remove(key);
        fileService.addEntry(new KeyValue(key, null));
        log.info("Deleted a key value pair.");
        return new AccelResponse("Deleted successfully!", true, 200);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void reloadCache() {
        log.info("Loading the cache from file...");
        fileService.reloadCache(cache);
        log.info("Loaded the cache successfully.");
    }
}
