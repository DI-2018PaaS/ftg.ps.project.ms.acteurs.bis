package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Organisme;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Organisme entity.
 */
public interface OrganismeSearchRepository extends ElasticsearchRepository<Organisme, Long> {
}
