package ftg.ps.project.ms.acteur.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.acteur.domain.Animateur;
import ftg.ps.project.ms.acteur.repository.AnimateurRepository;
import ftg.ps.project.ms.acteur.repository.search.AnimateurSearchRepository;
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
 * REST controller for managing Animateur.
 */
@RestController
@RequestMapping("/api")
public class AnimateurResource {

    private final Logger log = LoggerFactory.getLogger(AnimateurResource.class);

    private static final String ENTITY_NAME = "acteurAnimateur";

    private final AnimateurRepository animateurRepository;

    private final AnimateurSearchRepository animateurSearchRepository;

    public AnimateurResource(AnimateurRepository animateurRepository, AnimateurSearchRepository animateurSearchRepository) {
        this.animateurRepository = animateurRepository;
        this.animateurSearchRepository = animateurSearchRepository;
    }

    /**
     * POST  /animateurs : Create a new animateur.
     *
     * @param animateur the animateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new animateur, or with status 400 (Bad Request) if the animateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/animateurs")
    @Timed
    public ResponseEntity<Animateur> createAnimateur(@RequestBody Animateur animateur) throws URISyntaxException {
        log.debug("REST request to save Animateur : {}", animateur);
        if (animateur.getId() != null) {
            throw new BadRequestAlertException("A new animateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Animateur result = animateurRepository.save(animateur);
        animateurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/animateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /animateurs : Updates an existing animateur.
     *
     * @param animateur the animateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated animateur,
     * or with status 400 (Bad Request) if the animateur is not valid,
     * or with status 500 (Internal Server Error) if the animateur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/animateurs")
    @Timed
    public ResponseEntity<Animateur> updateAnimateur(@RequestBody Animateur animateur) throws URISyntaxException {
        log.debug("REST request to update Animateur : {}", animateur);
        if (animateur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Animateur result = animateurRepository.save(animateur);
        animateurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, animateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /animateurs : get all the animateurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of animateurs in body
     */
    @GetMapping("/animateurs")
    @Timed
    public List<Animateur> getAllAnimateurs() {
        log.debug("REST request to get all Animateurs");
        return animateurRepository.findAll();
    }

    /**
     * GET  /animateurs/:id : get the "id" animateur.
     *
     * @param id the id of the animateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the animateur, or with status 404 (Not Found)
     */
    @GetMapping("/animateurs/{id}")
    @Timed
    public ResponseEntity<Animateur> getAnimateur(@PathVariable Long id) {
        log.debug("REST request to get Animateur : {}", id);
        Optional<Animateur> animateur = animateurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(animateur);
    }

    /**
     * DELETE  /animateurs/:id : delete the "id" animateur.
     *
     * @param id the id of the animateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/animateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnimateur(@PathVariable Long id) {
        log.debug("REST request to delete Animateur : {}", id);

        animateurRepository.deleteById(id);
        animateurSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/animateurs?query=:query : search for the animateur corresponding
     * to the query.
     *
     * @param query the query of the animateur search
     * @return the result of the search
     */
    @GetMapping("/_search/animateurs")
    @Timed
    public List<Animateur> searchAnimateurs(@RequestParam String query) {
        log.debug("REST request to search Animateurs for query {}", query);
        return StreamSupport
            .stream(animateurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
