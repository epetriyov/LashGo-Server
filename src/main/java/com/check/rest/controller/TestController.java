package main.java.com.check.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 11.02.14.
 */

@Controller
public class TestController {
    @RequestMapping("/login")
    public
    @ResponseBody
    String hello() {
        return "Hello, World!";
    }
}
