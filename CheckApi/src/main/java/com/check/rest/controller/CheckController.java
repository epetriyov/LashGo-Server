package main.java.com.check.rest.controller;

import com.check.model.dto.CheckDto;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.Response;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 14.04.2014.
 */
@Controller
public class CheckController {

    @Autowired
    private CheckService checkService;

    @RequestMapping(value = "/checks", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CheckDtoList> getChecks() {
        return new Response<>(checkService.getChecks());
    }

    @RequestMapping(value = "/checks", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<CheckDto> getCurrentCheck(@RequestParam("current") boolean current) {
        if (current) {
            return new Response<>(checkService.getCurrentCheck());
        }
        throw new ValidationException(ErrorCodes.WRONG_CURRENT_CHECK_REQUEST);
    }
}
