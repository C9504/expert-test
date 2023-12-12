package com.expert.test.resources;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expert.test.helpers.GitHubService;
import com.expert.test.models.User;
import com.expert.test.services.UserService;

@RequestMapping("/api")
@RestController
@CrossOrigin //(origins = "*")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final AtomicLong counter = new AtomicLong(0);
    
    @Autowired
    private UserService userService;

    @Autowired
    private GitHubService gitHubService;

    @PostMapping("users")
    public ResponseEntity<String> saveUser(@RequestBody(required = true) User user) {
        long started = System.currentTimeMillis();
        User userFound = userService.findByUserName(user.getUserName());
        User userToSave = gitHubService.getGitHubUser(user.getUserName());
        if (gitHubService.checkUserExist(user.getUserName()) && userFound == null && userToSave != null) {
            user.setFullName(userToSave.getFullName());
            user.setUserName(userToSave.getUserName());
            user.setUrlImageProfile(userToSave.getUrlImageProfile());
            userService.save(user);
            final Long invocationNumber = counter.getAndIncrement();
            logger.info("UserResource#saveUser() invocation #%d returning successfully | #%d timed out after %d ms", invocationNumber, invocationNumber, System.currentTimeMillis() - started);
            return ResponseEntity.ok("Saved");            
        }
        final Long invocationNumber = counter.getAndIncrement();
        logger.error("UserResource#saveUser() invocation #%d returning not found | #%d timed out after %d ms", invocationNumber, invocationNumber, System.currentTimeMillis() - started);   
        return ResponseEntity.notFound().build();
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        long started = System.currentTimeMillis();
        final Long invocationNumber = counter.getAndIncrement();
        logger.info("UserResource#getAllUsers() invocation #%d returning successfully | #%d timed out after %d ms", invocationNumber, invocationNumber, System.currentTimeMillis() - started);
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
