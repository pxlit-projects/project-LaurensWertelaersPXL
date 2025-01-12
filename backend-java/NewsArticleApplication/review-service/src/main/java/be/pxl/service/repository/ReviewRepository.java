package be.pxl.service.repository;

import be.pxl.service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> getReviewByNewsArticleId(Long newsArticleId);
}
