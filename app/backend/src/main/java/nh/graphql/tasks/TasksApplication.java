package nh.graphql.tasks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ServletComponentScan
public class TasksApplication {

  public static void main(String[] args) {
    SpringApplication.run(TasksApplication.class, args);
  }

  @Bean
  public CommandLineRunner importInitialData(Importer importer) {
    return args -> importer.add();
  }

  @Bean
  WebMvcConfigurer webConfig() {
    return new WebMvcConfigurer() {
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true);
      }
    };
  }

  /**
   * RestTemplate needed for accessing the remote USerService
   * 
   * @param builder
   * @return
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }
}
