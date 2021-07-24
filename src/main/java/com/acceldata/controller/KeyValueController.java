package com.acceldata.controller;

import com.acceldata.dto.AccelResponse;
import com.acceldata.dto.KeyValue;
import com.acceldata.dto.KeyValueRequest;
import com.acceldata.exception.AccelDataException;
import com.acceldata.exception.AccelDataInvalidRequestException;
import com.acceldata.exception.AccelDataNotFoundException;
import com.acceldata.service.KeyValueService;
import com.acceldata.service.RequestQueueService;
import com.acceldata.service.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("data-store")
public class KeyValueController {

    @Autowired
    private KeyValueService keyValueService;

    @Autowired
    private RequestQueueService queueService;

    @PostMapping(path = "/create")
    public KeyValue createKeyValue(@RequestBody KeyValue keyValue) throws AccelDataException, IOException {
//        return keyValueService.create(keyValue);
        return queueService.add(new KeyValueRequest(RequestType.CREATE, keyValue, null));
    }

    @GetMapping(path = "/get")
    public KeyValue getValue(@RequestParam String key) throws AccelDataException {
//        return keyValueService.get(key);
        return queueService.add(new KeyValueRequest(RequestType.GET, null, key));
    }

    @PutMapping(path = "/update")
    public KeyValue updateKeyValue(@RequestBody KeyValue keyValue) throws AccelDataException {
//        return keyValueService.update(keyValue);
        return queueService.add(new KeyValueRequest(RequestType.UPDATE, keyValue, null));
    }

    @DeleteMapping(path = "/delete")
    public AccelResponse deleteKeyValue(@RequestParam String key) throws AccelDataException {
//        return keyValueService.delete(key);
         queueService.add(new KeyValueRequest(RequestType.DELETE, null, key));
        return new AccelResponse("Deleted successfully!", true, 200);
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND,
            reason="Resource not found")  // 404
    @ExceptionHandler(AccelDataNotFoundException.class)
    public ModelAndView notFound(HttpServletRequest req, Exception ex) {
        return getModelView(req, ex);
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST,
            reason="Bad request")  // 400
    @ExceptionHandler(AccelDataInvalidRequestException.class)
    public ModelAndView badRequest(HttpServletRequest req, Exception ex) {
        return getModelView(req, ex);
    }

    private ModelAndView getModelView(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("reason", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
