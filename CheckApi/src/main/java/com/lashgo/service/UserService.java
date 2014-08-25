package com.lashgo.service;

import com.lashgo.domain.Users;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.dto.*;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(String interfaceTypeCode, LoginInfo loginInfo) throws ValidationException, UnautharizedException;

    RegisterResponse register(String interfaceTypeCode, LoginInfo registerInfo) throws ValidationException;

    void sendRecoverPassword(RecoverInfo recoverInfo) throws ValidationException;

    UserDto getProfile(String sessionId);

    List<PhotoDto> getPhotos(String sessionId);

    List<SubscriptionDto> getSubscriptions(String sessionId);

    void unsubscribe(String sessionId, int userId);

    void subscribe(String sessionId, int userId);

    RegisterResponse socialSignIn(String interfaceTypeCode, SocialInfo socialInfo);

    RegisterResponse socialSignUp(String interfaceTypeCode, ExtendedSocialInfo socialInfo);

    MainScreenInfoDto getMainScreenInfo(String sessionId, UserLastViews userLastViews);

    Users getUserBySession(String sessionId);
}