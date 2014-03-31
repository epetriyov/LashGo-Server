package main.java.com.check.gcm;

import main.java.com.check.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 20.03.14
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger("FILE");

    public LoggingInterceptor() {

    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        ClientHttpResponse httpResponse = clientHttpRequestExecution.execute(httpRequest, bytes);
        return logHttpRequest(bytes, httpRequest, httpResponse);
    }

    private ClientHttpResponse logHttpRequest(byte[] bytes, HttpRequest httpRequest, ClientHttpResponse httpResponse) throws IOException {
        StringBuilder stringBuilder = new StringBuilder
                ("==============================================================================================\n");
        stringBuilder.append(httpRequest.getMethod().name());
        stringBuilder.append(" ");
        stringBuilder.append(httpRequest.getURI());
        stringBuilder.append("\nHeaders: \n");
        for (Object key : httpRequest.getHeaders().keySet()) {
            stringBuilder.append(key);
            stringBuilder.append(": ");
            stringBuilder.append(httpRequest.getHeaders().get(key));
            stringBuilder.append("\n");
        }
        stringBuilder.append("Body: \n");
        stringBuilder.append(new String(bytes));
        stringBuilder.append("\n");
        stringBuilder.append("Response headers: \n");
        HttpHeaders accountsHeader = httpResponse.getHeaders();
        for (Object key : accountsHeader.keySet()) {
            stringBuilder.append(key);
            stringBuilder.append(": ");
            stringBuilder.append(accountsHeader.get(key));
            stringBuilder.append("\n");
        }
        CustomClientHttpResponse customClientHttpResponse = new CustomClientHttpResponse(httpResponse);
        InputStream inputStream = customClientHttpResponse.getBody();
        if (inputStream != null) {
            byte[] bodyArray = CheckUtils.convertInputStreamToByteArray(inputStream);
            stringBuilder.append("Response body: \n");
            stringBuilder.append(CheckUtils.convertStreamToString(new ByteArrayInputStream(bodyArray)));
            customClientHttpResponse.setBody(bodyArray);
        }
        logger.info(stringBuilder.toString());
        return customClientHttpResponse;
    }

}
