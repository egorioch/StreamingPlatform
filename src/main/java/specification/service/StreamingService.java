package specification.service;

import com.google.gson.Gson;
import org.mp4parser.IsoFile;
import org.mp4parser.boxes.iso14496.part12.MovieHeaderBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@Service
public class StreamingService {
    private static final String FORMAT = "classpath:video/%s.mp4";

    @Autowired
    private ResourceLoader resourceLoader;

//    public Mono<Resource> getVideo(String title) {
//        return Mono.fromSupplier(() ->
//                resourceLoader.getResource(String.format(FORMAT, title)));
//    }

    /**
     * информация о видеофайле
     * @param title название mp4
     * @return json
     */
    public String getVideoProperties(String title) throws IOException {
        File file = new File("/home/egor/Документы/java/java Intellij/technical_specification_zel/src/main/resources/video/" + title + ".mp4");
        Resource resource = resourceLoader.getResource(String.format(FORMAT, title));
        HashMap<String, Object> hashMap = new HashMap<>();

        IsoFile isoFile = new IsoFile(file);
        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();

        Gson gson = new Gson();
        hashMap.put("size", resource.contentLength());
        hashMap.put("duration", longToSeconds(mhb.getDuration()));

        return gson.toJson(hashMap);
    }

    /**
     * @param title название видеофайла
     * @param rangeHeader bytes=start-end/size
     * @return resource в заданном диапазоне
     */
    public ResponseEntity<ResourceRegion> getVideo(String title, String rangeHeader) throws IOException {

        Resource resource = resourceLoader.getResource(String.format(FORMAT, title));
        long resourceLength = resource.contentLength();
        //позволяет отправить лишь определенное число байтов в response
        ResourceRegion resourceRegion = resourceRegion(resource, rangeHeader, resourceLength);

        //забираем хедеры с запроса и устанавливаем свои
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM));
        System.out.println("getPOSITION: " + resourceRegion.getPosition());
        System.out.println("resourceRegion.getCount(): " + resourceRegion.getCount());
//        headers.set("Content-Range", "bytes " + resourceRegion.getPosition() + "-" + resourceRegion.getCount() + "/" + resourceLength);
        headers.set("Accept-ranges", "bytes");
        headers.setContentLength(resourceRegion.getCount());

        System.out.println("headers: " + headers.getFirst("Content-Range"));

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(resourceRegion);
    }

    private ResourceRegion resourceRegion(Resource resource, String rangeHeader, long resourceLength) {
        if (rangeHeader == null) {
            return new ResourceRegion(resource, 0, resourceLength);
        }

        HttpRange range = HttpRange.parseRanges(rangeHeader).get(0);
        long start = range.getRangeStart(resourceLength);
        long end = range.getRangeEnd(resourceLength);
        long count = end - start + 1;

        return new ResourceRegion(resource, start, count);
    }


    /**
     * long-значение в секунды
     */
    private float longToSeconds(long longSeconds) {
        String stringSeconds = Long.toString(longSeconds);
        String seconds = stringSeconds.substring(0, stringSeconds.length() - 3);
        String milliseconds = stringSeconds.substring(stringSeconds.length() - 3);

        return Float.parseFloat(String.format("%s.%s", seconds, milliseconds));
    }








}
