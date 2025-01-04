package be.pxl.service.controller;

import be.pxl.service.domain.dto.ReviewRequest;
import be.pxl.service.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/approve")
    public void approve(@RequestBody ReviewRequest reviewRequest) {
        reviewService.approve(reviewRequest);
    }

    @PostMapping("/disapprove")
    public void disapprove(@RequestBody ReviewRequest reviewRequest){
        reviewService.disapprove(reviewRequest);
    }

    @PutMapping("/updateRemark/{id}")
    public void updateRemark(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest){
        reviewService.updateRemark(id, reviewRequest);
    }
}
