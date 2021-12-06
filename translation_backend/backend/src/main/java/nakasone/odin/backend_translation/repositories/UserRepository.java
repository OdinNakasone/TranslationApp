package nakasone.odin.backend_translation.repositories;

import nakasone.odin.backend_translation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
