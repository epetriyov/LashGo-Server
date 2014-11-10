package com.lashgo.model;

/**
 * Created by Eugene on 13.02.14.
 */
public final class ErrorCodes {
    public static final String USER_NOT_EXISTS = "user.not.exists";
    public static final String USER_ALREADY_EXISTS = "user.already_exists";
    public static final String UUID_IS_EMPTY = "uuid.is_empty";
    public static final String INVALID_CLIENT_TYPE = "client_type.invalid";
    public static final String SESSION_EXPIRED = "session.expired";
    public static final String REGISTRATION_ID_IS_EMPTY = "registration_id.empty";
    public static final String SESSION_IS_EMPTY = "session.empty";
    public static final String WRONG_SESSION = "session.not_exists";
    public static final String PHOTO_ALREADY_EXISTS = "photo.already_exists";
    public static final String PHOTO_READ_ERROR = "photo.read_error";
    public static final String PHOTO_WRITE_ERROR = "photo.write_error";
    public static final String UNSUPPORTED_SOCIAL = "social.unsupported";
    public static final String INTERNAL_SERVER_ERROR = "server.internal_error";
    public static final String CHECK_ID_NULL = "check.empty_id";
    public static final String USERS_DOESNT_MATCHES = "users.not_matches";
    public static final String EMPTY_EMAIL = "email.is_empty";
    public static final String SOCIAL_WRONG_DATA = "social.wrong_data";
    public static final String USER_WITH_LOGIN_ALREADY_EXISTS = "login.already_exists";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "email.already_exists";
    public static final String PHOTO_ID_NULL = "photo.empty_id";
    public static final String SUBSCRIPTION_ALREADY_EXISTS = "subscription.already_exists";
    public static final String SUBSCRIPTION_NOT_EXISTS = "subscription_not_exists";
    public static final String CHECK_IS_NOT_ACTIVE = "check.is_not_active";
    public static final String CANT_SUBSCRIBE_TO_YOURSELF = "cant_subscribe_to_yourself";

    private ErrorCodes() {

    }
}
