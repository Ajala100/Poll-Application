package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PollLikeRepository extends JpaRepository<PollLike, Long> {
    Optional<PollLike> findPollLikeByLikerAndPoll(AppUser liker, Poll poll);
    Optional<List<PollLike>> findPollLikeByLiker(AppUser liker);
}
