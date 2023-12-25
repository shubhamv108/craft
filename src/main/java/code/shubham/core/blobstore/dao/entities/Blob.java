package code.shubham.core.blobstore.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "blobs")
public class Blob extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String bucket;

	@Column(nullable = false)
	private String keyName;

	@Column(nullable = false)
	private String owner;

	public String getFullKey() {
		return keyName + "/" + getId();
	}

}
