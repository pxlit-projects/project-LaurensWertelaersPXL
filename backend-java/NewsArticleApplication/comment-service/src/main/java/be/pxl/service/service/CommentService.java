package be.pxl.service.service;

import be.pxl.service.client.NewsArticleClient;
import be.pxl.service.domain.Comment;
import be.pxl.service.domain.dto.CommentRequest;
import be.pxl.service.exception.ForbiddenException;
import be.pxl.service.exception.ResourceNotFoundException;
import be.pxl.service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsArticleClient newsArticleClient;


    public void createComment(CommentRequest commentRequest){
        //nakijken of newsArticleId bestaat en of status APPROVED IS
        newsArticleClient.verifyApproved(commentRequest.getNewsArticleId());

        //create comment
        Comment comment = Comment.builder()
                .newsArticleId(commentRequest.getNewsArticleId())
                .usernameCommenter(commentRequest.getUsernameCommenter())
                .creationDate(new Date())
                .content(commentRequest.getContent())
                .build();

        commentRepository.save(comment);
    }


    public void updateComment(Long id, CommentRequest commentRequest) {
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
}
