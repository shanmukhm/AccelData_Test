package com.acceldata.service;

import com.acceldata.dto.KeyValue;
import com.acceldata.dto.KeyValueRequest;
import com.acceldata.exception.AccelDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class RequestQueueService {
    @Autowired
    private KeyValueService keyValueService;

    private final HashMap<KeyValueRequest, KeyValue> responseMap = new HashMap<>();

    private final BlockingDeque<KeyValueRequest> requestsQ = new LinkedBlockingDeque<>(1);

    public KeyValue add(KeyValueRequest request) throws AccelDataException {
        boolean res = requestsQ.add(request);
        while(!res) {
            res = requestsQ.add(request);
        }
        processRequests();
        boolean resExists = responseMap.containsKey(request);
        while (!resExists) {
            resExists = responseMap.containsKey(request);
        }
        KeyValue keyValue = responseMap.get(request);
        responseMap.remove(request);
        return keyValue;
    }

    public void processRequests() throws AccelDataException {
        while (!requestsQ.isEmpty()) {
            KeyValueRequest request = requestsQ.poll();
            KeyValue keyValue;
            switch (request.getRequestType()) {
                case CREATE:
                    keyValue = keyValueService.create(request.getKeyValue());
                    break;
                case UPDATE:
                    keyValue = keyValueService.update(request.getKeyValue());
                    break;
                case DELETE:
                    keyValueService.delete(request.getKey());
                    keyValue = new KeyValue(request.getKey(), null);
                    break;
                case GET:
                    keyValue = keyValueService.get(request.getKey());
                    break;
                default:
                    keyValue = new KeyValue("", "");
                    break;
            }

            responseMap.put(request, keyValue);
        }
    }
}
