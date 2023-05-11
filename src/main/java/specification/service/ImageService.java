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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepo imageRepo;
    private final String pathToImage = "src/main/resources/screenshots/";

    public ImageService(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public void saveImage(MultipartFile imageFile) throws IOException {
        // Получение имени файла
        Image imageModel = new Image();


        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        long imageSize = imageFile.getSize();
        String mimeType = "image/jpeg";

        imageModel.setContent(imageFile.getBytes());
        imageModel.setName(fileName);
        imageModel.setSize(imageSize);
        imageModel.setMimeType(mimeType);
        System.out.println("filename: " + imageModel.getName());
        System.out.println("size: " + imageModel.getSize());
        System.out.println("mime: " + imageModel.getMimeType());

        //сохраняем картинку
        ByteArrayInputStream bis = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage image = ImageIO.read(bis);
        bis.close();
        String filepath = String.format("%s%s", pathToImage, fileName);
        File file = new File(filepath);
        ImageIO.write(image, "jpeg", file);

        imageRepo.save(imageModel);

        System.out.println("findALL: " + imageRepo.findAll());
    }

    public ArrayList<HashMap<String, Object>> getImageList() {
        List<Image> imageList = imageRepo.findAll();
        ArrayList<HashMap<String, Object>> listToClient = new ArrayList<>();

        for (var image : imageList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", image.getId());
            map.put("content", image.getContent());
            map.put("name", image.getName());
            map.put("mimeType", image.getMimeType());

            listToClient.add(map);
        }

        return listToClient;
    }

    private byte[] getImageBytesFromFolder(String filepath, String filename) {
        String fullName = filepath + filename;
        System.out.println("fullname: " + fullName);
        File file = new File(fullName);

        try {
            if (file.exists()) {
                System.out.println("file SIZE: " + file.length());
                return Files.readAllBytes(file.toPath());
            } else {
                System.err.println("File with path " + fullName + " is not exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
