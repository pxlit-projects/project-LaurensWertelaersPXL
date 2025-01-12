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
public class NewsArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String usernameWriter;
    private LocalDateTime creationDate;
}
