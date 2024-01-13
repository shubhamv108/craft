package code.shubham.commons.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(name = "Shubham Varshney", email = "thread.shubham@gmail.com",
						url = "http://github.com/shubhamv108"),
				description = "OpenAPI documentation for application", title = "CraftApplication", version = "1.0"),
		servers = { @Server(description = "Local", url = "http://localhost:8080/api") })
@SecurityScheme(name = "BearerAuth", description = "JWTAuthDescription", scheme = "Bearer",
		type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenAPIConfiguration {

}
