package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, String> {
    Optional<List<Poll>> findPollByPollCreatorId(String pollCreatorId);
}
