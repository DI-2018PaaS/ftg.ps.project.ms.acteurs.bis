package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Fournisseur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Fournisseur entity.
 */
public interface FournisseurSearchRepository extends ElasticsearchRepository<Fournisseur, Long> {
}
