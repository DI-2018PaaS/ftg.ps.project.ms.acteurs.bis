package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Banque;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Banque entity.
 */
public interface BanqueSearchRepository extends ElasticsearchRepository<Banque, Long> {
}
