package africa.semicolon.pollapplication.data.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class PollComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Comment cannot be blank")
    private String comment;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @NotNull(message = "AppUser cannot be blank")
    private AppUser commenter;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Poll poll;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public PollComment(String comment, AppUser commenter, Poll poll){
        this.comment = comment;
        this.commenter = commenter;
        this.poll = poll;
    }

}
