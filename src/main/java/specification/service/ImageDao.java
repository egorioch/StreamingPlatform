package specification.service;

import org.springframework.stereotype.Service;
import specification.repo.ImageRepo;

@Service
public class ImageDao {
    private final ImageRepo imageRepo;
    public ImageDao(ImageRepo imageRepo) {
        this.imageRepo = imageRepo;
    }

    public void saveImage(String name, String mimeType, long contentLength, Byte[] content) {

    }

}
