package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Acheteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Acheteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcheteurRepository extends JpaRepository<Acheteur, Long> {

}
