package com.lashgo.filters;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by Eugene on 06.03.2015.
 */
public class LashgoRequestWrapper extends HttpServletRequestWrapper {
    private ByteArrayInputStream bais = null;
    private ByteArrayOutputStream baos = null;
    private BufferedServletInputStream bsis = null;
    private byte[] buffer = null;


    public LashgoRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        // Read InputStream and store its content in a buffer.
        InputStream is = req.getInputStream();
        this.baos = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int letti;
        while ((letti = is.read(buf)) > 0) {
            this.baos.write(buf, 0, letti);
        }
        this.buffer = this.baos.toByteArray();
    }


    @Override
    public ServletInputStream getInputStream() {
        this.bais = new ByteArrayInputStream(this.buffer);
        this.bsis = new BufferedServletInputStream(this.bais);
        return this.bsis;
    }


    public String getRequestBody() {
        StringBuilder bodyBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
        try {
            String bodyLine;
            do {
                bodyLine = reader.readLine();
                bodyBuilder.append(bodyLine);
            } while (bodyLine != null);
        } catch (IOException e) {

        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bodyBuilder.toString();
    }

    private static final class BufferedServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }


        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
