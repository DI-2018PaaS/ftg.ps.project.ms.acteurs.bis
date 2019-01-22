package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Agreement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Agreement entity.
 */
public interface AgreementSearchRepository extends ElasticsearchRepository<Agreement, Long> {
}
