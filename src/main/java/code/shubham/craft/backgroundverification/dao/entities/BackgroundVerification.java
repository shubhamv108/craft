package code.shubham.craft.backgroundverification.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "background_verification")
public class BackgroundVerification extends BaseAbstractAuditableEntity {

	@Column(unique = true, nullable = false)
	private String applicantId;

	@Column(nullable = false)
	private String applicantType;

	@Enumerated(EnumType.STRING)
	private BackgroundVerificationStatus status;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false, unique = true)
	private String clientReferenceId;

}
