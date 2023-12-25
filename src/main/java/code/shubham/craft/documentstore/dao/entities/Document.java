package code.shubham.craft.documentstore.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "documents",
		uniqueConstraints = { @UniqueConstraint(name = "UniqueOwnerAndName", columnNames = { "owner", "name" }) })
public class Document extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String owner;

	@Column(nullable = false)
	private String blobId;

}
