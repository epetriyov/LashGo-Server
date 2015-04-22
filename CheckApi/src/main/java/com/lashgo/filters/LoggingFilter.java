package com.lashgo.filters;

import com.lashgo.model.CheckApiHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Eugene on 06.03.2015.
 */
public class LoggingFilter extends GenericFilterBean {

    private static Logger logger = LoggerFactory.getLogger("FILE");

    private void logRequest(LashgoRequestWrapper httpServletRequest) {
        StringBuilder requestInfoBuilder = new StringBuilder("REQUEST INFO: ");
        requestInfoBuilder.append(httpServletRequest.getMethod());
        requestInfoBuilder.append(" ");
        requestInfoBuilder.append(httpServletRequest.getRequestURI());
        requestInfoBuilder.append("; Headers: ");
        String headerName;
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            try {
                CheckApiHeaders.valueOf(headerName);
            } catch (IllegalArgumentException e) {
                continue;
            }
            requestInfoBuilder.append(headerName);
            requestInfoBuilder.append(" = ");
            requestInfoBuilder.append(httpServletRequest.getHeader(headerName));
            requestInfoBuilder.append("; ");
        }
        if (httpServletRequest.getContentType() == null || (!httpServletRequest.getContentType().contains("multipart/form-data"))) {
            requestInfoBuilder.append(" Body: ");
            requestInfoBuilder.append(httpServletRequest.getRequestBody());
        }
        logger.debug(requestInfoBuilder.toString());
    }

    private void logResponse(LashGoResponseWrapper httpServletResponse) {
        StringBuilder requestInfoBuilder = new StringBuilder("RESPONSE INFO: ");
        requestInfoBuilder.append(httpServletResponse.getStatus());
        requestInfoBuilder.append(" Body: ");
        String response = httpServletResponse.getContent();
        String responseBody = response != null && response.length() > 150 ? response.substring(0, 150) : response;
        requestInfoBuilder.append(responseBody);
        logger.debug(requestInfoBuilder.toString());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            LashgoRequestWrapper requestWrapper = new LashgoRequestWrapper((HttpServletRequest) request);
            if (!"/".equals(requestWrapper.getPathInfo())) {
                logRequest(requestWrapper);
            }
            LashGoResponseWrapper responseWrapper = new LashGoResponseWrapper((HttpServletResponse) response);
            chain.doFilter(requestWrapper, responseWrapper);
            if (!"/".equals(requestWrapper.getPathInfo())) {
                logResponse(responseWrapper);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}