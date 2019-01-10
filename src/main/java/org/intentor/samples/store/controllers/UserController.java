package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.intentor.samples.store.Constants.MAX_PAGE_SIZE;
import static org.intentor.samples.store.controllers.helpers.PaginationHelper.paginate;
import static org.springframework.http.ResponseEntity.*;

/**
 * API for users related operations.
 */
@RestController
@RequestMapping(UserController.MAIN_ROUTE)
public class UserController {
    /**
     * Main controller route.
     */
    public static final String MAIN_ROUTE = "/api/users";
    /**
     * Controller entity name.
     */
    public static final String ENTITY_NAME = "user";

    /**
     * Repository for accessing users data.
     */
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        userRepository.save(user);
        return created(URI.create("/users/" + user.getUsername())).build();
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<?> findById(@PathVariable("username") String username) {
        return userRepository.findByUsername(username)
                .map(entity -> ok(entity))
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, username));
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, userRepository::findAll);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody User user) {
        return userRepository.findByUsername(username)
                .map(entity -> {
                    entity.setFullname(user.getFullname());
                    userRepository.save(entity);

                    return ok(entity);
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        return userRepository.findByUsername(username)
                .map(entity -> {
                    userRepository.delete(entity);
                    return noContent().build();
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, username));
    }
}
