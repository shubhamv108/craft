package code.shubham.craft.driveronboard.dao.entities;

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
@Table(name = "driver_onboard__orders_mapping")
public class DriverOnboardBackgroundVerification extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String driverOnboardId;

	@Column(unique = true)
	private String backgroundVerificationReferenceId;

}
