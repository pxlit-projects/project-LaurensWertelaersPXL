package be.pxl.services.controller;

import be.pxl.services.domain.dto.NewsArticleRequest;
import be.pxl.services.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsarticle")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsArticleService newsArticleService;


    @GetMapping
    public ResponseEntity getAllNewsArticles(){
        return new ResponseEntity(newsArticleService.getAllNewsArticles(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewsArticle(@RequestBody NewsArticleRequest newsArticleRequest){
        newsArticleService.createNewsArticle(newsArticleRequest);
    }

    @PutMapping("/{id}")
    public void updateNewsArticle(
            @PathVariable Long id,
            @RequestBody NewsArticleRequest newsArticleRequest){
        newsArticleService.updateNewsArticle(id, newsArticleRequest);
    }
    @PutMapping("/status/{id}")
    public void updateNewsArticleStatus(@PathVariable Long id,
                                        @RequestBody NewsArticleRequest newsArticleRequest){
        newsArticleService.updateNewsArticleStatus(id, newsArticleRequest);
    }
}
