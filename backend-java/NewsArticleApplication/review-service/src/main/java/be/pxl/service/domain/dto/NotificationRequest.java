package be.pxl.service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private Long newsArticleId;
    private String receiverUsernameWriter;
    private String senderUsernameEditor;

    private String approvedOrDisapproved;
    private String remark;
}
