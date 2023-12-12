package com.expert.test.helpers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.expert.test.models.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GitHubService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);

    private final AtomicLong counter = new AtomicLong(0);

    private static final String URL = "https://api.github.com/users";
    
    public boolean checkUserExist(String userName) {
        long started = System.currentTimeMillis();
        boolean exist = false;
        HttpSingleton.getInstance();
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        try {
            HttpRequest request = HttpRequest.newBuilder().GET()
            .uri(new URI(userName == null ? URL : URL + "/" + userName))
            .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            exist = response.statusCode() == 200;
            final Long invocationNumber = counter.getAndIncrement();
            logger.info("GitHubService#checkUserExist() invocation #%d returning successfully | #%d timed out after %d ms", invocationNumber, invocationNumber, System.currentTimeMillis() - started);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return exist;
    }

    public User getGitHubUser(String userName) {
        long started = System.currentTimeMillis();
        User user = new User();
        HttpSingleton.getInstance();
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        try {
            HttpRequest request = HttpRequest.newBuilder().GET()
            .uri(new URI(userName == null ? URL : URL + "/" + userName))
            .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode mapper = new ObjectMapper().readTree(response.body());
            user.setUserName(userName);
            user.setFullName(mapper.get("name").asText());
            user.setUrlImageProfile(mapper.get("avatar_url").asText());
            final Long invocationNumber = counter.getAndIncrement();
            logger.info("GitHubService#checkUserExist() invocation #%d returning successfully | #%d timed out after %d ms", invocationNumber, invocationNumber, System.currentTimeMillis() - started);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

}
