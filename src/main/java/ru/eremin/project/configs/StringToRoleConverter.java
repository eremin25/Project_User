package ru.eremin.project.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import ru.eremin.project.models.Role;
import ru.eremin.project.services.RoleService;

public class StringToRoleConverter implements Converter<String, Role> {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String role) {
        return roleService.findRoleByName(role);
    }
}
