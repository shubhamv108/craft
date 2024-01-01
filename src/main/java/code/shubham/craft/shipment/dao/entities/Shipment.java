package code.shubham.craft.shipment.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "shipments", indexes = { @Index(name = "index_shipments_orderId", columnList = "orderId") })
public class Shipment extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String orderId; // partition key

	private String deliveryAddress;

	@Column(length = 40)
	private String trackingNumber;

	@Column
	private String carrier;

	@Enumerated(EnumType.STRING)
	private ShipmentStatus status;

	@Column(nullable = false, unique = true)
	private String uniqueReferenceId; // Sort key

}
