package manga.novel.mmmn.api.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import manga.novel.mmmn.api.properties.ApplicationProperties;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/swagger-ui/**")
				.antMatchers("/swagger-ui.html")
				.antMatchers("/oauth2-logout");
	}

	@Bean
	public JwtAuthenticationConverter jwtConverter() {
		var converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter((jwt) -> {
			@SuppressWarnings("unchecked")
			var groups = (List<String>) jwt.getClaims().get("groups");
			if (groups == null) {
				return List.of();
			}

			return groups.stream()
					.map(r -> new SimpleGrantedAuthority("ROLE_" + StringUtils.strip(r, "/")))
					.collect(Collectors.toList());
		});
		return converter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable().and().requestCache()
				.requestCache(new NullRequestCache());

		http.authorizeRequests()
				.antMatchers("/about/bearer/time/**")
				.permitAll()
		;

		http.authorizeRequests()
				.antMatchers("/v3/api-docs/crud", "/v3/api-docs/api-public").hasRole("Administrateur")
				.antMatchers("/v3/api-docs/**").permitAll()
				.antMatchers("/administration/**")
				.hasRole("Administrateur")
				.antMatchers("/actuator/**")
				.hasRole("Administrateur")
				.antMatchers("/groupe/automatiques/**")
				.hasRole("Administrateur")
				.antMatchers("/about/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(jwtConverter());
	}

}
