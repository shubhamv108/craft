package code.shubham.app.drivermodels;

import code.shubham.app.driver.cabmodels.CabDTO;
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
