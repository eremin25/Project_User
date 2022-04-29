package ru.eremin.project.services;

import ru.eremin.project.models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> allRoles();

    Role findRoleByName(String name);

    Set<Role> convertingSetOfStringsToSetOfRoles(Set<String> roleNames);
}
