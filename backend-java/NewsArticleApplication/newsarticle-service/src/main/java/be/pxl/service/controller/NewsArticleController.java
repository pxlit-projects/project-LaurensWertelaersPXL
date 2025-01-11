package be.pxl.service.controller;

import be.pxl.service.domain.dto.NewsArticleRequest;
import be.pxl.service.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/newsarticle")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsArticleService newsArticleService;
    private static final Logger logger = LoggerFactory.getLogger(NewsArticleController.class);


    @GetMapping
    public ResponseEntity getAllNewsArticles(){
        logger.info("NewsArticleController: called getAllNewsArticles() - [GET] /api/newsarticle ");
        return new ResponseEntity(newsArticleService.getAllNewsArticles(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewsArticle(@RequestBody NewsArticleRequest newsArticleRequest){
        logger.info("NewsArticleController: called createNewsArticle() - [POST] /api/newsarticle ");
        newsArticleService.createNewsArticle(newsArticleRequest);
    }

    @PutMapping("/saveAsConcept/{id}")
    public void updateNewsArticle(
            @PathVariable Long id,
            @RequestBody NewsArticleRequest newsArticleRequest){
        logger.info("NewsArticleController: called updateNewsArticle() - [PUT] /api/newsarticle/saveAsConcept/{id} ");
        newsArticleService.saveAsConcept(id, newsArticleRequest);
    }
    @PutMapping("/submitForApproval/{id}")
    public void submitForApproval(@PathVariable Long id,
                                  @RequestBody NewsArticleRequest newsArticleRequest){
        logger.info("NewsArticleController: called submitForApproval() - [PUT] /api/newsarticle/submitForApproval/{id} ");
        newsArticleService.submitForApproval(id, newsArticleRequest);
    }

    //API gebruikt door review-service
    @PutMapping("/approveNA/{id}")
    public void approveNA(@PathVariable Long id){
        logger.info("NewsArticleController: called approveNA() - [PUT] /api/newsarticle/approveNA/{id} ");
        newsArticleService.approveNA(id);
    }

    //API gebruikt door review-service
    @PutMapping("/disapproveNA/{id}")
    public void disapproveNA(@PathVariable Long id){
        logger.info("NewsArticleController: called disapproveNA() - [PUT] /api/newsarticle/disapproveNA/{id} ");
        newsArticleService.disapproveNA(id);
    }

    //API gebruikt door comment-service
    @GetMapping("/verifyApproved/{id}")
    public void verifyApproved(@PathVariable Long id){
        logger.info("NewsArticleController: called verifyApproved() - [GET] /api/newsarticle/verifyApproved/{id} ");
        newsArticleService.verifyApproved(id);
    }

    //getAll met status approved
    @GetMapping("/approved")
    public ResponseEntity getAllApprovedNewsArticles() {
        logger.info("NewsArticleController: called getAllApprovedNewsArticles() - [GET] /api/newsarticle/approved ");
        return new ResponseEntity(newsArticleService.getAllApprovedNewsArticles(), HttpStatus.OK);
    }

    //getAll met opgegeven usernameWriter
    @GetMapping("/myarticles")
    public ResponseEntity getAllNewsArticlesWithUsername(@RequestHeader String usernameWriter){
        logger.info("NewsArticleController: called getAllNewsArticlesWithUsername() - [GET] /api/newsarticle/myarticles ");
        return new ResponseEntity(newsArticleService.getAllNewsArticlesWithUsername(usernameWriter), HttpStatus.OK);
    }
}
