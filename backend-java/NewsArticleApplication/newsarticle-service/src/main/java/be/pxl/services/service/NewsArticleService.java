package be.pxl.services.service;

import be.pxl.services.domain.ArticleStatus;
import be.pxl.services.domain.NewsArticle;
import be.pxl.services.domain.dto.NewsArticleRequest;
import be.pxl.services.domain.dto.NewsArticleResponse;
import be.pxl.services.exception.ResourceNotFoundException;
import be.pxl.services.exception.ForbiddenException;
import be.pxl.services.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;

    public List<NewsArticleResponse> getAllNewsArticles() {
        List<NewsArticle> newsArticles = newsArticleRepository.findAll();
        List<NewsArticleResponse> newsArticleResponses = new ArrayList<>();
        for (NewsArticle newsArticle : newsArticles){
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
    }

    public void updateNewsArticle(Long id, NewsArticleRequest newsArticleRequest) {
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als usernameWriter in request niet hetzelfde is gooi exception
        if (!newsArticle.getUsernameWriter().equals(newsArticleRequest.getUsernameWriter())){
            throw new ForbiddenException("username is not the same as the article writer");
        }
        //als status niet CONCEPT is mag er niks veranderen dus throw exception
        if (newsArticle.getStatus() != ArticleStatus.CONCEPT){
            throw new ForbiddenException("Cannot perform updates while status is not CONCEPT");
        }

        //voer updates uit
        //title
        if (newsArticleRequest.getTitle() != null){
            newsArticle.setTitle(newsArticleRequest.getTitle());
        }
        //content
        if (newsArticleRequest.getContent() != null){
            newsArticle.setContent(newsArticleRequest.getContent());
        }

        newsArticleRepository.save(newsArticle);
    }

    public void updateNewsArticleStatus(Long id, NewsArticleRequest newsArticleRequest) {
        Optional<NewsArticle> newsArticleOptional = newsArticleRepository.findById(id);

        //als voor die id niks is gevonden gooi exception
        if (!newsArticleOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        NewsArticle newsArticle = newsArticleOptional.get();

        //als usernameWriter in request niet hetzelfde is gooi exception
        if (!newsArticle.getUsernameWriter().equals(newsArticleRequest.getUsernameWriter())){
            throw new ForbiddenException("username is not the same as the article writer");
        }

        //updatestatus
        newsArticle.setStatus(newsArticleRequest.getStatus());

        newsArticleRepository.save(newsArticle);
    }
}
