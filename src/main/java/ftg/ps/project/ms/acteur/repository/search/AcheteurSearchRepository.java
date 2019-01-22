package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Acheteur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Acheteur entity.
 */
public interface AcheteurSearchRepository extends ElasticsearchRepository<Acheteur, Long> {
}
