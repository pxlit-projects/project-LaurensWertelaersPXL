package be.pxl.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "newsarticle-service")
public interface NewsArticleClient {

    @PutMapping("/api/newsarticle/approveNA/{id}")
    void approveNA(@PathVariable("id") Long id);

    @PutMapping("/api/newsarticle/disapproveNA/{id}")
    void disapproveNA(@PathVariable("id") Long id);
}
