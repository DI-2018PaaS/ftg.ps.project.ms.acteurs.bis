package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.ActeurType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ActeurType entity.
 */
public interface ActeurTypeSearchRepository extends ElasticsearchRepository<ActeurType, Long> {
}
