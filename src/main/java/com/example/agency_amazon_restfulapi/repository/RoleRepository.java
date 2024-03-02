package com.example.agency_amazon_restfulapi.repository;

import com.example.agency_amazon_restfulapi.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findRoleByRoleName(Role.RoleName roleName);
}
