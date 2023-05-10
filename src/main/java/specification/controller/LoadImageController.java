package specification.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class LoadImageController {

//    @PostMapping("/video/{timeOnTheLine}/load")
//    public ResponseEntity<String> uploadImage(@PathVariable String timeOnTheLine, @RequestBody String imageData, @RequestHeader HttpHeaders headers) {
//        System.out.println("timeOnTheLine: " + timeOnTheLine);
//        System.out.println("imageData: " + imageData);
//        System.out.println("headers: ");
//        System.out.println("content-type: " + headers.getContentType().getType());
//        System.out.println("content-length: " + headers.getContentLength());
//
//        byte[] imageBytes = Base64.getDecoder().decode(imageData.split(",")[1]);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/video/{currentTime}/load")
    public ResponseEntity<String> uploadImage(@PathVariable String currentTime, @RequestPart("image") MultipartFile imageFile) {
        try {
            // Проверка наличия файла
            if (imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("No image file provided");
            }

            // Получение имени файла
            String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            System.out.println("fileNAME: " + fileName);

            // Сохранение файла
            // Здесь вам нужно добавить код для сохранения файла на диск или другое место хранения

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }
}
