package be.pxl.service.domain.dto;

import be.pxl.service.domain.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleRequest {
    private String title;
    private String content;
    private String usernameWriter;
}
