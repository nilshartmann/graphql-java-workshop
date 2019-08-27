package nh.graphql.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger logger = LoggerFactory.getLogger(TasksApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(TasksApplication.class, args);
  }

  @Bean
  public CommandLineRunner importInitialData(Importer importer, RxStreamsTaskSubscriber taskSubscriber,
      GraphQLTaskSubscriber graphQLTaskSubscriber) {
    return args -> {
      logger.info("====== IMPORTING DATA ======= ");
      importer.add();

      logger.info("====== START 'PLAIN' LISTENER ======= ");
      taskSubscriber.subscribe();

      logger.info("====== START 'GRAPHQL' LISTENER ======= ");
      graphQLTaskSubscriber.executeAndSubscribe();

    };

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
