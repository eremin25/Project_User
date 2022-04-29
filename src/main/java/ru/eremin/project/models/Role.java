package ru.eremin.project.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name = "role")
    private String name;

    public Role() {

    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
