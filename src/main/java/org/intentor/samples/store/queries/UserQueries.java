package org.intentor.samples.store.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.UserRepository;
import org.intentor.samples.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL user queries.
 */
@Component
public class UserQueries implements GraphQLQueryResolver {
    /**
     * User manipulation service.
     */
    @Autowired
    private UserService userService;

    /**
     * Finds all users.
     *
     * @return Users found.
     */
    public Iterable<User> allUsers() {
        return userService.findAll();
    }

    /**
     * Finds a user by its username.
     *
     * @param username Username of the user to find.
     * @return User found.
     * @throws DataNotFoundException in case no user is found.
     */
    public User findUser(String username) {
        return userService.findByUsername(username);
    }
}
