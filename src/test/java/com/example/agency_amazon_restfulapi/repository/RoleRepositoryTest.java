package com.example.agency_amazon_restfulapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.agency_amazon_restfulapi.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataMongoTest
@ActiveProfiles("test")
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    private Role roleUser;
    private Role roleAdmin;

    @BeforeEach
    void setUp() {
        roleUser = new Role(Role.RoleName.ROLE_USER);
        roleAdmin = new Role(Role.RoleName.ROLE_ADMIN);

        roleRepository.saveAll(List.of(roleUser, roleAdmin));
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void findRoleByRoleName_shouldReturnRoleForExistingRoleName() {
        Optional<Role> foundRole = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER);
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo(Role.RoleName.ROLE_USER);
    }

    @Test
    void findRoleByRoleName_shouldReturnEmptyOptionalForNonExistingRoleName() {
        String nonExistentRoleName = "ROLE_NON_EXISTENT";
        boolean roleExists = Arrays.stream(Role.RoleName.values())
                .anyMatch(roleName -> roleName.name().equals(nonExistentRoleName));

        Optional<Role> foundRole = roleExists
                ? roleRepository.findRoleByRoleName(Role.RoleName.valueOf(nonExistentRoleName))
                : Optional.empty();

        assertThat(foundRole).isEmpty();
    }

    @Test
    void findAll_shouldReturnAllRoles() {
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(2);
        assertThat(roles).containsExactlyInAnyOrder(roleUser, roleAdmin);
    }
}
