package africa.semicolon.pollapplication.payLoad.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class PollLikeAndUnlikeResponse {
    private Long id;
    private Long userId;
    private Long pollId;
    private LocalDateTime createdAt;

}
