package code.shubham.craft.backgroundverification.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "background_verification",
		indexes = { @Index(name = "index_background_verification_user_id", columnList = "userId") })
public class BackgroundVerification extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String applicantId;

	@Column(nullable = false)
	private String applicantType;

	@Enumerated(EnumType.STRING)
	private BackgroundVerificationStatus status;

	@Column(nullable = false)
	private String userId; // Partition Key - DynamoDB

	@Column(nullable = false, unique = true)
	private String clientReferenceId; // Sort Key - DynamoDB

}
