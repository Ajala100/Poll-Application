package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.PollComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollCommentRepository extends JpaRepository<PollComment, Long> {
}
