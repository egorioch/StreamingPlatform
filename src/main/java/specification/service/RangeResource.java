package specification.service;

import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

public class RangeResource extends InputStreamResource {

    private long startByte;
    private long contentLength;

    public RangeResource(InputStream inputStream, long startByte, long contentLength) {
        super(inputStream);
        this.startByte = startByte;
        this.contentLength = contentLength;
    }

    @Override
    public long contentLength() throws IOException {
        return contentLength;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = super.getInputStream();
        inputStream.skip(startByte);
        return inputStream;
    }
}