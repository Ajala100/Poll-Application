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
    private Long userId;
    private Long optionId;
}
