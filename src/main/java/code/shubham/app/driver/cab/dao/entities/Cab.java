package code.shubham.app.driver.cab.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cabs", indexes = { @Index(name = "index_cabs_driver_id", columnList = "driverId"),
		@Index(name = "index_cabs_user_id", columnList = "userId") })
public class Cab extends BaseAbstractAuditableEntity {

	@Column(nullable = false, unique = true)
	private String registrationNumber;

	@Column(nullable = false)
	private String color;

	@Column(nullable = false)
	private String driverId;

	@Column(nullable = false)
	private String userId;

}
