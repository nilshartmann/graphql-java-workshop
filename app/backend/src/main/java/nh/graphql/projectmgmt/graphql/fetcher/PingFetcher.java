package nh.graphql.projectmgmt.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PingFetcher {

  private static String currentTime() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
  }

}
