package be.pxl.services.domain.dto;

import be.pxl.services.domain.ArticleStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
