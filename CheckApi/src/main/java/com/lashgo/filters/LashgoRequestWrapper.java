package com.lashgo.filters;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by Eugene on 06.03.2015.
 */
public class LashgoRequestWrapper extends HttpServletRequestWrapper {

    private byte[] buffer = null;

    public LashgoRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        InputStream is = super.getInputStream();
        buffer = IOUtils.toByteArray(is);
    }


    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStreamImpl(new ByteArrayInputStream(buffer));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String enc = getCharacterEncoding();
        if(enc == null) enc = "UTF-8";
        return new BufferedReader(new InputStreamReader(getInputStream(), enc));
    }

    public String getRequestBody() {
        return new String(buffer);
    }

    private class ServletInputStreamImpl extends ServletInputStream {

        private InputStream is;

        public ServletInputStreamImpl(InputStream is) {
            this.is = is;
        }

        public int read() throws IOException {
            return is.read();
        }

        public boolean markSupported() {
            return is.markSupported();
        }

        public synchronized void mark(int i) {
            is.mark(i);
        }

        public synchronized void reset() throws IOException {
            is.reset();
        }
    }
}
