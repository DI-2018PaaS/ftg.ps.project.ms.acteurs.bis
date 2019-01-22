package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.domain.Fournisseur;
import ftg.ps.project.ms.acteur.repository.FournisseurRepository;
import ftg.ps.project.ms.acteur.repository.search.FournisseurSearchRepository;
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
 * Test class for the FournisseurResource REST controller.
 *
 * @see FournisseurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActeurApp.class)
public class FournisseurResourceIntTest {

    private static final Long DEFAULT_FOURNISSEUR_ID = 1L;
    private static final Long UPDATED_FOURNISSEUR_ID = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

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
    private FournisseurRepository fournisseurRepository;

    /**
     * This repository is mocked in the ftg.ps.project.ms.acteur.repository.search test package.
     *
     * @see ftg.ps.project.ms.acteur.repository.search.FournisseurSearchRepositoryMockConfiguration
     */
    @Autowired
    private FournisseurSearchRepository mockFournisseurSearchRepository;

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

    private MockMvc restFournisseurMockMvc;

    private Fournisseur fournisseur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FournisseurResource fournisseurResource = new FournisseurResource(fournisseurRepository, mockFournisseurSearchRepository);
        this.restFournisseurMockMvc = MockMvcBuilders.standaloneSetup(fournisseurResource)
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
    public static Fournisseur createEntity(EntityManager em) {
        Fournisseur fournisseur = new Fournisseur()
            .fournisseurId(DEFAULT_FOURNISSEUR_ID)
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return fournisseur;
    }

    @Before
    public void initTest() {
        fournisseur = createEntity(em);
    }

    @Test
    @Transactional
    public void createFournisseur() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isCreated());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate + 1);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFournisseurId()).isEqualTo(DEFAULT_FOURNISSEUR_ID);
        assertThat(testFournisseur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFournisseur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFournisseur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testFournisseur.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFournisseur.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testFournisseur.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFournisseur.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);

        // Validate the Fournisseur in Elasticsearch
        verify(mockFournisseurSearchRepository, times(1)).save(testFournisseur);
    }

    @Test
    @Transactional
    public void createFournisseurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fournisseurRepository.findAll().size();

        // Create the Fournisseur with an existing ID
        fournisseur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFournisseurMockMvc.perform(post("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fournisseur in Elasticsearch
        verify(mockFournisseurSearchRepository, times(0)).save(fournisseur);
    }

    @Test
    @Transactional
    public void getAllFournisseurs() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get all the fournisseurList
        restFournisseurMockMvc.perform(get("/api/fournisseurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].fournisseurId").value(hasItem(DEFAULT_FOURNISSEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }
    
    @Test
    @Transactional
    public void getFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fournisseur.getId().intValue()))
            .andExpect(jsonPath("$.fournisseurId").value(DEFAULT_FOURNISSEUR_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFournisseur() throws Exception {
        // Get the fournisseur
        restFournisseurMockMvc.perform(get("/api/fournisseurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Update the fournisseur
        Fournisseur updatedFournisseur = fournisseurRepository.findById(fournisseur.getId()).get();
        // Disconnect from session so that the updates on updatedFournisseur are not directly saved in db
        em.detach(updatedFournisseur);
        updatedFournisseur
            .fournisseurId(UPDATED_FOURNISSEUR_ID)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFournisseur)))
            .andExpect(status().isOk());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);
        Fournisseur testFournisseur = fournisseurList.get(fournisseurList.size() - 1);
        assertThat(testFournisseur.getFournisseurId()).isEqualTo(UPDATED_FOURNISSEUR_ID);
        assertThat(testFournisseur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFournisseur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFournisseur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFournisseur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFournisseur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testFournisseur.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFournisseur.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testFournisseur.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFournisseur.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);

        // Validate the Fournisseur in Elasticsearch
        verify(mockFournisseurSearchRepository, times(1)).save(testFournisseur);
    }

    @Test
    @Transactional
    public void updateNonExistingFournisseur() throws Exception {
        int databaseSizeBeforeUpdate = fournisseurRepository.findAll().size();

        // Create the Fournisseur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFournisseurMockMvc.perform(put("/api/fournisseurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fournisseur)))
            .andExpect(status().isBadRequest());

        // Validate the Fournisseur in the database
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fournisseur in Elasticsearch
        verify(mockFournisseurSearchRepository, times(0)).save(fournisseur);
    }

    @Test
    @Transactional
    public void deleteFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);

        int databaseSizeBeforeDelete = fournisseurRepository.findAll().size();

        // Get the fournisseur
        restFournisseurMockMvc.perform(delete("/api/fournisseurs/{id}", fournisseur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fournisseur> fournisseurList = fournisseurRepository.findAll();
        assertThat(fournisseurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fournisseur in Elasticsearch
        verify(mockFournisseurSearchRepository, times(1)).deleteById(fournisseur.getId());
    }

    @Test
    @Transactional
    public void searchFournisseur() throws Exception {
        // Initialize the database
        fournisseurRepository.saveAndFlush(fournisseur);
        when(mockFournisseurSearchRepository.search(queryStringQuery("id:" + fournisseur.getId())))
            .thenReturn(Collections.singletonList(fournisseur));
        // Search the fournisseur
        restFournisseurMockMvc.perform(get("/api/_search/fournisseurs?query=id:" + fournisseur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fournisseur.getId().intValue())))
            .andExpect(jsonPath("$.[*].fournisseurId").value(hasItem(DEFAULT_FOURNISSEUR_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
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
        TestUtil.equalsVerifier(Fournisseur.class);
        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setId(1L);
        Fournisseur fournisseur2 = new Fournisseur();
        fournisseur2.setId(fournisseur1.getId());
        assertThat(fournisseur1).isEqualTo(fournisseur2);
        fournisseur2.setId(2L);
        assertThat(fournisseur1).isNotEqualTo(fournisseur2);
        fournisseur1.setId(null);
        assertThat(fournisseur1).isNotEqualTo(fournisseur2);
    }
}
