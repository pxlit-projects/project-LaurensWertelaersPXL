package be.pxl.service.controller;

import be.pxl.service.domain.dto.ReviewRequest;
import be.pxl.service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @PostMapping("/approve")
    public void approve(@RequestBody ReviewRequest reviewRequest) {
        logger.info("ReviewController: called approve() - [POST] /api/review/approve ");
        reviewService.approve(reviewRequest);
    }

    @PostMapping("/disapprove")
    public void disapprove(@RequestBody ReviewRequest reviewRequest){
        logger.info("ReviewController: called disapprove() - [POST] /api/review/disapprove ");
        reviewService.disapprove(reviewRequest);
    }

    @PutMapping("/updateRemark/{id}")
    public void updateRemark(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest){
        logger.info("ReviewController: called updateRemark() - [PUT] /api/review/updateRemark/{id} ");
        reviewService.updateRemark(id, reviewRequest);
    }
}
