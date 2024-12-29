package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "newsarticle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String usernameWriter;
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;
}
