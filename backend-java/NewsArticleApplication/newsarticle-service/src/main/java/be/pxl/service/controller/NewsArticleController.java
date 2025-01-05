package be.pxl.service.controller;

import be.pxl.service.domain.dto.NewsArticleRequest;
import be.pxl.service.service.NewsArticleService;
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

    @PutMapping("/saveAsConcept/{id}")
    public void updateNewsArticle(
            @PathVariable Long id,
            @RequestBody NewsArticleRequest newsArticleRequest){
        newsArticleService.saveAsConcept(id, newsArticleRequest);
    }
    @PutMapping("/submitForApproval/{id}")
    public void submitForApproval(@PathVariable Long id,
                                  @RequestBody NewsArticleRequest newsArticleRequest){
        newsArticleService.submitForApproval(id, newsArticleRequest);
    }

    //API gebruikt door review-service
    @PutMapping("/approveNA/{id}")
    public void approveNA(@PathVariable Long id){
        newsArticleService.approveNA(id);
    }

    //API gebruikt door review-service
    @PutMapping("/disapproveNA/{id}")
    public void disapproveNA(@PathVariable Long id){
        newsArticleService.disapproveNA(id);
    }

    //API gebruikt door comment-service
    @GetMapping("/verifyApproved/{id}")
    public void verifyApproved(@PathVariable Long id){
        newsArticleService.verifyApproved(id);
    }
}
