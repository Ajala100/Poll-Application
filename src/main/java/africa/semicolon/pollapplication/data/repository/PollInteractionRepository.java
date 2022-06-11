package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollInteractionRepository extends JpaRepository<PollInteraction, Long> {
    Optional<PollInteraction> findPollInteractionByUserAndPoll(AppUser user, Poll poll);
}
