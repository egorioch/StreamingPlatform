package specification.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import specification.service.StreamingService;
import org.springframework.core.io.support.ResourceRegion;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class StreamingController {
    private final StreamingService streamingService;

    public StreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }


//    @GetMapping(value="/video/{title}", produces = "video/mp4")
//    public Mono<Resource> getVideos(@PathVariable String title, @RequestHeader("Range") String range, HttpServletResponse response) {
//        System.out.println("range in bytes: " + range);
//        response.setContentType("video/mp4");
//        return streamingService.getVideo(title);
//    }

//    @GetMapping(value="/video/{title}")
//    public String getVideos(@PathVariable String title) throws IOException{
//        return streamingService.getVideoProperties(title);
//    }

    @GetMapping(value="video/{title}", produces = "video/mp4")
    public ResponseEntity<ResourceRegion> getVideos(@PathVariable String title,
                                                    @RequestHeader(value = "Range", required = false) String rangeHeader) throws IOException {
        System.out.println("title(from client): " + title);
        System.out.println("range_header(from client): " + rangeHeader);
        return streamingService.getVideo(title, rangeHeader);

    }


//    public void myChunkDivider(@PathVariable String path, HttpRequest request) {
//        HttpHeaders headers = request.getHeaders();
//        headers.
//    }
}
