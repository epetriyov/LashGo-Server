package main.java.com.check.rest.controller;

import main.java.com.check.core.domain.Users;
import main.java.com.check.core.service.UserService;
import main.java.com.check.rest.dto.LoginInfo;
import main.java.com.check.rest.error.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    Users login(@RequestBody LoginInfo loginInfo) throws Exception {
        Users user = userService.login(loginInfo);
        if (user == null) {
            throw new Exception(ErrorCodes.USER_NOT_FOUND);
        } else {
            return user;
        }
    }

    @RequestMapping("/register")
    public
    @ResponseBody
    void register(@RequestBody LoginInfo loginInfo) throws Exception {
        userService.register(loginInfo);
    }
}
