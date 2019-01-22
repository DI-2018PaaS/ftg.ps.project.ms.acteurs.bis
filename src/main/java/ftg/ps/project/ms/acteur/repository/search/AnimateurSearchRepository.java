package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Animateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Animateur entity.
 */
public interface AnimateurSearchRepository extends ElasticsearchRepository<Animateur, Long> {
}
