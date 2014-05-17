package main.java.com.check.service;

import com.check.model.dto.*;
import main.java.com.check.rest.error.UnautharizedException;
import main.java.com.check.rest.error.ValidationException;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(String interfaceTypeCode, LoginInfo loginInfo) throws ValidationException, UnautharizedException;

    void register(RegisterInfo registerInfo) throws ValidationException;

    void sendRecoverPassword(RecoverInfo recoverInfo) throws ValidationException;

    UserDto getProfile(String sessionId);

    PhotoDtoList getPhotos(String sessionId);

    SubscriptionDtoList getSubscriptions(String sessionId);

    void unsubscribe(String sessionId, int userId);

    void subscribe(String sessionId, int userId);
}
