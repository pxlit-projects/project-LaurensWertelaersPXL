package be.pxl.service.client;

import be.pxl.service.domain.dto.NewsArticleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "newsarticle-service")
public interface NewsArticleClient {

    @GetMapping("/api/newsarticle/{id}")
    NewsArticleResponse getById(@PathVariable("id") Long id);

    @PutMapping("/api/newsarticle/approveNA/{id}")
    void approveNA(@PathVariable("id") Long id);

    @PutMapping("/api/newsarticle/disapproveNA/{id}")
    void disapproveNA(@PathVariable("id") Long id);
}
