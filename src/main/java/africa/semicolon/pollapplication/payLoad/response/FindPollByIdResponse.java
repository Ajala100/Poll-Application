package africa.semicolon.pollapplication.payLoad.response;

import africa.semicolon.pollapplication.data.models.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FindPollByIdResponse {
    private String pollId;
    private String pollCreatorId;
    private String question;
    private List<Option> options;
    private int commentCount;
    private int likeCount;
    private int dislikeCount;
    private String expiresAt;
    private List<CreatePollCommentResponse> pollComments;


    public FindPollByIdResponse(String pollId, String pollCreatorId, String question, List<Option> options, int commentCount,
                                int likeCount, int dislikeCount, List<CreatePollCommentResponse> pollComments){
        this.pollId = pollId;
        this.pollCreatorId = pollCreatorId;
        this.question = question;
        this.options = options;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.pollComments = pollComments;
    }
}
