package be.pxl.service.controller;

import be.pxl.service.domain.dto.CommentRequest;
import be.pxl.service.domain.dto.CommentResponse;
import be.pxl.service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    //getall met newsarticleId
    @GetMapping("/ofnewsarticle")
    public ResponseEntity getAllCommentsWithNewsArticleId(@RequestHeader Long newsArticleId){
        List<CommentResponse> commentResponses = commentService.getAllCommentsWithNewsArticleId(newsArticleId);
        return new ResponseEntity(commentResponses, HttpStatus.OK);
    }

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest){
        logger.info("CommentController: called createComment() - [POST] /api/comment ");
        commentService.createComment(commentRequest);
    }

    //UPDATE
    @PutMapping("/updateComment/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        logger.info("CommentController: called updateComment() - [PUT] /api/comment/updateComment/{id} ");
        commentService.updateComment(id, commentRequest);
    }

    //DELETE
    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        logger.info("CommentController: called deleteComment() - [DELETE] /api/comment/deleteComment/{id} ");
        commentService.deleteComment(id, commentRequest);
    }
}
