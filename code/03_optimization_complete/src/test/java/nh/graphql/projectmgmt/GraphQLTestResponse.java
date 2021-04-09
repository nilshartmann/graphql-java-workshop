package nh.graphql.projectmgmt;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class GraphQLTestResponse {
  private final int returnCode;
  private final String payload;
  private final ReadContext context;

  GraphQLTestResponse(int returnCode, String payload) {
    this.returnCode = returnCode;
    this.payload = payload;
    this.context = JsonPath.parse(payload);
  }

  public boolean isOk() {
    return this.returnCode == 200;
  }

  public String getPayload() {
    return payload;
  }

  public String get(String path) {
    return context.read(path);
  }

  public int getInt(String path) {
    return context.read(path);
  }

  @Override
  public String toString() {
    return "GraphQLTestResponse [returnCode=" + returnCode + ", payload=" + payload + "]";
  }

}
