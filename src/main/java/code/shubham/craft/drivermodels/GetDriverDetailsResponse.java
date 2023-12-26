package code.shubham.craft.drivermodels;

import code.shubham.craft.driver.cab.dao.entities.Cab;
import code.shubham.craft.driver.cabmodels.CabDTO;
import code.shubham.craft.driver.dao.entities.Driver;
import code.shubham.craft.driver.dao.entities.DriverStatus;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetDriverDetailsResponse {

	private String driverId;

	private String drivingLicenseName;

	private String drivingLicense;

	private String userId;

	private String status;

	private String activeCabId;

	private List<CabDTO> cabs;

}
