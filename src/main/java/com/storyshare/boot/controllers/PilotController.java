package com.storyshare.boot.controllers;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.ResourceBundle;

@RestController
public class PilotController {
    @GetMapping(value = "/locale", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getLocale(@RequestParam("locale") String locale) {
        ResourceBundle rb;
        JsonObject json = new JsonObject();

        if ("ru".equals(locale)) {
            rb = ResourceBundle.getBundle("localization_ru");
        } else if ("en".equals(locale)) {
            rb = ResourceBundle.getBundle("localization_en");
        } else {
            rb = ResourceBundle.getBundle("localization");
        }

        Enumeration<String> listOfKeys = rb.getKeys();
        String nextElement;
        while (listOfKeys.hasMoreElements()) {
            nextElement = listOfKeys.nextElement();
            json.addProperty(nextElement, rb.getString(nextElement));
        }

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}