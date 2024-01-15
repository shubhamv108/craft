package code.shubham.core.iammodels;

import code.shubham.core.iam.dao.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Builder
@Getter
@Setter
public class GetUserResponse {

	private UserDTO user;

	private Collection<String> roles;

}