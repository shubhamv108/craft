package code.shubham.craft.productorder.doa.repositories;

import code.shubham.craft.productorder.doa.entities.CustomerTypeProduct;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTypeProductRepository extends JpaRepository<CustomerTypeProduct, String> {

	List<CustomerTypeProduct> findAllByCustomerType(String customerType);

}
