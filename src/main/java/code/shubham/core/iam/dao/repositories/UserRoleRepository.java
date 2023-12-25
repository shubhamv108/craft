package code.shubham.core.iam.dao.repositories;

import code.shubham.core.iam.dao.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {

	List<UserRole> findByUserId(String userId);

}
