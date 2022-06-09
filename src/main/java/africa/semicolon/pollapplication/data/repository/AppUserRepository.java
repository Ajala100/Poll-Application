package africa.semicolon.pollapplication.data.repository;

import africa.semicolon.pollapplication.data.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

}
