package africa.semicolon.pollapplication.payLoad.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class PollCommentResponse {
    private Long commentId;
    private Long commenterId;
    private String comment;
    private Long pollId;
    private LocalDateTime createdAt;
}
