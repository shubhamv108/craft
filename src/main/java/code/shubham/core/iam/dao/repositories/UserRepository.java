package code.shubham.core.iam.dao.repositories;

import code.shubham.core.iam.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select * from users where email = ?", nativeQuery = true)
	Optional<User> findByEmail(String email);

}
