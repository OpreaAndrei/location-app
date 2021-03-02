package org.scd.repository;

import java.util.Set;
import org.scd.model.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Set<Role> findByRole(String role);
}
