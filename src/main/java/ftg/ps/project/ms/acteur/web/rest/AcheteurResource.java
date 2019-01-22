package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Acheteur;
import ftg.ps.project.ms.acteur.repository.AcheteurRepository;
import ftg.ps.project.ms.acteur.repository.search.AcheteurSearchRepository;
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
 * REST controller for managing Acheteur.
 */
@RestController
@RequestMapping("/api")
public class AcheteurResource {

    private final Logger log = LoggerFactory.getLogger(AcheteurResource.class);

    private static final String ENTITY_NAME = "acteurAcheteur";

    private final AcheteurRepository acheteurRepository;

    private final AcheteurSearchRepository acheteurSearchRepository;

    public AcheteurResource(AcheteurRepository acheteurRepository, AcheteurSearchRepository acheteurSearchRepository) {
        this.acheteurRepository = acheteurRepository;
        this.acheteurSearchRepository = acheteurSearchRepository;
    }

    /**
     * POST  /acheteurs : Create a new acheteur.
     *
     * @param acheteur the acheteur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acheteur, or with status 400 (Bad Request) if the acheteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acheteurs")
    @Timed
    public ResponseEntity<Acheteur> createAcheteur(@RequestBody Acheteur acheteur) throws URISyntaxException {
        log.debug("REST request to save Acheteur : {}", acheteur);
        if (acheteur.getId() != null) {
            throw new BadRequestAlertException("A new acheteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acheteur result = acheteurRepository.save(acheteur);
        acheteurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/acheteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acheteurs : Updates an existing acheteur.
     *
     * @param acheteur the acheteur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acheteur,
     * or with status 400 (Bad Request) if the acheteur is not valid,
     * or with status 500 (Internal Server Error) if the acheteur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acheteurs")
    @Timed
    public ResponseEntity<Acheteur> updateAcheteur(@RequestBody Acheteur acheteur) throws URISyntaxException {
        log.debug("REST request to update Acheteur : {}", acheteur);
        if (acheteur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Acheteur result = acheteurRepository.save(acheteur);
        acheteurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acheteur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acheteurs : get all the acheteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acheteurs in body
     */
    @GetMapping("/acheteurs")
    @Timed
    public List<Acheteur> getAllAcheteurs() {
        log.debug("REST request to get all Acheteurs");
        return acheteurRepository.findAll();
    }

    /**
     * GET  /acheteurs/:id : get the "id" acheteur.
     *
     * @param id the id of the acheteur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acheteur, or with status 404 (Not Found)
     */
    @GetMapping("/acheteurs/{id}")
    @Timed
    public ResponseEntity<Acheteur> getAcheteur(@PathVariable Long id) {
        log.debug("REST request to get Acheteur : {}", id);
        Optional<Acheteur> acheteur = acheteurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acheteur);
    }

    /**
     * DELETE  /acheteurs/:id : delete the "id" acheteur.
     *
     * @param id the id of the acheteur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acheteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcheteur(@PathVariable Long id) {
        log.debug("REST request to delete Acheteur : {}", id);

        acheteurRepository.deleteById(id);
        acheteurSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/acheteurs?query=:query : search for the acheteur corresponding
     * to the query.
     *
     * @param query the query of the acheteur search
     * @return the result of the search
     */
    @GetMapping("/_search/acheteurs")
    @Timed
    public List<Acheteur> searchAcheteurs(@RequestParam String query) {
        log.debug("REST request to search Acheteurs for query {}", query);
        return StreamSupport
            .stream(acheteurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
