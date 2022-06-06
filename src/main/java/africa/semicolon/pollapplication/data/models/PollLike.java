package africa.semicolon.pollapplication.data.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class PollLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private AppUser liker;

    @CreationTimestamp
    private LocalDateTime likedAt;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private Poll poll;

}
