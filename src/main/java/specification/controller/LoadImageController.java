package specification.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class LoadImageController {

    @PostMapping("/video/{timeOnTheLine}/load")
    public ResponseEntity<String> uploadImage(@PathVariable String timeOnTheLine, @RequestBody String imageData, @RequestHeader HttpHeaders headers) {
        System.out.println("timeOnTheLine: " + timeOnTheLine);
        System.out.println("imageData: " + imageData);
        System.out.println("headers: ");
        System.out.println("content-type: " + headers.getContentType().getType());
        System.out.println("content-length: " + headers.getContentLength());

        byte[] imageBytes = Base64.getDecoder().decode(imageData);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
