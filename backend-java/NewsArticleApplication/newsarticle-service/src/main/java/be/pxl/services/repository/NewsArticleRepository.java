package be.pxl.services.repository;

import be.pxl.services.domain.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
}
