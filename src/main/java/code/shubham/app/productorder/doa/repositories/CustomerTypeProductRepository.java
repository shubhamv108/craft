package code.shubham.app.productorder.doa.repositories;

import code.shubham.app.productorder.doa.entities.CustomerTypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTypeProductRepository extends JpaRepository<CustomerTypeProduct, String> {

	List<CustomerTypeProduct> findAllByCustomerType(String customerType);

}
