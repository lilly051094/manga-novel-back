package manga.novel.mmmn.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private String externalUrl;
	private KeycloakProperties keycloak;
	private String environment;

}
