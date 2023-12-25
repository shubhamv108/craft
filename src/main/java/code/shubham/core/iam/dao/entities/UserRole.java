package code.shubham.core.iam.dao.entities;

import code.shubham.commons.dao.entities.base.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users_roles")
public class UserRole extends BaseIdEntity {

	@Column(nullable = false)
	private String userId;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	private Role role;

}