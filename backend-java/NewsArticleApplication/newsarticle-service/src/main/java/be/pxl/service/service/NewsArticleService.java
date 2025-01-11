package be.pxl.service.service;

import be.pxl.service.client.NotificationClient;
import be.pxl.service.domain.ArticleStatus;
import be.pxl.service.domain.NewsArticle;
import be.pxl.service.domain.dto.NewsArticleRequest;
import be.pxl.service.domain.dto.NewsArticleResponse;
import be.pxl.service.domain.dto.NotificationRequest;
import be.pxl.service.exception.ResourceNotFoundException;
import be.pxl.service.exception.ForbiddenException;
import be.pxl.service.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NotificationClient notificationClient;
    private static final Logger logger = LoggerFactory.getLogger(NewsArticleService.class);

    public List<NewsArticleResponse> getAllNewsArticles() {
        logger.info("NewsArticleService: getting NewsArticles");
        List<NewsArticle> newsArticles = newsArticleRepository.findAll();
        List<NewsArticleResponse> newsArticleResponses = new ArrayList<>();
        for (NewsArticle newsArticle : newsArticles) {
            newsArticleResponses.add(mapToNewsArticleResponse(newsArticle));
        }
        return newsArticleResponses;
    }

    private NewsArticleResponse mapToNewsArticleResponse(NewsArticle newsArticle) {
        logger.info("NewsArticleService: mapping NewsArticles to NewsArticleResponses");
        return NewsArticleResponse.builder()
                .id(newsArticle.getId())
                .status(newsArticle.getStatus())
                .creationDate(newsArticle.getCreationDate())
                .title(newsArticle.getTitle())
                .content(newsArticle.getContent())
                .usernameWriter(newsArticle.getUsernameWriter())
                .build();
    }

    public void createNewsArticle(NewsArticleRequest newsArticleRequest) {
        logger.info("NewsArticleService: creating NewsArticle");
        NewsArticle newsArticle = NewsArticle.builder()
                .status(ArticleStatus.CONCEPT) //standaard status CONCEPT geven
                .content(newsArticleRequest.getContent())
                .title(newsArticleRequest.getTitle())
                .usernameWriter(newsArticleRequest.getUsernameWriter())
                .creationDate(LocalDateTime.now())
                .build();
        newsArticleRepository.save(newsArticle);
    }

    public void saveAsConcept(Long id, NewsArticleRequest newsArticleRequest) {
        logger.info("NewsArticleService: saving as concept");
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als usernameWriter in request niet hetzelfde is gooi exception
        if (!newsArticle.getUsernameWriter().equals(newsArticleRequest.getUsernameWriter())) {
            throw new ForbiddenException("username is not the same as the article-writer");
        }
        //als status SUBMITTED is -> zet status op CONCEPT
        if (newsArticle.getStatus() == ArticleStatus.SUBMITTED){
            newsArticle.setStatus(ArticleStatus.CONCEPT);
        }
        //als status niet CONCEPT is -> gooi error
        if (newsArticle.getStatus() != ArticleStatus.CONCEPT) {
            throw new ForbiddenException("Cannot perform updates while status is not CONCEPT");
        }

        //voer updates uit
        //title
        if (newsArticleRequest.getTitle() != null) {
            newsArticle.setTitle(newsArticleRequest.getTitle());
        }
        //content
        if (newsArticleRequest.getContent() != null) {
            newsArticle.setContent(newsArticleRequest.getContent());
        }

        newsArticleRepository.save(newsArticle);
    }

    public void submitForApproval(Long id, NewsArticleRequest newsArticleRequest) {
        logger.info("NewsArticleService: submitting for approval");
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als usernameWriter in request niet hetzelfde is gooi exception
        if (!newsArticle.getUsernameWriter().equals(newsArticleRequest.getUsernameWriter())) {
            throw new ForbiddenException("username is not the same as the article writer");
        }

        //verander status naar SUBMITTED
        newsArticle.setStatus(ArticleStatus.SUBMITTED);

        newsArticleRepository.save(newsArticle);
    }

    public void approveNA(Long id){
        logger.info("NewsArticleService: approving");
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als status niet SUBMITTED is gooi exception
        if (newsArticle.getStatus() != ArticleStatus.SUBMITTED){
            throw new ForbiddenException("Only an article with status SUBMITTED can be approved");
        }

        //verander status naar APPROVED
        newsArticle.setStatus(ArticleStatus.APPROVED);

        newsArticleRepository.save(newsArticle);

        //notification versturen naar de writer van het artikel
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("Uw artikel met id " + newsArticle.getId() + " werd goedgekeurd.")
                .receiver(newsArticle.getUsernameWriter())
                .build();
        notificationClient.sendNotification(notificationRequest);
    }

    public void disapproveNA(Long id) {
        logger.info("NewsArticleService: disapproving");
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als status niet SUBMITTED is gooi exception
        if (newsArticle.getStatus() != ArticleStatus.SUBMITTED){
            throw new ForbiddenException("Only an article with status SUBMITTED can be approved");
        }

        //verander status naar DISAPPROVED
        newsArticle.setStatus(ArticleStatus.DISAPPROVED);

        newsArticleRepository.save(newsArticle);
    }

    public void verifyApproved(Long id) {
        logger.info("NewsArticleService: verifying if status is approved");
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()) {
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als status niet APPROVED is gooi exception
        if (newsArticle.getStatus() != ArticleStatus.APPROVED){
            throw new ForbiddenException("You can only comment on articles with status APPROVED");
        }
    }

    public List<NewsArticleResponse> getAllApprovedNewsArticles() {
        logger.info("NewsArticleService: getting all newsarticles with status approved");
        List<NewsArticle> newsArticles = newsArticleRepository.findByStatus(ArticleStatus.APPROVED);
        List<NewsArticleResponse> newsArticleResponses = new ArrayList<>();
        for (NewsArticle newsArticle : newsArticles){
            newsArticleResponses.add(mapToNewsArticleResponse(newsArticle));
        }
        return newsArticleResponses;
    }

    public List<NewsArticleResponse> getAllNewsArticlesWithUsername(String usernameWriter) {
        logger.info("NewsArticleService: getting all newsarticles with usernameWriter: " + usernameWriter);
        List<NewsArticle> newsArticles = newsArticleRepository.findByUsernameWriter(usernameWriter);

        //if (newsArticles.size() == 0){
        //    throw new ResourceNotFoundException();
        //}

        List<NewsArticleResponse> newsArticleResponses = new ArrayList<>();
        for (NewsArticle newsArticle : newsArticles){
            newsArticleResponses.add(mapToNewsArticleResponse(newsArticle));
        }
        return newsArticleResponses;
    }
}
