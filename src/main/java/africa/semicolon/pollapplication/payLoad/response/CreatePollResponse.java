package africa.semicolon.pollapplication.payLoad.response;

import africa.semicolon.pollapplication.data.models.Option;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CreatePollResponse {
    private Long pollId;
    private Long pollCreatorId;
    private String question;
    private List<Option> options;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;
    private List<CreatePollCommentResponse> pollComments;
    private String duration;
}
