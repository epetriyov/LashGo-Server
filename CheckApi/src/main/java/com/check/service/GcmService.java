package main.java.com.check.service;

import com.check.model.dto.GcmRegistrationDto;
import com.check.model.dto.MulticastResult;
import main.java.com.check.rest.error.GcmSendException;
import main.java.com.check.rest.error.ValidationException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public interface GcmService {
    void addRegistrationId(String uuid, GcmRegistrationDto registrationDto) throws ValidationException;

    void multicastSend();

    void sendChecks();
}
