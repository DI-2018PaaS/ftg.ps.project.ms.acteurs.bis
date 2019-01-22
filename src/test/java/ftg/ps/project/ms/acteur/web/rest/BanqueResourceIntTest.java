package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.domain.Banque;
import ftg.ps.project.ms.acteur.repository.BanqueRepository;
import ftg.ps.project.ms.acteur.repository.search.BanqueSearchRepository;
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
 * Test class for the BanqueResource REST controller.
 *
 * @see BanqueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActeurApp.class)
public class BanqueResourceIntTest {

    private static final Long DEFAULT_BANQUE_ID = 1L;
    private static final Long UPDATED_BANQUE_ID = 2L;

    private static final String DEFAULT_RAISON_SOCIALE = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIALE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_WEB = "AAAAAAAAAA";
    private static final String UPDATED_SITE_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPITAL = 1L;
    private static final Long UPDATED_CAPITAL = 2L;

    private static final Long DEFAULT_CHIFFRE_DAFFAIRE = 1L;
    private static final Long UPDATED_CHIFFRE_DAFFAIRE = 2L;

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BanqueRepository banqueRepository;

    /**
     * This repository is mocked in the ftg.ps.project.ms.acteur.repository.search test package.
     *
     * @see ftg.ps.project.ms.acteur.repository.search.BanqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private BanqueSearchRepository mockBanqueSearchRepository;

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

    private MockMvc restBanqueMockMvc;

    private Banque banque;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BanqueResource banqueResource = new BanqueResource(banqueRepository, mockBanqueSearchRepository);
        this.restBanqueMockMvc = MockMvcBuilders.standaloneSetup(banqueResource)
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
    public static Banque createEntity(EntityManager em) {
        Banque banque = new Banque()
            .banqueId(DEFAULT_BANQUE_ID)
            .raisonSociale(DEFAULT_RAISON_SOCIALE)
            .email(DEFAULT_EMAIL)
            .siteWeb(DEFAULT_SITE_WEB)
            .telephone(DEFAULT_TELEPHONE)
            .capital(DEFAULT_CAPITAL)
            .chiffreDaffaire(DEFAULT_CHIFFRE_DAFFAIRE)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return banque;
    }

    @Before
    public void initTest() {
        banque = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanque() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isCreated());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate + 1);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getBanqueId()).isEqualTo(DEFAULT_BANQUE_ID);
        assertThat(testBanque.getRaisonSociale()).isEqualTo(DEFAULT_RAISON_SOCIALE);
        assertThat(testBanque.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBanque.getSiteWeb()).isEqualTo(DEFAULT_SITE_WEB);
        assertThat(testBanque.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testBanque.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testBanque.getChiffreDaffaire()).isEqualTo(DEFAULT_CHIFFRE_DAFFAIRE);
        assertThat(testBanque.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testBanque.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testBanque.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBanque.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);

        // Validate the Banque in Elasticsearch
        verify(mockBanqueSearchRepository, times(1)).save(testBanque);
    }

    @Test
    @Transactional
    public void createBanqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = banqueRepository.findAll().size();

        // Create the Banque with an existing ID
        banque.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanqueMockMvc.perform(post("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the Banque in Elasticsearch
        verify(mockBanqueSearchRepository, times(0)).save(banque);
    }

    @Test
    @Transactional
    public void getAllBanques() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get all the banqueList
        restBanqueMockMvc.perform(get("/api/banques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].banqueId").value(hasItem(DEFAULT_BANQUE_ID.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].chiffreDaffaire").value(hasItem(DEFAULT_CHIFFRE_DAFFAIRE.intValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }
    
    @Test
    @Transactional
    public void getBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banque.getId().intValue()))
            .andExpect(jsonPath("$.banqueId").value(DEFAULT_BANQUE_ID.intValue()))
            .andExpect(jsonPath("$.raisonSociale").value(DEFAULT_RAISON_SOCIALE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.siteWeb").value(DEFAULT_SITE_WEB.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.intValue()))
            .andExpect(jsonPath("$.chiffreDaffaire").value(DEFAULT_CHIFFRE_DAFFAIRE.intValue()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanque() throws Exception {
        // Get the banque
        restBanqueMockMvc.perform(get("/api/banques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Update the banque
        Banque updatedBanque = banqueRepository.findById(banque.getId()).get();
        // Disconnect from session so that the updates on updatedBanque are not directly saved in db
        em.detach(updatedBanque);
        updatedBanque
            .banqueId(UPDATED_BANQUE_ID)
            .raisonSociale(UPDATED_RAISON_SOCIALE)
            .email(UPDATED_EMAIL)
            .siteWeb(UPDATED_SITE_WEB)
            .telephone(UPDATED_TELEPHONE)
            .capital(UPDATED_CAPITAL)
            .chiffreDaffaire(UPDATED_CHIFFRE_DAFFAIRE)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanque)))
            .andExpect(status().isOk());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);
        Banque testBanque = banqueList.get(banqueList.size() - 1);
        assertThat(testBanque.getBanqueId()).isEqualTo(UPDATED_BANQUE_ID);
        assertThat(testBanque.getRaisonSociale()).isEqualTo(UPDATED_RAISON_SOCIALE);
        assertThat(testBanque.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBanque.getSiteWeb()).isEqualTo(UPDATED_SITE_WEB);
        assertThat(testBanque.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testBanque.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testBanque.getChiffreDaffaire()).isEqualTo(UPDATED_CHIFFRE_DAFFAIRE);
        assertThat(testBanque.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testBanque.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testBanque.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBanque.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);

        // Validate the Banque in Elasticsearch
        verify(mockBanqueSearchRepository, times(1)).save(testBanque);
    }

    @Test
    @Transactional
    public void updateNonExistingBanque() throws Exception {
        int databaseSizeBeforeUpdate = banqueRepository.findAll().size();

        // Create the Banque

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanqueMockMvc.perform(put("/api/banques")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banque)))
            .andExpect(status().isBadRequest());

        // Validate the Banque in the database
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Banque in Elasticsearch
        verify(mockBanqueSearchRepository, times(0)).save(banque);
    }

    @Test
    @Transactional
    public void deleteBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);

        int databaseSizeBeforeDelete = banqueRepository.findAll().size();

        // Get the banque
        restBanqueMockMvc.perform(delete("/api/banques/{id}", banque.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banque> banqueList = banqueRepository.findAll();
        assertThat(banqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Banque in Elasticsearch
        verify(mockBanqueSearchRepository, times(1)).deleteById(banque.getId());
    }

    @Test
    @Transactional
    public void searchBanque() throws Exception {
        // Initialize the database
        banqueRepository.saveAndFlush(banque);
        when(mockBanqueSearchRepository.search(queryStringQuery("id:" + banque.getId())))
            .thenReturn(Collections.singletonList(banque));
        // Search the banque
        restBanqueMockMvc.perform(get("/api/_search/banques?query=id:" + banque.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banque.getId().intValue())))
            .andExpect(jsonPath("$.[*].banqueId").value(hasItem(DEFAULT_BANQUE_ID.intValue())))
            .andExpect(jsonPath("$.[*].raisonSociale").value(hasItem(DEFAULT_RAISON_SOCIALE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].siteWeb").value(hasItem(DEFAULT_SITE_WEB)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].chiffreDaffaire").value(hasItem(DEFAULT_CHIFFRE_DAFFAIRE.intValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banque.class);
        Banque banque1 = new Banque();
        banque1.setId(1L);
        Banque banque2 = new Banque();
        banque2.setId(banque1.getId());
        assertThat(banque1).isEqualTo(banque2);
        banque2.setId(2L);
        assertThat(banque1).isNotEqualTo(banque2);
        banque1.setId(null);
        assertThat(banque1).isNotEqualTo(banque2);
    }
}
