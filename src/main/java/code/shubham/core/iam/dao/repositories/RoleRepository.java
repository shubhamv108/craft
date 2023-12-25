package code.shubham.core.iam.dao.repositories;

import code.shubham.core.iam.dao.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	@Query(value = "select * from roles limit 10", nativeQuery = true)
	Set<Role> findAnyTen();

	Optional<Role> findByName(String name);

}
