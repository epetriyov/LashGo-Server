package com.lashgo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lashgo.CheckConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Created by Eugene on 24.04.2014.
 */
@Component
public class LashGoObjectMapper extends ObjectMapper {

    public LashGoObjectMapper() {
        super();
        configure(SerializationFeature.
                WRITE_DATES_AS_TIMESTAMPS, false);
        setDateFormat(new SimpleDateFormat(CheckConstants.DATE_FORMAT));
    }
}
