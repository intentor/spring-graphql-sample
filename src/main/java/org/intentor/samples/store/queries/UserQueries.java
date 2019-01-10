package org.intentor.samples.store.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL user queries.
 */
@Component
public class UserQueries implements GraphQLQueryResolver {
    /**
     * Repository for accessing users data.
     */
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> allUsers() {
        return userRepository.findAll();
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("user", username));
    }
}
