package code.shubham.craft.documentstore.dao.repositories;

import code.shubham.craft.documentstore.dao.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

	List<Document> findAllByOwner(String owner);

	Optional<Document> findByOwnerAndName(String owner, String name);

}
