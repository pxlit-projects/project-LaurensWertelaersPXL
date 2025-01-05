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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;
    private final NotificationClient notificationClient;

    public List<NewsArticleResponse> getAllNewsArticles() {
        List<NewsArticle> newsArticles = newsArticleRepository.findAll();
        List<NewsArticleResponse> newsArticleResponses = new ArrayList<>();
        for (NewsArticle newsArticle : newsArticles) {
            newsArticleResponses.add(mapToNewsArticleResponse(newsArticle));
        }
        return newsArticleResponses;
    }

    private NewsArticleResponse mapToNewsArticleResponse(NewsArticle newsArticle) {
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
        NewsArticle newsArticle = NewsArticle.builder()
                .status(ArticleStatus.CONCEPT) //standaard status CONCEPT geven
                .content(newsArticleRequest.getContent())
                .title(newsArticleRequest.getTitle())
                .usernameWriter(newsArticleRequest.getUsernameWriter())
                .creationDate(new Date())
                .build();
        newsArticleRepository.save(newsArticle);

        //NOG AANPASSEN?
        //enkel toegevoegd om notification service ergens te gebruiken (om te zien of hij werkt)
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("newsArticle aangemaakt")
                .sender("Laurens")
                .build();
        notificationClient.sendNotification(notificationRequest);
    }

    public void saveAsConcept(Long id, NewsArticleRequest newsArticleRequest) {
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
    }

    public void disapproveNA(Long id) {
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
}
