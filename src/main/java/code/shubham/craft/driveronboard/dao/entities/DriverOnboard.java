package code.shubham.craft.driveronboard.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "driver_onboard")
public class DriverOnboard extends BaseAbstractAuditableEntity {

	@Enumerated(EnumType.STRING)
	private DriverOnboardStatus status;

	private String driverId;

	@Column(unique = true)
	private String userId;

	@Column(unique = true)
	private String clientReferenceId;

}
