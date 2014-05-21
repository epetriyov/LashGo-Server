package main.java.com.lashgo.gcm;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 20.03.14
 * Time: 23:56
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CustomClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        connection.setUseCaches(false);
        setOutputStreaming(false);
        super.prepareConnection(connection, httpMethod);
    }
}
