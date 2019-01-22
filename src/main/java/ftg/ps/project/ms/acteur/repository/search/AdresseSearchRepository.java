package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Adresse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Adresse entity.
 */
public interface AdresseSearchRepository extends ElasticsearchRepository<Adresse, Long> {
}
