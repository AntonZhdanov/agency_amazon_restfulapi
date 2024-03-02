package com.example.agency_amazon_restfulapi.initdata;

import com.example.agency_amazon_restfulapi.model.Role;
import com.example.agency_amazon_restfulapi.repository.RoleRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@ChangeUnit(order = "001", id = "initializeRoles", author = "Anton Zhdanov")
public class DatabaseInitializer {
    private final MongoTemplate mongoTemplate;

    @Execution
    public void addInitialRoles(RoleRepository roleRepository) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Role.RoleName.ROLE_USER));
        roles.add(new Role(Role.RoleName.ROLE_ADMIN));

        roleRepository.insert(roles);
    }

    @RollbackExecution
    public void rollback(RoleRepository roleRepository) {
        roleRepository.deleteAll();
    }
}
