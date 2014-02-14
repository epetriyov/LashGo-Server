package main.java.com.check.rest.controller;

import main.java.com.check.core.service.UserService;
import main.java.com.check.rest.dto.LoginInfo;
import main.java.com.check.rest.error.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public
    @ResponseBody
    ResponseEntity<Void> login(@RequestHeader HttpHeaders requestHeaders, @RequestBody LoginInfo loginInfo) throws Exception {
        List<String> uuidHeaders = requestHeaders.get(CheckApiHeaders.UUID);
        String sessionId = userService.login(loginInfo, uuidHeaders.get(0));
        if (sessionId == null) {
            throw new Exception(ErrorCodes.USER_NOT_FOUND);
        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(CheckApiHeaders.SESSION_ID, sessionId);
            return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
        }
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    void register(@RequestBody LoginInfo loginInfo) throws Exception {
        userService.register(loginInfo);
    }
}
