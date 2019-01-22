package ftg.ps.project.ms.acteur.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of FournisseurSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FournisseurSearchRepositoryMockConfiguration {

    @MockBean
    private FournisseurSearchRepository mockFournisseurSearchRepository;

}
