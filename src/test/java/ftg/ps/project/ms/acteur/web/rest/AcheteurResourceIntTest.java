package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.domain.Acheteur;
import ftg.ps.project.ms.acteur.repository.AcheteurRepository;
import ftg.ps.project.ms.acteur.repository.search.AcheteurSearchRepository;
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
 * Test class for the AcheteurResource REST controller.
 *
 * @see AcheteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActeurApp.class)
public class AcheteurResourceIntTest {

    private static final Long DEFAULT_ACHETEUR_ID = 1L;
    private static final Long UPDATED_ACHETEUR_ID = 2L;

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
    private AcheteurRepository acheteurRepository;

    /**
     * This repository is mocked in the ftg.ps.project.ms.acteur.repository.search test package.
     *
     * @see ftg.ps.project.ms.acteur.repository.search.AcheteurSearchRepositoryMockConfiguration
     */
    @Autowired
    private AcheteurSearchRepository mockAcheteurSearchRepository;

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

    private MockMvc restAcheteurMockMvc;

    private Acheteur acheteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcheteurResource acheteurResource = new AcheteurResource(acheteurRepository, mockAcheteurSearchRepository);
        this.restAcheteurMockMvc = MockMvcBuilders.standaloneSetup(acheteurResource)
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
    public static Acheteur createEntity(EntityManager em) {
        Acheteur acheteur = new Acheteur()
            .acheteurId(DEFAULT_ACHETEUR_ID)
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return acheteur;
    }

    @Before
    public void initTest() {
        acheteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcheteur() throws Exception {
        int databaseSizeBeforeCreate = acheteurRepository.findAll().size();

        // Create the Acheteur
        restAcheteurMockMvc.perform(post("/api/acheteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acheteur)))
            .andExpect(status().isCreated());

        // Validate the Acheteur in the database
        List<Acheteur> acheteurList = acheteurRepository.findAll();
        assertThat(acheteurList).hasSize(databaseSizeBeforeCreate + 1);
        Acheteur testAcheteur = acheteurList.get(acheteurList.size() - 1);
        assertThat(testAcheteur.getAcheteurId()).isEqualTo(DEFAULT_ACHETEUR_ID);
        assertThat(testAcheteur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAcheteur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAcheteur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAcheteur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAcheteur.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAcheteur.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAcheteur.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testAcheteur.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAcheteur.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);

        // Validate the Acheteur in Elasticsearch
        verify(mockAcheteurSearchRepository, times(1)).save(testAcheteur);
    }

    @Test
    @Transactional
    public void createAcheteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acheteurRepository.findAll().size();

        // Create the Acheteur with an existing ID
        acheteur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcheteurMockMvc.perform(post("/api/acheteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acheteur)))
            .andExpect(status().isBadRequest());

        // Validate the Acheteur in the database
        List<Acheteur> acheteurList = acheteurRepository.findAll();
        assertThat(acheteurList).hasSize(databaseSizeBeforeCreate);

        // Validate the Acheteur in Elasticsearch
        verify(mockAcheteurSearchRepository, times(0)).save(acheteur);
    }

    @Test
    @Transactional
    public void getAllAcheteurs() throws Exception {
        // Initialize the database
        acheteurRepository.saveAndFlush(acheteur);

        // Get all the acheteurList
        restAcheteurMockMvc.perform(get("/api/acheteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acheteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].acheteurId").value(hasItem(DEFAULT_ACHETEUR_ID.intValue())))
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
    public void getAcheteur() throws Exception {
        // Initialize the database
        acheteurRepository.saveAndFlush(acheteur);

        // Get the acheteur
        restAcheteurMockMvc.perform(get("/api/acheteurs/{id}", acheteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acheteur.getId().intValue()))
            .andExpect(jsonPath("$.acheteurId").value(DEFAULT_ACHETEUR_ID.intValue()))
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
    public void getNonExistingAcheteur() throws Exception {
        // Get the acheteur
        restAcheteurMockMvc.perform(get("/api/acheteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcheteur() throws Exception {
        // Initialize the database
        acheteurRepository.saveAndFlush(acheteur);

        int databaseSizeBeforeUpdate = acheteurRepository.findAll().size();

        // Update the acheteur
        Acheteur updatedAcheteur = acheteurRepository.findById(acheteur.getId()).get();
        // Disconnect from session so that the updates on updatedAcheteur are not directly saved in db
        em.detach(updatedAcheteur);
        updatedAcheteur
            .acheteurId(UPDATED_ACHETEUR_ID)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restAcheteurMockMvc.perform(put("/api/acheteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcheteur)))
            .andExpect(status().isOk());

        // Validate the Acheteur in the database
        List<Acheteur> acheteurList = acheteurRepository.findAll();
        assertThat(acheteurList).hasSize(databaseSizeBeforeUpdate);
        Acheteur testAcheteur = acheteurList.get(acheteurList.size() - 1);
        assertThat(testAcheteur.getAcheteurId()).isEqualTo(UPDATED_ACHETEUR_ID);
        assertThat(testAcheteur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAcheteur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAcheteur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAcheteur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAcheteur.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAcheteur.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAcheteur.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testAcheteur.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAcheteur.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);

        // Validate the Acheteur in Elasticsearch
        verify(mockAcheteurSearchRepository, times(1)).save(testAcheteur);
    }

    @Test
    @Transactional
    public void updateNonExistingAcheteur() throws Exception {
        int databaseSizeBeforeUpdate = acheteurRepository.findAll().size();

        // Create the Acheteur

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcheteurMockMvc.perform(put("/api/acheteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acheteur)))
            .andExpect(status().isBadRequest());

        // Validate the Acheteur in the database
        List<Acheteur> acheteurList = acheteurRepository.findAll();
        assertThat(acheteurList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acheteur in Elasticsearch
        verify(mockAcheteurSearchRepository, times(0)).save(acheteur);
    }

    @Test
    @Transactional
    public void deleteAcheteur() throws Exception {
        // Initialize the database
        acheteurRepository.saveAndFlush(acheteur);

        int databaseSizeBeforeDelete = acheteurRepository.findAll().size();

        // Get the acheteur
        restAcheteurMockMvc.perform(delete("/api/acheteurs/{id}", acheteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acheteur> acheteurList = acheteurRepository.findAll();
        assertThat(acheteurList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Acheteur in Elasticsearch
        verify(mockAcheteurSearchRepository, times(1)).deleteById(acheteur.getId());
    }

    @Test
    @Transactional
    public void searchAcheteur() throws Exception {
        // Initialize the database
        acheteurRepository.saveAndFlush(acheteur);
        when(mockAcheteurSearchRepository.search(queryStringQuery("id:" + acheteur.getId())))
            .thenReturn(Collections.singletonList(acheteur));
        // Search the acheteur
        restAcheteurMockMvc.perform(get("/api/_search/acheteurs?query=id:" + acheteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acheteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].acheteurId").value(hasItem(DEFAULT_ACHETEUR_ID.intValue())))
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
        TestUtil.equalsVerifier(Acheteur.class);
        Acheteur acheteur1 = new Acheteur();
        acheteur1.setId(1L);
        Acheteur acheteur2 = new Acheteur();
        acheteur2.setId(acheteur1.getId());
        assertThat(acheteur1).isEqualTo(acheteur2);
        acheteur2.setId(2L);
        assertThat(acheteur1).isNotEqualTo(acheteur2);
        acheteur1.setId(null);
        assertThat(acheteur1).isNotEqualTo(acheteur2);
    }
}
