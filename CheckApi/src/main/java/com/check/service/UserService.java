package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.rest.error.UnautharizedException;
import main.java.com.check.rest.error.ValidationException;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(LoginInfo loginInfo) throws ValidationException, UnautharizedException;

    void register(RegisterInfo registerInfo) throws ValidationException;

    void sendRecoverPassword(RecoverInfo recoverInfo) throws ValidationException;
}
