package africa.semicolon.pollapplication.payLoad.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder

public class FindPollInteractionByUserResponse {

    private Long pollInteractionId;
    private String pollId;
    private Long optionId;
}
