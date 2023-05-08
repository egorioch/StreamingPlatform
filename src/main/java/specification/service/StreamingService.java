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
//
//    public Mono<Resource> getVideo(String title) {
//        return Mono.fromSupplier(() ->
//                resourceLoader.getResource(String.format(FORMAT, title)));
//    }

//    public String getVideoProperties(String title) throws IOException {
//        File file = new File("/home/egor/Документы/java/java Intellij/technical_specification_zel/src/main/resources/video/" + title + ".mp4");
//        Resource resource = resourceLoader.getResource(String.format(FORMAT, title));
//        HashMap<String, Object> hashMap = new HashMap<>();
//
//        System.out.println("size" + resource.contentLength());
//
//        IsoFile isoFile = new IsoFile(file);
//        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();
//        System.out.println("duration: " + isoFile.getMovieBox().getMovieHeaderBox().getDuration());
//        System.out.println("mhb.getSize: " + mhb.getSize());
//        System.out.println("mhb.getTimescale: " + mhb.getTimescale());
//        long timeBytes =  mhb.getDuration() / mhb.getTimescale();
//        System.out.println("timeBytes: " + timeBytes);
//
//        Gson gson = new Gson();
//        hashMap.put("sizeBytes: ", timeBytes);
//        String jsonAns = gson.toJson(hashMap);
//        System.out.println("jsonAns: " + jsonAns);
//        return jsonAns;
//    }

    public ResponseEntity<ResourceRegion> getVideo(String title, String rangeHeader) throws IOException {

        Resource resource = resourceLoader.getResource(String.format(FORMAT, title));

        long resourceLength = resource.contentLength();
        System.out.println("resourceLength: " + resourceLength);

        //позволяет отправить лишь определенное число байтов в response
        ResourceRegion resourceRegion = resourceRegion(resource, rangeHeader, resourceLength);
        System.out.println("resourceReg_getCount: " + resourceRegion.getCount());
        System.out.println("resourceReg_getPosition: " + resourceRegion.getPosition());
        System.out.println("resourceReg_getResource: " + resourceRegion.getResource().getFilename());

        //забираем хедеры с запроса и устанавливаем свои
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.set("Content-Range", "bytes " + resourceRegion.getPosition() + "-" + resourceRegion.getCount() + "/" + resourceLength);
        headers.setContentLength(resourceRegion.getCount());
        System.out.println("headers: " + headers.getFirst("Content-Range"));

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .headers(headers)
                .body(resourceRegion);
    }

    private ResourceRegion resourceRegion(Resource resource, String rangeHeader, long resourceLength) {
        //при первой загрузке страницы возвращается именно это значение
        if (rangeHeader == null) {
            return new ResourceRegion(resource, 0, resourceLength);
        }

        HttpRange range = HttpRange.parseRanges(rangeHeader).get(0);
        System.out.println("range: " + range.toString());
        long start = range.getRangeStart(resourceLength);
        System.out.println("start: " + range.getRangeStart(resourceLength));
        long end = range.getRangeEnd(resourceLength);
        System.out.println("end: " + range.getRangeEnd(resourceLength));
        long count = end - start + 1;
        System.out.println("count: " + count);
        return new ResourceRegion(resource, start, count);
    }



    public void getRange(String path, String clientRange) throws IOException {
        Resource resource = resourceLoader.getResource(String.format(FORMAT, path));
        long lengthFile = resource.contentLength();
        HttpRange range = HttpRange.parseRanges(clientRange).get(0);
        System.out.println("client range:" + range.toString());
        System.out.println("length of file: " + lengthFile);
    }











}
