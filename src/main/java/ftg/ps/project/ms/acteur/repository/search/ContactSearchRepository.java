package ftg.ps.project.ms.acteur.repository.search;

import ftg.ps.project.ms.acteur.domain.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Contact entity.
 */
public interface ContactSearchRepository extends ElasticsearchRepository<Contact, Long> {
}
