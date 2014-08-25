package com.lashgo.model;

/**
 * Created by Eugene on 12.05.2014.
 */
public final class Path {

    private Path() {

    }

    public static final String JSONDOC = "/";

    public static final class Checks {
        public static final String GET = "/checks";
        public static final String CURRENT = "/checks/current";
        public static final String PHOTOS = "/checks/{checkId}/photos";
        public static final String COMMENTS = "/checks/{checkId}/comments";
        public static final String VOTE_PHOTOS = "/checks/{checkId}/vote/photos";
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
        public static final String GET_FILE = "/photos/{fileName}";
        public static final String VOTE = "/photos/{photoId}/vote";
        public static final String COMMENTS = "/photos/{photoId}/comments";
    }

    public static final class Users {
        public static final String REGISTER = "/users/register";
        public static final String LOGIN = "/users/login";
        public static final String RECOVER = "/users/recover";
        public static final String PROFILE = "/users/profile";
        public static final String PHOTOS = "/users/photos";
        public static final String SUBSCRIPTIONS = "/users/subscriptions";
        public static final String SUBSCRIPTION = "/users/subscriptions/{userId}";
        public static final String SOCIAL_SIGN_IN = "/users/social-sign-in";
        public static final String SOCIAL_SIGN_UP = "/users/social-sign-up";
        public static final String MAIN_SCREEN_INFO = "/users/main-screen-info";
    }
}
