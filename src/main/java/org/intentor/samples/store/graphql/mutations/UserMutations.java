package org.intentor.samples.store.graphql.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL user mutations.
 */
@Component
public class UserMutations implements GraphQLMutationResolver {
    /**
     * User manipulation service.
     */
    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     *
     * @param username Username of the new user.
     * @param fullname Fullname of the new user.
     * @return Created user, with ID populated.
     */
    public User createUser(String username, String fullname) {
        var user = new User();
        user.setUsername(username);
        user.setFullname(fullname);
        return userService.create(user);
    }

    /**
     * Updates a user.
     *
     * @param username Username of the user to be updated.
     * @param fullname New user's fullname.
     * @return Updated user.
     */
    public User updateUser(String username, String fullName) {
        var user = new User();
        user.setUsername(username);
        user.setFullname(fullName);
        return userService.update(user);
    }

    /**
     * Deletes a user.
     *
     * @param username Username of the user to delete.
     */
    public boolean deleteUser(String username) {
        userService.delete(username);
        return true;
    }
}
