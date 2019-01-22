package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.domain.Organisme;
import ftg.ps.project.ms.acteur.repository.OrganismeRepository;
import ftg.ps.project.ms.acteur.repository.search.OrganismeSearchRepository;
import ftg.ps.project.ms.acteur.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static ftg.ps.project.ms.acteur.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganismeResource REST controller.
 *
 * @see OrganismeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActeurApp.class)
public class OrganismeResourceIntTest {

    private static final Long DEFAULT_ORGANISME_ID = 1L;
    private static final Long UPDATED_ORGANISME_ID = 2L;

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_ORGANISME = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ORGANISME = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAINE_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_DOMAINE_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrganismeRepository organismeRepository;

    /**
     * This repository is mocked in the ftg.ps.project.ms.acteur.repository.search test package.
     *
     * @see ftg.ps.project.ms.acteur.repository.search.OrganismeSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrganismeSearchRepository mockOrganismeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrganismeMockMvc;

    private Organisme organisme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganismeResource organismeResource = new OrganismeResource(organismeRepository, mockOrganismeSearchRepository);
        this.restOrganismeMockMvc = MockMvcBuilders.standaloneSetup(organismeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisme createEntity(EntityManager em) {
        Organisme organisme = new Organisme()
            .organismeId(DEFAULT_ORGANISME_ID)
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .typeOrganisme(DEFAULT_TYPE_ORGANISME)
            .domaineActivite(DEFAULT_DOMAINE_ACTIVITE)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return organisme;
    }

    @Before
    public void initTest() {
        organisme = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganisme() throws Exception {
        int databaseSizeBeforeCreate = organismeRepository.findAll().size();

        // Create the Organisme
        restOrganismeMockMvc.perform(post("/api/organismes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisme)))
            .andExpect(status().isCreated());

        // Validate the Organisme in the database
        List<Organisme> organismeList = organismeRepository.findAll();
        assertThat(organismeList).hasSize(databaseSizeBeforeCreate + 1);
        Organisme testOrganisme = organismeList.get(organismeList.size() - 1);
        assertThat(testOrganisme.getOrganismeId()).isEqualTo(DEFAULT_ORGANISME_ID);
        assertThat(testOrganisme.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testOrganisme.getTypeOrganisme()).isEqualTo(DEFAULT_TYPE_ORGANISME);
        assertThat(testOrganisme.getDomaineActivite()).isEqualTo(DEFAULT_DOMAINE_ACTIVITE);
        assertThat(testOrganisme.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganisme.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testOrganisme.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testOrganisme.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testOrganisme.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testOrganisme.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);

        // Validate the Organisme in Elasticsearch
        verify(mockOrganismeSearchRepository, times(1)).save(testOrganisme);
    }

    @Test
    @Transactional
    public void createOrganismeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organismeRepository.findAll().size();

        // Create the Organisme with an existing ID
        organisme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganismeMockMvc.perform(post("/api/organismes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisme)))
            .andExpect(status().isBadRequest());

        // Validate the Organisme in the database
        List<Organisme> organismeList = organismeRepository.findAll();
        assertThat(organismeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Organisme in Elasticsearch
        verify(mockOrganismeSearchRepository, times(0)).save(organisme);
    }

    @Test
    @Transactional
    public void getAllOrganismes() throws Exception {
        // Initialize the database
        organismeRepository.saveAndFlush(organisme);

        // Get all the organismeList
        restOrganismeMockMvc.perform(get("/api/organismes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisme.getId().intValue())))
            .andExpect(jsonPath("$.[*].organismeId").value(hasItem(DEFAULT_ORGANISME_ID.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].typeOrganisme").value(hasItem(DEFAULT_TYPE_ORGANISME.toString())))
            .andExpect(jsonPath("$.[*].domaineActivite").value(hasItem(DEFAULT_DOMAINE_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }
    
    @Test
    @Transactional
    public void getOrganisme() throws Exception {
        // Initialize the database
        organismeRepository.saveAndFlush(organisme);

        // Get the organisme
        restOrganismeMockMvc.perform(get("/api/organismes/{id}", organisme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisme.getId().intValue()))
            .andExpect(jsonPath("$.organismeId").value(DEFAULT_ORGANISME_ID.intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE.toString()))
            .andExpect(jsonPath("$.typeOrganisme").value(DEFAULT_TYPE_ORGANISME.toString()))
            .andExpect(jsonPath("$.domaineActivite").value(DEFAULT_DOMAINE_ACTIVITE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganisme() throws Exception {
        // Get the organisme
        restOrganismeMockMvc.perform(get("/api/organismes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganisme() throws Exception {
        // Initialize the database
        organismeRepository.saveAndFlush(organisme);

        int databaseSizeBeforeUpdate = organismeRepository.findAll().size();

        // Update the organisme
        Organisme updatedOrganisme = organismeRepository.findById(organisme.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisme are not directly saved in db
        em.detach(updatedOrganisme);
        updatedOrganisme
            .organismeId(UPDATED_ORGANISME_ID)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .typeOrganisme(UPDATED_TYPE_ORGANISME)
            .domaineActivite(UPDATED_DOMAINE_ACTIVITE)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restOrganismeMockMvc.perform(put("/api/organismes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganisme)))
            .andExpect(status().isOk());

        // Validate the Organisme in the database
        List<Organisme> organismeList = organismeRepository.findAll();
        assertThat(organismeList).hasSize(databaseSizeBeforeUpdate);
        Organisme testOrganisme = organismeList.get(organismeList.size() - 1);
        assertThat(testOrganisme.getOrganismeId()).isEqualTo(UPDATED_ORGANISME_ID);
        assertThat(testOrganisme.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testOrganisme.getTypeOrganisme()).isEqualTo(UPDATED_TYPE_ORGANISME);
        assertThat(testOrganisme.getDomaineActivite()).isEqualTo(UPDATED_DOMAINE_ACTIVITE);
        assertThat(testOrganisme.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganisme.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testOrganisme.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testOrganisme.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testOrganisme.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testOrganisme.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);

        // Validate the Organisme in Elasticsearch
        verify(mockOrganismeSearchRepository, times(1)).save(testOrganisme);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganisme() throws Exception {
        int databaseSizeBeforeUpdate = organismeRepository.findAll().size();

        // Create the Organisme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganismeMockMvc.perform(put("/api/organismes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organisme)))
            .andExpect(status().isBadRequest());

        // Validate the Organisme in the database
        List<Organisme> organismeList = organismeRepository.findAll();
        assertThat(organismeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Organisme in Elasticsearch
        verify(mockOrganismeSearchRepository, times(0)).save(organisme);
    }

    @Test
    @Transactional
    public void deleteOrganisme() throws Exception {
        // Initialize the database
        organismeRepository.saveAndFlush(organisme);

        int databaseSizeBeforeDelete = organismeRepository.findAll().size();

        // Get the organisme
        restOrganismeMockMvc.perform(delete("/api/organismes/{id}", organisme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organisme> organismeList = organismeRepository.findAll();
        assertThat(organismeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Organisme in Elasticsearch
        verify(mockOrganismeSearchRepository, times(1)).deleteById(organisme.getId());
    }

    @Test
    @Transactional
    public void searchOrganisme() throws Exception {
        // Initialize the database
        organismeRepository.saveAndFlush(organisme);
        when(mockOrganismeSearchRepository.search(queryStringQuery("id:" + organisme.getId())))
            .thenReturn(Collections.singletonList(organisme));
        // Search the organisme
        restOrganismeMockMvc.perform(get("/api/_search/organismes?query=id:" + organisme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisme.getId().intValue())))
            .andExpect(jsonPath("$.[*].organismeId").value(hasItem(DEFAULT_ORGANISME_ID.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].typeOrganisme").value(hasItem(DEFAULT_TYPE_ORGANISME)))
            .andExpect(jsonPath("$.[*].domaineActivite").value(hasItem(DEFAULT_DOMAINE_ACTIVITE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organisme.class);
        Organisme organisme1 = new Organisme();
        organisme1.setId(1L);
        Organisme organisme2 = new Organisme();
        organisme2.setId(organisme1.getId());
        assertThat(organisme1).isEqualTo(organisme2);
        organisme2.setId(2L);
        assertThat(organisme1).isNotEqualTo(organisme2);
        organisme1.setId(null);
        assertThat(organisme1).isNotEqualTo(organisme2);
    }
}
