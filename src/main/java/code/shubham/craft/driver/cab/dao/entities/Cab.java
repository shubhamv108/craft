package code.shubham.craft.driver.cab.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cabs")
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
