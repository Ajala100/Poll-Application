package africa.semicolon.pollapplication.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class PollInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private AppUser user;

    @OneToOne
    private Poll poll;

    @OneToOne
    private Option option;

    public PollInteraction(AppUser user, Poll poll, Option option){
        this.user = user;
        this.poll = poll;
        this.option = option;
    }
}
