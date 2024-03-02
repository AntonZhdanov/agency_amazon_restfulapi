package com.example.agency_amazon_restfulapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;
    private RoleName roleName;

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN
    }
}
