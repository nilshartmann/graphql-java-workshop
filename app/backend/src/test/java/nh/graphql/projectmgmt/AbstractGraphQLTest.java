package nh.graphql.projectmgmt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractGraphQLTest {
    private static final Logger logger = LoggerFactory.getLogger(AbstractGraphQLTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    private ObjectMapper objectMapper = new ObjectMapper();

    protected String loadGraphQL(String name) {
        String resourceName = getClass().getPackageName().replace('.', '/') + "/" + name + ".graphQL";
        Resource resource = resourceLoader.getResource("classpath:" + resourceName);
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not load resource '" + resourceName + "' from classpath: " + ex, ex);
        }
    }

    protected GraphQLTestResponse execute(String graphqlQuery) {
        String jsonRequest = createJsonQuery(graphqlQuery);
        HttpEntity<Object> httpEntity = forJson(jsonRequest);

        ResponseEntity<String> response = restTemplate.exchange("/graphql", HttpMethod.POST, httpEntity, String.class);
        GraphQLTestResponse graphQLTestResponse = new GraphQLTestResponse(response.getStatusCodeValue(),
            response.getBody());
        logger.info("GRAPHQL RESPONSE ====>>> {}", graphQLTestResponse);

        return graphQLTestResponse;
    }

    // ALL FROM
    // https://github.com/graphql-java-kickstart/graphql-spring-boot/blob/master/graphql-spring-boot-test/src/main/java/com/graphql/spring/boot/test
    private String createJsonQuery(String graphql) {

        ObjectNode wrapper = objectMapper.createObjectNode();
        wrapper.put("query", graphql);
//  wrapper.set("variables", variables);
        try {
            return objectMapper.writeValueAsString(wrapper);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Could not create json: " + e, e);
        }
    }

    static HttpEntity<Object> forJson(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<>(json, headers);
    }
}
