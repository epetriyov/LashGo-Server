package com.lashgo.model;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 27.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public final class ClientTypes {

    private ClientTypes() {

    }

    public static boolean isClientTypeValid(String clientType) {
        for (ClientTypesEnum clientTypesEnum : ClientTypesEnum.values()) {
            if (clientTypesEnum.name().equals(clientType)) {
                return true;
            }
        }
        return false;
    }


    private enum ClientTypesEnum {
        ANDROID, IOS
    }
}