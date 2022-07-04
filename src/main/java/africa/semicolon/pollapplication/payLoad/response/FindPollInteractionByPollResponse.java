package africa.semicolon.pollapplication.payLoad.response;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class FindPollInteractionByPollResponse {
    private Long pollInteractionId;
    private String userId;
    private Long optionId;
}
