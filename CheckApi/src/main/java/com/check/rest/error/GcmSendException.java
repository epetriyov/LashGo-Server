package main.java.com.check.rest.error;

import org.springframework.web.client.RestClientException;

/**
 * Created by Eugene on 20.03.14.
 */
public class GcmSendException extends RuntimeException {
    public GcmSendException(RestClientException e) {
        super(e);
    }
}
