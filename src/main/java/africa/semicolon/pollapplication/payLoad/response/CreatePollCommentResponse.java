package africa.semicolon.pollapplication.payLoad.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder

public class CreatePollCommentResponse {
    private Long pollCommentId;
    private String pollId;
    private String comment;
    private String commenterId;
    private LocalDateTime createdAt;
}
