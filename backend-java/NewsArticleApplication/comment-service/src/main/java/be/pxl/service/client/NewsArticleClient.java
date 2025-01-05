package be.pxl.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "newsarticle-service")
public interface NewsArticleClient {

    @GetMapping("/newsarticle/verifyApproved/{id}")
    void verifyApproved(@PathVariable("id") Long id);
}
