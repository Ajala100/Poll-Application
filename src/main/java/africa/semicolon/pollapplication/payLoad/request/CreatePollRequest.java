package africa.semicolon.pollapplication.payLoad.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePollRequest {
    private List<String> options;

    private String question;

    private int durationInMinutes;

    public CreatePollRequest(List<String> options, String question){
        this.options = options;
        this.question = question;
    }
}
