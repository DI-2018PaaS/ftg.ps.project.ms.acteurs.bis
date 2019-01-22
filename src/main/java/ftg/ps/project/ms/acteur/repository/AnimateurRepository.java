package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Animateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Animateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimateurRepository extends JpaRepository<Animateur, Long> {

}
