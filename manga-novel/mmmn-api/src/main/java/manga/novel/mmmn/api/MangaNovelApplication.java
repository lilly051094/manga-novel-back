package manga.novel.mmmn.api;

import manga.novel.mmmn.api.properties.ApplicationProperties;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(
		scanBasePackages = {
				"manga.novel.mmmn.api.*",
				"manga.novel.mmmn.business.*",
				"manga.novel.mmmn.commons.*",
				"manga.novel.mmmn.persistence.*",
		}
)
@EnableAutoConfiguration(exclude = H2ConsoleAutoConfiguration.class)
@EnableMongoRepositories(basePackages = {"manga.novel.mmmn.persistence.repository"}) // // Chargement des repository Spring et association avec les composants de persistance cibles
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableWebMvc //It enables support for @Controller-annotated classes that use @RequestMapping to map incoming requests to a certain method.
@EnableTransactionManagement
public class MangaNovelApplication extends SpringBootServletInitializer {

	/**
	 * Methode principale de lancement des services Rest.
	 *
	 * @param args les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
		SpringApplication gestionApplication = new SpringApplication(MangaNovelApplication.class);
		gestionApplication.setBannerMode(Banner.Mode.OFF);
		gestionApplication.run();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MangaNovelApplication.class).bannerMode(Banner.Mode.OFF);
	}

}