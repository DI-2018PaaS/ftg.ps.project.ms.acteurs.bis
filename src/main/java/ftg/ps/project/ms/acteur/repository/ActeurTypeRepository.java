package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.ActeurType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ActeurType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActeurTypeRepository extends JpaRepository<ActeurType, Long> {

}
