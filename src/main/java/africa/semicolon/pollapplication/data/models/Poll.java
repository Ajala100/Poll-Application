package africa.semicolon.pollapplication.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Poll {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "question cannot be blank")
    private String question;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private AppUser pollCreator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Option> options;

    @JsonIgnore
    private Integer selectedOptionsCount = 0;

    private Integer commentCount = 0;

    private Integer likeCount = 0;

    private Integer dislikeCount = 0;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PollComment> comments;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Poll(String question, List<Option> listOfOptions, AppUser pollCreator){

        this.question = question;
        this.options = listOfOptions;
        this.pollCreator = pollCreator;
    }
}
