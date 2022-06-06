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

public class PollDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private AppUser disliker;

    @CreationTimestamp
    private LocalDateTime dislikedAt;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Poll poll;

    public PollDislike(AppUser disliker, Poll poll){
        this.disliker = disliker;
        this.poll = poll;
    }

}
