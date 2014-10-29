package com.lashgo.model;

/**
 * Created by Eugene on 12.05.2014.
 */
public final class Path {

    private Path() {

    }

    public static final String JSONDOC = "/";

    public static final class Events {
        public static final String GET = "/events";
    }

    public static final class Checks {
        public static final String GET = "/checks";
        public static final String CHECK = "/checks/{checkId}";
        public static final String PHOTOS = "/checks/{checkId}/photos";
        public static final String COMMENTS = "/checks/{checkId}/comments";
        public static final String VOTE_PHOTOS = "/checks/{checkId}/vote/photos";
        public static final String LIKE = "/checks/like";
        public static final String COUNTERS = "/checks/{checkId}/counters";
        public static final String USERS = "/checks/{checkId}/users";
    }

    public static final class Comments {
        public static final String DELETE = "/comments/{commentId}";
    }

    public static final class Contents {
        public static final String GET = "/contents";
    }

    public static final class Gcm {
        public static final String REGISTER = "/gcm/register";
        public static final String TEST = "/gcm/test";
    }

    public static final class Photos {
        public static final String GET_FILE = "/photos/{fileName:.+}";
        public static final String VOTE = "/photos/vote";
        public static final String COMMENTS = "/photos/{photoId}/comments";
        public static final String COUNTERS = "/photos/{photoId}/counters";
        public static final String LIKE = "/photos/like";
        public static final String PHOTO = "/photos/by/id/{photoId}";
    }

    public static final class Users {
        public static final String REGISTER = "/users/register";
        public static final String LOGIN = "/users/login";
        public static final String RECOVER = "/users/recover";
        public static final String MY_PROFILE = "/users/profile";
        public static final String PROFILE = "/users/{userId}/profile";
        public static final String MY_PHOTOS = "/users/photos";
        public static final String PHOTOS = "/users/{userId}/photos";
        public static final String SUBSCRIPTIONS = "/users/{userId}/subscriptions";
        public static final String SUBSCRIBERS = "/users/{userId}/subscribers";
        public static final String SUBSCRIPTION = "/users/subscriptions/{userId}";
        public static final String SUBSCRIPTION_POST = "/users/subscriptions";
        public static final String SOCIAL_SIGN_IN = "/users/social-sign-in";
        public static final String MAIN_SCREEN_INFO = "/users/main-screen-info";
        public static final String AVATAR = "/users/avatar";
        public static final String GET = "/users";
    }
}
