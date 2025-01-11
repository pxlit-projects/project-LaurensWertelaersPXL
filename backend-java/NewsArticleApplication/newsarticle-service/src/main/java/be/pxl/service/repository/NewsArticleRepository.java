package be.pxl.service.repository;

import be.pxl.service.domain.ArticleStatus;
import be.pxl.service.domain.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findByStatus(ArticleStatus articleStatus);
    List<NewsArticle> findByUsernameWriter(String usernameWriter);
}
