package ftg.ps.project.ms.acteur.repository;

import ftg.ps.project.ms.acteur.domain.Agreement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Agreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

}
