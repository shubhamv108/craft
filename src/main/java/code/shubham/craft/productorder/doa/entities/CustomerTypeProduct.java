package code.shubham.craft.productorder.doa.entities;

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
@Table(name = "customer_type__product_mapping")
public class CustomerTypeProduct extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String customerType;

	@Column(nullable = false)
	private String productId;

	@Builder.Default
	@Column(nullable = false)
	private int quantity = 1;

}
