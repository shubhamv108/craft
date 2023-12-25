package code.shubham.core.iam.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
public class Role extends BaseAbstractAuditableEntity {

	@NaturalId
	@NotBlank
	@Column(unique = true, nullable = false, length = 60)
	private String name;

}
