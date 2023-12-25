package code.shubham.craft.driveronboardmodels;

import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetDriverOnboardStatusResponse {

	private List<DriverOnboardDTO> onboards;

}
