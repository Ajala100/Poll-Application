package africa.semicolon.pollapplication.payLoad.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePollCommentRequest {
    @NotBlank(message = "comment cannot be blank")
    private String comment;

    @NotBlank(message = "poll Id cannot be blank")
    private Long pollId;

    @NotBlank(message = "commenter Id cannot be null")
    private Long commenterId;
}
