package code.shubham.craft.driveronboardmodels;

import code.shubham.craft.driveronboard.dao.entities.DriverOnboard;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GetDriverOnboardStatusResponse {

	private List<DriverOnboardDTO> onboards;

}
