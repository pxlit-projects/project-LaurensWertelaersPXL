package be.pxl.service.service;

import be.pxl.service.client.NewsArticleClient;
import be.pxl.service.domain.Comment;
import be.pxl.service.domain.dto.CommentRequest;
import be.pxl.service.domain.dto.CommentResponse;
import be.pxl.service.exception.ForbiddenException;
import be.pxl.service.exception.ResourceNotFoundException;
import be.pxl.service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsArticleClient newsArticleClient;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);


    public void createComment(CommentRequest commentRequest){
        logger.info("CommentService: creating comment");
        //nakijken of newsArticleId bestaat en of status APPROVED IS
        newsArticleClient.verifyApproved(commentRequest.getNewsArticleId());

        //create comment
        Comment comment = Comment.builder()
                .newsArticleId(commentRequest.getNewsArticleId())
                .usernameCommenter(commentRequest.getUsernameCommenter())
                .creationDate(LocalDateTime.now())
                .content(commentRequest.getContent())
                .build();

        commentRepository.save(comment);
    }


    public void updateComment(Long id, CommentRequest commentRequest) {
        logger.info("CommentService: updating comment");
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if (!commentOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        Comment comment = commentOptional.get();

        if (!comment.getUsernameCommenter().equals(commentRequest.getUsernameCommenter())){
            throw new ForbiddenException("username is not the same as the commenter");
        }

        if (commentRequest.getContent() == null){
            throw new ForbiddenException("Content of a comment cannot be empty");
        }

        comment.setContent(commentRequest.getContent());

        commentRepository.save(comment);
    }

    public void deleteComment(Long id, CommentRequest commentRequest) {
        logger.info("CommentService: deleting comment");
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if (!commentOptional.isPresent()){
            throw new ResourceNotFoundException();
        }

        Comment comment = commentOptional.get();

        if (!comment.getUsernameCommenter().equals(commentRequest.getUsernameCommenter())){
            throw new ForbiddenException("username is not the same as the commenter");
        }

        commentRepository.delete(comment);
    }

    public List<CommentResponse> getAllCommentsWithNewsArticleId(Long newsArticleId) {
        logger.info("CommentService: getting all comments for specific newsArticleId");
        List<Comment> comments = commentRepository.getCommentsByNewsArticleId(newsArticleId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments){
            commentResponses.add(mapToCommentResponse(comment));
        }
        return commentResponses;
    }

    private CommentResponse mapToCommentResponse(Comment comment){
        logger.info("CommentService: mapping comment object to commentResponse object");
        return CommentResponse.builder()
                .id(comment.getId())
                .newsArticleId(comment.getNewsArticleId())
                .usernameCommenter(comment.getUsernameCommenter())
                .creationDate(comment.getCreationDate())
                .content(comment.getContent())
                .build();
    }
}
