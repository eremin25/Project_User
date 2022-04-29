package ru.eremin.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.eremin.project.models.Role;
import ru.eremin.project.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    /*
    Получение списка всех ролей.
    */
    @Override
    public Set<Role> allRoles() {
        return roleRepository.findAll().stream().collect(Collectors.toSet());
    }


    /*
    Получение роли по ID.
    */
    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }


    /*
    Конвертация списка названия ролей(String) в список ролей(Role)
    */
    @Override
    public Set<Role> convertingSetOfStringsToSetOfRoles(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String name : roleNames) {
            roles.add(findRoleByName(name));
        }
        return roles;
    }
}
