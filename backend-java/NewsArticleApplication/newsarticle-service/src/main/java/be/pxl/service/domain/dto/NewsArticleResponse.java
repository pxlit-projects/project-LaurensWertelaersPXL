package be.pxl.service.domain.dto;

import be.pxl.service.domain.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleResponse {
    private Long id;
    private String title;
    private String content;
    private String usernameWriter;
    private Date creationDate;
    private ArticleStatus status;
}
