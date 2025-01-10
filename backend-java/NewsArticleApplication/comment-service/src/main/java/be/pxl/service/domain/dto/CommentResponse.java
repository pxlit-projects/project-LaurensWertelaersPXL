package be.pxl.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;

    private Long newsArticleId;
    private String usernameCommenter;
    private LocalDateTime creationDate;
    private String content;
}
