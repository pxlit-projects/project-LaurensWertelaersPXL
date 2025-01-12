package be.pxl.service.service;

import be.pxl.service.client.NewsArticleClient;
import be.pxl.service.client.NotificationClient;
import be.pxl.service.domain.Review;
import be.pxl.service.domain.dto.NewsArticleResponse;
import be.pxl.service.domain.dto.NotificationRequest;
import be.pxl.service.domain.dto.ReviewRequest;
import be.pxl.service.domain.dto.ReviewResponse;
import be.pxl.service.exception.ForbiddenException;
import be.pxl.service.exception.ResourceNotFoundException;
import be.pxl.service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final NewsArticleClient newsArticleClient;
    private final NotificationClient notificationClient;
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);


    //Ik zou hier een transaction van willen maken...
    public void approve(ReviewRequest reviewRequest){
        logger.info("ReviewService: approving");
        //status bijwerken in NewsArticleService
        newsArticleClient.approveNA(reviewRequest.getNewsArticleId());

        //create oproepen
        createReview(reviewRequest);

        //notification maken
        NewsArticleResponse newsArticleResponse = newsArticleClient.getById(reviewRequest.getNewsArticleId());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .newsArticleId(reviewRequest.getNewsArticleId())
                .receiverUsernameWriter(newsArticleResponse.getUsernameWriter())
                .senderUsernameEditor(reviewRequest.getUserNameEditor())
                .approvedOrDisapproved("APPROVED")
                .remark(reviewRequest.getRemark())
                .build();
        notificationClient.createNotification(notificationRequest);
    }

    //Ik zou hier een transaction van willen maken...
    public void disapprove(ReviewRequest reviewRequest) {
        logger.info("ReviewService: disapproving");
        //status bijwerken in NewsArticleService
        newsArticleClient.disapproveNA(reviewRequest.getNewsArticleId());

        //create oproepen
        createReview(reviewRequest);

        //notification maken
        NewsArticleResponse newsArticleResponse = newsArticleClient.getById(reviewRequest.getNewsArticleId());
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .newsArticleId(reviewRequest.getNewsArticleId())
                .receiverUsernameWriter(newsArticleResponse.getUsernameWriter())
                .senderUsernameEditor(reviewRequest.getUserNameEditor())
                .approvedOrDisapproved("DISAPPROVED")
                .remark(reviewRequest.getRemark())
                .build();
        notificationClient.createNotification(notificationRequest);
    }


    private void createReview(ReviewRequest reviewRequest){
        logger.info("ReviewService: creating Review");
        Review review = Review.builder()
                .newsArticleId(reviewRequest.getNewsArticleId())
                .userNameEditor(reviewRequest.getUserNameEditor())
                .remark(reviewRequest.getRemark())
                .reviewDate(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    public void updateRemark(Long id, ReviewRequest reviewRequest){
        logger.info("ReviewService: updating remark");
        Optional<Review> reviewOptional = reviewRepository.findById(id);

        //als niks gevonden is voor gegeven id gooi exception
        if (!reviewOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        Review review = reviewOptional.get();

        //als username niet hetzelfde is gooi exception
        if (!review.getUserNameEditor().equals(reviewRequest.getUserNameEditor())){
            throw new ForbiddenException("username is not the same as the review-editor");
        }

        review.setRemark(reviewRequest.getRemark());

        reviewRepository.save(review);
    }


    public ReviewResponse getReviewWithNewsArticleId(Long newsArticleId) {
        Optional<Review> reviewOptional = reviewRepository.getReviewByNewsArticleId(newsArticleId);

        if (!reviewOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        Review review = reviewOptional.get();

        ReviewResponse reviewResponse = ReviewResponse.builder()
                .id(review.getId())
                .newsArticleId(review.getNewsArticleId())
                .userNameEditor(review.getUserNameEditor())
                .remark(review.getRemark())
                .reviewDate(review.getReviewDate())
                .build();
        return reviewResponse;
    }
}
