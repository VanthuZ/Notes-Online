package pl.vanthus.notesonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vanthus.notesonline.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
