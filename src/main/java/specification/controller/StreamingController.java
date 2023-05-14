package specification.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import specification.service.RangeResource;
import specification.service.StreamingService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class StreamingController {
    private final StreamingService streamingService;
    private final ResourceLoader resourceLoader;

    public StreamingController(StreamingService streamingService, ResourceLoader resourceLoader) {
        this.streamingService = streamingService;
        this.resourceLoader = resourceLoader;
    }


    @GetMapping(value="/video/{title}/info")
    public String getVideos(@PathVariable String title) throws IOException{
        return streamingService.getVideoProperties(title);
    }

    @GetMapping(value="/video/{title}")
    public ResponseEntity<ResourceRegion> getVideos(@PathVariable String title,
                                                    @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        System.out.println("title(from client): " + title);
        System.out.println("range_header(from client): " + rangeHeader);
        return streamingService.getVideo(title, rangeHeader);

    }


//    @GetMapping("/video/{videoName}")
//    public ResponseEntity<Resource> streamVideo(@PathVariable String videoName, HttpServletRequest request) throws IOException {
//        // Здесь вам нужно получить ресурс видео, либо от файловой системы, либо из базы данных, и создать объект Resource
//        Resource videoResource = getVideoResource(videoName);
//
//        // Получаем полный размер видео
//        long videoSize = videoResource.contentLength();
//
//        // Проверяем, поддерживает ли клиент диапазонные запросы
//        String rangeHeader = request.getHeader("Range");
//        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
//            String[] rangeValues = rangeHeader.substring(6).split("-");
//            long startByte = Long.parseLong(rangeValues[0]);
//            long endByte = videoSize - 1;
//            if (rangeValues.length == 2) {
//                endByte = Long.parseLong(rangeValues[1]);
//            }
//            long contentLength = endByte - startByte + 1;
//            System.out.println("content-length: " + contentLength);
//
//            // Создаем объект RangeResource для представления запрошенной части видео
//            RangeResource rangeResource = new RangeResource(videoResource.getInputStream(), startByte, contentLength);
//
//            // Устанавливаем заголовки ответа для поддержки диапазонных запросов
//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.setContentType(MediaTypeFactory.getMediaType(videoName).orElse(MediaType.APPLICATION_OCTET_STREAM));
//            responseHeaders.set("Accept-Ranges", "bytes");
//            responseHeaders.set("Content-Range", "bytes=" + startByte + "-" + endByte + "/" + videoSize);
//            responseHeaders.setContentLength(contentLength);
//
//            // Возвращаем частичный ответ с запрошенной частью видео
//            return new ResponseEntity<>(rangeResource, responseHeaders, HttpStatus.PARTIAL_CONTENT);
//        } else {
//            // Возвращаем полный видеофайл, если клиент не поддерживает диапазонные запросы
//            return ResponseEntity.ok()
//                    .contentType(MediaTypeFactory.getMediaType(videoName).orElse(MediaType.APPLICATION_OCTET_STREAM))
//                    .body(videoResource);
//        }
//    }

    private Resource getVideoResource(String videoName) {
        return resourceLoader.getResource(String.format("classpath:video/%s.mp4", videoName));
    }

}
