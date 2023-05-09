package specification.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import specification.dto.Image;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
