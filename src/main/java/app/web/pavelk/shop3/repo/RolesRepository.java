package app.web.pavelk.shop3.repo;

import app.web.pavelk.shop3.entity.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {
}
