package code.shubham.commons;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class EntityManagerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void truncateTable(final String tableName) {
		String sql = "TRUNCATE TABLE " + tableName;
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}

}
