package com.lashgo.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Eugene on 13.05.2014.
 */
@Controller
@RequestMapping("/doc")
public class JsonDocUiController{

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        return "jsondoc";
    }

}