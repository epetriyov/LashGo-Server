package main.java.com.check.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 12.05.2014.
 */
@Controller
public class DocumentationController {

    @RequestMapping(value = "/doc", method = RequestMethod.GET)
    public
    @ResponseBody
    String getDoc(ModelMap modelMap) {
        return "doc";

    }
}
