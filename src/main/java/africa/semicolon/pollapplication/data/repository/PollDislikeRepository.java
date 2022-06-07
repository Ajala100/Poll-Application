package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollDislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollDislikeRepository extends JpaRepository<PollDislike, Long> {
    Optional<PollDislike> findPollDislikeByPollAndDisliker(Poll poll, AppUser disliker);
    Optional<List<PollDislike>> findPollDislikeByDisliker(AppUser disliker);
}
