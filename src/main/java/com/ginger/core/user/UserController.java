package com.ginger.core.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserRepository userRepository;

    @PostMapping("create")
    public ResponseEntity<DefaultUserOutput> create(@RequestBody CreateUserInput input) {
        User user = userRepository.save(input.toUser());
        return ResponseEntity.ok(new DefaultUserOutput().fromUser(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<DefaultUserOutput> getOne(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new DefaultUserOutput().fromUser(user.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DefaultUserOutput>> all() {
        List<User> users = userRepository.findAll();
        if (users != null && !users.isEmpty()) {
            return ResponseEntity
                    .ok(users.stream().map(u -> new DefaultUserOutput().fromUser(u))
                            .collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DefaultUserOutput> delete(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
