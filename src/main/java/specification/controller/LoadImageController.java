package specification.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import specification.dto.Image;
import specification.repo.ImageRepo;
import specification.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class LoadImageController {
    private final ImageService imageService;
    private final ImageRepo imageRepo;

    public LoadImageController(ImageService imageService,
                               ImageRepo imageRepo) {
        this.imageService = imageService;
        this.imageRepo = imageRepo;
    }

    @PostMapping("/video/load")
    public ResponseEntity<String> uploadImage(@RequestPart("image") MultipartFile imageFile) {
        try {
            // Проверка наличия файла
            if (imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("No image file provided");
            }

            imageService.saveImage(imageFile);

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }

    //возвращает все скриншоты, сделанные за всё время
    @GetMapping("/video/all_images")
    public ResponseEntity<List<Image>> getImages() {
        return ResponseEntity.ok(imageRepo.findAll());
    }
}
