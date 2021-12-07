package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.RoleAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.RoleNotFoundException;

import java.util.Collection;

public interface RoleManager {

    Collection<Role> readAll();

    hu.uni.eku.tzs.model.Role readById(int id) throws RoleNotFoundException;

    hu.uni.eku.tzs.model.Role modify(hu.uni.eku.tzs.model.Role role);

    void delete(hu.uni.eku.tzs.model.Role role) throws RoleNotFoundException;

    hu.uni.eku.tzs.model.Role record(hu.uni.eku.tzs.model.Role role)
            throws RoleAlreadyExistsException;
}
