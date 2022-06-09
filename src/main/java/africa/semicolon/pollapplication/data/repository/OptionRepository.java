package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
