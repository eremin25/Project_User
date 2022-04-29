package ru.eremin.project.services;

import ru.eremin.project.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> allRoles();

    Role findRoleByName(String name);

    Set<Role> convertingSetOfStringsToSetOfRoles(Set<String> roleNames);
}
