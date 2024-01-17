package code.shubham.app.documentstore.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "documents",
		uniqueConstraints = { @UniqueConstraint(name = "UniqueOwnerAndName", columnNames = { "owner", "name" }) },
		indexes = { @Index(name = "index_documents_owner", columnList = "owner") })
public class Document extends BaseAbstractAuditableEntity {

	@Column(nullable = false, length = 24)
	private String name;

	@Column(nullable = false)
	private String owner;

	@Column(nullable = false)
	private String blobId;

}
