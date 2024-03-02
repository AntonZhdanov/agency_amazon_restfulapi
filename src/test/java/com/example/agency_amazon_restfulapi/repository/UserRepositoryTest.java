package com.example.agency_amazon_restfulapi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.agency_amazon_restfulapi.model.Role;
import com.example.agency_amazon_restfulapi.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("anton@example.com");
        user.setPassword("Apassword!");
        user.setName("Anton");
        user.setLastName("Zhdanov");
        user.setRoles(new HashSet<>(List.of(new Role(Role.RoleName.ROLE_USER))));

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail_shouldReturnUserForExistingEmail() {
        Optional<User> foundUser = userRepository.findByEmail("anton@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    void findByEmail_shouldReturnEmptyOptionalForNonExistingEmail() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void save_shouldPersistUserWithNewEmail() {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setPassword("NewPassword!");
        newUser.setName("New");
        newUser.setLastName("User");
        newUser.setRoles(new HashSet<>(List.of(new Role(Role.RoleName.ROLE_USER))));

        User savedUser = userRepository.save(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
    }

    @Test
    void save_shouldUpdateExistingUser() {
        user.setName("UpdatedName");
        User updatedUser = userRepository.save(user);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getId()).isEqualTo(user.getId());
        assertThat(updatedUser.getName()).isEqualTo("UpdatedName");
    }

    @Test
    void save_shouldNotAllowDuplicateEmails() {
        User newUser = new User();
        newUser.setEmail("anton@example.com");
        newUser.setPassword("NewPassword!");
        newUser.setName("New");
        newUser.setLastName("User");
        newUser.setRoles(new HashSet<>(List.of(new Role(Role.RoleName.ROLE_USER))));

        assertThrows(DuplicateKeyException.class, () -> userRepository.save(newUser));
    }

    @Test
    void delete_shouldRemoveUser() {
        userRepository.delete(user);
        Optional<User> foundUser = userRepository.findByEmail("anton@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User anotherUser = new User();
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("AnotherPassword!");
        anotherUser.setName("Another");
        anotherUser.setLastName("User");
        anotherUser.setRoles(new HashSet<>(List.of(new Role(Role.RoleName.ROLE_USER))));

        userRepository.save(anotherUser);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).containsExactlyInAnyOrder(user, anotherUser);
    }
}
