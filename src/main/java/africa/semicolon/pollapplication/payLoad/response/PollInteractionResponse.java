package africa.semicolon.pollapplication.payLoad.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PollInteractionResponse {

    private Long pollInteractionId;
    private Long optionId;
    private String option;
}
