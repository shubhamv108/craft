package code.shubham.craft.driver.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "drivers")
public class Driver extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String drivingLicenseName;

	@Column(nullable = false, unique = true)
	private String drivingLicense;

	@Column(nullable = false)
	private DriverStatus status;

	@Column
	private String activeCabId;

	@Column(nullable = false, unique = true, updatable = false)
	private String userId;

}
