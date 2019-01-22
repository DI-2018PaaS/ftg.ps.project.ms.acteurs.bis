package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Organisme;
import ftg.ps.project.ms.acteur.repository.OrganismeRepository;
import ftg.ps.project.ms.acteur.repository.search.OrganismeSearchRepository;
import ftg.ps.project.ms.acteur.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.acteur.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Organisme.
 */
@RestController
@RequestMapping("/api")
public class OrganismeResource {

    private final Logger log = LoggerFactory.getLogger(OrganismeResource.class);

    private static final String ENTITY_NAME = "acteurOrganisme";

    private final OrganismeRepository organismeRepository;

    private final OrganismeSearchRepository organismeSearchRepository;

    public OrganismeResource(OrganismeRepository organismeRepository, OrganismeSearchRepository organismeSearchRepository) {
        this.organismeRepository = organismeRepository;
        this.organismeSearchRepository = organismeSearchRepository;
    }

    /**
     * POST  /organismes : Create a new organisme.
     *
     * @param organisme the organisme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organisme, or with status 400 (Bad Request) if the organisme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organismes")
    @Timed
    public ResponseEntity<Organisme> createOrganisme(@RequestBody Organisme organisme) throws URISyntaxException {
        log.debug("REST request to save Organisme : {}", organisme);
        if (organisme.getId() != null) {
            throw new BadRequestAlertException("A new organisme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Organisme result = organismeRepository.save(organisme);
        organismeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/organismes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organismes : Updates an existing organisme.
     *
     * @param organisme the organisme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organisme,
     * or with status 400 (Bad Request) if the organisme is not valid,
     * or with status 500 (Internal Server Error) if the organisme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organismes")
    @Timed
    public ResponseEntity<Organisme> updateOrganisme(@RequestBody Organisme organisme) throws URISyntaxException {
        log.debug("REST request to update Organisme : {}", organisme);
        if (organisme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Organisme result = organismeRepository.save(organisme);
        organismeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organisme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organismes : get all the organismes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organismes in body
     */
    @GetMapping("/organismes")
    @Timed
    public List<Organisme> getAllOrganismes() {
        log.debug("REST request to get all Organismes");
        return organismeRepository.findAll();
    }

    /**
     * GET  /organismes/:id : get the "id" organisme.
     *
     * @param id the id of the organisme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organisme, or with status 404 (Not Found)
     */
    @GetMapping("/organismes/{id}")
    @Timed
    public ResponseEntity<Organisme> getOrganisme(@PathVariable Long id) {
        log.debug("REST request to get Organisme : {}", id);
        Optional<Organisme> organisme = organismeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organisme);
    }

    /**
     * DELETE  /organismes/:id : delete the "id" organisme.
     *
     * @param id the id of the organisme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organismes/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganisme(@PathVariable Long id) {
        log.debug("REST request to delete Organisme : {}", id);

        organismeRepository.deleteById(id);
        organismeSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/organismes?query=:query : search for the organisme corresponding
     * to the query.
     *
     * @param query the query of the organisme search
     * @return the result of the search
     */
    @GetMapping("/_search/organismes")
    @Timed
    public List<Organisme> searchOrganismes(@RequestParam String query) {
        log.debug("REST request to search Organismes for query {}", query);
        return StreamSupport
            .stream(organismeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
