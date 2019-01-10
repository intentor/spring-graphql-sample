package org.intentor.samples.store.services;

import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service to manage user data.
 */
@Service
public class UserService {
    /**
     * Controller entity name.
     */
    private static final String ENTITY_NAME = "user";

    /**
     * Repository for accessing users data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user User to be created.
     * @return Created user, with ID populated.
     */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Finds all users.
     *
     * @return Users found.
     */
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Finds all users.
     *
     * @param pageable Pagination details.
     * @return Users found.
     */
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Finds a user by its username.
     *
     * @param username Username of the user to find.
     * @return User found.
     * @throws DataNotFoundException in case no user is found.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, username));
    }

    /**
     * Updates a user.
     *
     * @param user User data to be updated.
     * @return Updated user.
     * @throws DataNotFoundException in case no user is found.
     */
    public User update(User user) {
        var entity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, user.getUsername()));

        entity.setFullname(user.getFullname());
        return userRepository.save(entity);
    }

    /**
     * Deletes a user.
     *
     * @param username Username of the user to delete.
     * @throws DataNotFoundException in case no user is found.
     */
    public void delete(String username) {
        var entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, username));

        userRepository.delete(entity);
    }
}
