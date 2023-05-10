package specification.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import specification.dto.Image;
import specification.repo.ImageRepo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepo imageRepo;

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public void saveImage(MultipartFile imageFile) throws IOException {
        // Получение имени файла
        Image imageModel = new Image();

        byte[] imageData = imageFile.getBytes();
        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        long imageSize = imageFile.getSize();
        String mimeType = "image/jpeg";

        imageModel.setContent(imageData);
        imageModel.setName(fileName);
        imageModel.setSize(imageSize);
        imageModel.setMimeType(mimeType);
        System.out.println("filename: " + imageModel.getName());
        System.out.println("size: " + imageModel.getSize());
        System.out.println("mime: " + imageModel.getMimeType());

        imageRepo.save(imageModel);

        System.out.println("findALL: " + imageRepo.findAll());
//        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
//        BufferedImage image = ImageIO.read(bis);
//        bis.close();
//        String filepath = String.format("src/main/resources/%s.jpeg", fileName);
//        File file = new File(filepath);
//        ImageIO.write(image, "jpeg", file);
    }

}
