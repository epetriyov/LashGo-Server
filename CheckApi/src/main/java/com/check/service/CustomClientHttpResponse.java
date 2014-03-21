package main.java.com.check.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: Eugene
 * Date: 22.08.13
 * Time: 16:27
 */
public class CustomClientHttpResponse implements ClientHttpResponse {

    private ClientHttpResponse response;

    public CustomClientHttpResponse(ClientHttpResponse response) {
        this.response = response;
    }

    private byte[] body;

    @Override
    public InputStream getBody() throws IOException {
        if (body == null) {
            return response.getBody();
        } else {
            return new ByteArrayInputStream(body);
        }
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public HttpStatus getStatusCode() throws IOException {
        return response.getStatusCode();
    }

    @Override
    public int getRawStatusCode() throws IOException {
        return response.getRawStatusCode();
    }

    @Override
    public String getStatusText() throws IOException {
        return response.getStatusText();
    }

    @Override
    public void close() {
        response.close();
    }

    @Override
    public HttpHeaders getHeaders() {
        return response.getHeaders();
    }
}
