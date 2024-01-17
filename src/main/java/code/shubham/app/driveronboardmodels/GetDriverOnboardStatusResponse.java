package code.shubham.app.driveronboardmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GetDriverOnboardStatusResponse {

	private List<DriverOnboardDTO> onboards;

}
