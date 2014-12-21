package com.lashgo.service;

import com.lashgo.domain.Users;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
public interface UserService {

    SessionInfo login(String interfaceTypeCode, LoginInfo loginInfo) throws ValidationException, UnautharizedException;

    RegisterResponse register(String interfaceTypeCode, LoginInfo registerInfo) throws ValidationException;

    void sendRecoverPassword(RecoverInfo email) throws ValidationException;

    UserDto getProfile(String sessionId, int userId);

    List<PhotoDto> getPhotos(String sessionId);

    List<SubscriptionDto> getSubscribers(String sessionId, int userId);

    List<SubscriptionDto> getMySubscriptions(String sessionId);

    List<SubscriptionDto> getMySubscribers(String sessionId);

    List<SubscriptionDto> getSubscriptions(String sessionId, int userId);

    void unsubscribe(String sessionId, int userId);

    void subscribe(String sessionId, int userId);

    RegisterResponse socialSignIn(String interfaceTypeCode, SocialInfo socialInfo);

    MainScreenInfoDto getMainScreenInfo(String sessionId, UserLastViews userLastViews);

    Users getUserBySession(String sessionId);

    UserDto getProfile(String sessionId);

    List<PhotoDto> getPhotos(int userId);

    void saveAvatar(String sessionId, MultipartFile file);

    void updateProfile(String sessionId, UserDto userDto);

    List<SubscriptionDto> findUsers(String sessionId, String searchText);

    List<SubscriptionDto> getUsersByVotes(String sessionId, long photoId);
}
