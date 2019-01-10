package org.intentor.samples.store.repositories;

import org.intentor.samples.store.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Repository for accessing {@link User} data.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    /**
     * Finds a user by username.
     *
     * @param username Username to look for.
     * @return User with the given username or {@literal Optional#empty()} if none found.
     */
    Optional<User> findByUsername(String username);
}
