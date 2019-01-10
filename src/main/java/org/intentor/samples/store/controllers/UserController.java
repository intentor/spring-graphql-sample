package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.services.UserService;
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
     * User manipulation service.
     */
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        userService.create(user);
        return created(URI.create("/users/" + user.getUsername())).build();
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, userService::findAll);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> update(@PathVariable("username") String username, @RequestBody User user) {
        user.setUsername(username);
        return ok(userService.update(user));
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<?> findById(@PathVariable("username") String username) {
        return ok(userService.findByUsername(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        userService.delete(username);
        return noContent().build();
    }
}
