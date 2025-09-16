package online.rabko.basketball.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import online.rabko.api.UsersApi;
import online.rabko.basketball.controller.mapper.UserMapper;
import online.rabko.basketball.entity.User;
import online.rabko.basketball.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for {@code /users} endpoints. Implements {@link UsersApi}.
 */
@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<online.rabko.model.User>> usersGet() {
        return ResponseEntity.ok(
            userServiceImpl.findAll().stream()
                .map(userMapper::toDto)
                .toList()
        );
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.User> usersIdGet(Long id) {
        return ResponseEntity.ok(userMapper.toDto(userServiceImpl.findById(id)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<online.rabko.model.User> usersIdPut(Long id,
        online.rabko.model.User dto) {
        User toUpdate = userMapper.toEntity(dto);
        toUpdate.setId(id);
        User updated = userServiceImpl.update(id, toUpdate);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> usersIdDelete(Long id) {
        userServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}
