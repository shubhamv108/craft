package code.shubham.app.driveronboard.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "driver_onboard", indexes = { @Index(name = "index_driver_onboard_driver_id", columnList = "driverId"),
		@Index(name = "index_driver_onboard_user_id", columnList = "userId") })
public class DriverOnboard extends BaseAbstractAuditableEntity {

	@Enumerated(EnumType.STRING)
	private DriverOnboardStatus status;

	@Column(nullable = false)
	private String driverId;

	@Column(nullable = false)
	private String userId;

	@Column(unique = true)
	private String clientReferenceId;

}
