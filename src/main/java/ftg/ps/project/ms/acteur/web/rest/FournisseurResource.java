package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Fournisseur;
import ftg.ps.project.ms.acteur.repository.FournisseurRepository;
import ftg.ps.project.ms.acteur.repository.search.FournisseurSearchRepository;
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
 * REST controller for managing Fournisseur.
 */
@RestController
@RequestMapping("/api")
public class FournisseurResource {

    private final Logger log = LoggerFactory.getLogger(FournisseurResource.class);

    private static final String ENTITY_NAME = "acteurFournisseur";

    private final FournisseurRepository fournisseurRepository;

    private final FournisseurSearchRepository fournisseurSearchRepository;

    public FournisseurResource(FournisseurRepository fournisseurRepository, FournisseurSearchRepository fournisseurSearchRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurSearchRepository = fournisseurSearchRepository;
    }

    /**
     * POST  /fournisseurs : Create a new fournisseur.
     *
     * @param fournisseur the fournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fournisseur, or with status 400 (Bad Request) if the fournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fournisseurs")
    @Timed
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) throws URISyntaxException {
        log.debug("REST request to save Fournisseur : {}", fournisseur);
        if (fournisseur.getId() != null) {
            throw new BadRequestAlertException("A new fournisseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fournisseur result = fournisseurRepository.save(fournisseur);
        fournisseurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fournisseurs : Updates an existing fournisseur.
     *
     * @param fournisseur the fournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fournisseur,
     * or with status 400 (Bad Request) if the fournisseur is not valid,
     * or with status 500 (Internal Server Error) if the fournisseur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fournisseurs")
    @Timed
    public ResponseEntity<Fournisseur> updateFournisseur(@RequestBody Fournisseur fournisseur) throws URISyntaxException {
        log.debug("REST request to update Fournisseur : {}", fournisseur);
        if (fournisseur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fournisseur result = fournisseurRepository.save(fournisseur);
        fournisseurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fournisseur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fournisseurs : get all the fournisseurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fournisseurs in body
     */
    @GetMapping("/fournisseurs")
    @Timed
    public List<Fournisseur> getAllFournisseurs() {
        log.debug("REST request to get all Fournisseurs");
        return fournisseurRepository.findAll();
    }

    /**
     * GET  /fournisseurs/:id : get the "id" fournisseur.
     *
     * @param id the id of the fournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<Fournisseur> getFournisseur(@PathVariable Long id) {
        log.debug("REST request to get Fournisseur : {}", id);
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fournisseur);
    }

    /**
     * DELETE  /fournisseurs/:id : delete the "id" fournisseur.
     *
     * @param id the id of the fournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete Fournisseur : {}", id);

        fournisseurRepository.deleteById(id);
        fournisseurSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/fournisseurs?query=:query : search for the fournisseur corresponding
     * to the query.
     *
     * @param query the query of the fournisseur search
     * @return the result of the search
     */
    @GetMapping("/_search/fournisseurs")
    @Timed
    public List<Fournisseur> searchFournisseurs(@RequestParam String query) {
        log.debug("REST request to search Fournisseurs for query {}", query);
        return StreamSupport
            .stream(fournisseurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
