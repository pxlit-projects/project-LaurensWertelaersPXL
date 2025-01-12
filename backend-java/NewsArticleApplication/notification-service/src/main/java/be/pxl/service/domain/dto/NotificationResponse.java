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
public class NotificationResponse {
    private Long id;

    private Long newsArticleId;
    private String receiverUsernameWriter;
    private String senderUsernameEditor;

    private String approvedOrDisapproved;
    private String remark;
    private LocalDateTime creationDate;
}
