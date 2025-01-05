package be.pxl.service.controller;

import be.pxl.service.domain.dto.CommentRequest;
import be.pxl.service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@RequestBody CommentRequest commentRequest){
        commentService.createComment(commentRequest);
    }

    //UPDATE
    @PutMapping("/updateComment/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        commentService.updateComment(id, commentRequest);
    }

    //DELETE
    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        commentService.deleteComment(id, commentRequest);
    }
}
