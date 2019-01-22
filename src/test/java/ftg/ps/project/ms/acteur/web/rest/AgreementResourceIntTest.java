package ftg.ps.project.ms.acteur.web.rest;

import ftg.ps.project.ms.acteur.ActeurApp;

import ftg.ps.project.ms.acteur.domain.Agreement;
import ftg.ps.project.ms.acteur.repository.AgreementRepository;
import ftg.ps.project.ms.acteur.repository.search.AgreementSearchRepository;
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
 * Test class for the AgreementResource REST controller.
 *
 * @see AgreementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActeurApp.class)
public class AgreementResourceIntTest {

    private static final Long DEFAULT_AGREEMENT_ID = 1L;
    private static final Long UPDATED_AGREEMENT_ID = 2L;

    private static final String DEFAULT_NUMERO_AGREMENT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_AGREMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ATTIBUTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ATTIBUTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEB_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEB_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN_VALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN_VALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NIVEAU_AGREEMENT = 1;
    private static final Integer UPDATED_NIVEAU_AGREEMENT = 2;

    @Autowired
    private AgreementRepository agreementRepository;

    /**
     * This repository is mocked in the ftg.ps.project.ms.acteur.repository.search test package.
     *
     * @see ftg.ps.project.ms.acteur.repository.search.AgreementSearchRepositoryMockConfiguration
     */
    @Autowired
    private AgreementSearchRepository mockAgreementSearchRepository;

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

    private MockMvc restAgreementMockMvc;

    private Agreement agreement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgreementResource agreementResource = new AgreementResource(agreementRepository, mockAgreementSearchRepository);
        this.restAgreementMockMvc = MockMvcBuilders.standaloneSetup(agreementResource)
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
    public static Agreement createEntity(EntityManager em) {
        Agreement agreement = new Agreement()
            .agreementId(DEFAULT_AGREEMENT_ID)
            .numeroAgrement(DEFAULT_NUMERO_AGREMENT)
            .dateAttibution(DEFAULT_DATE_ATTIBUTION)
            .dateDebValidite(DEFAULT_DATE_DEB_VALIDITE)
            .dateFinValidite(DEFAULT_DATE_FIN_VALIDITE)
            .status(DEFAULT_STATUS)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF)
            .niveauAgreement(DEFAULT_NIVEAU_AGREEMENT);
        return agreement;
    }

    @Before
    public void initTest() {
        agreement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgreement() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isCreated());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate + 1);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getAgreementId()).isEqualTo(DEFAULT_AGREEMENT_ID);
        assertThat(testAgreement.getNumeroAgrement()).isEqualTo(DEFAULT_NUMERO_AGREMENT);
        assertThat(testAgreement.getDateAttibution()).isEqualTo(DEFAULT_DATE_ATTIBUTION);
        assertThat(testAgreement.getDateDebValidite()).isEqualTo(DEFAULT_DATE_DEB_VALIDITE);
        assertThat(testAgreement.getDateFinValidite()).isEqualTo(DEFAULT_DATE_FIN_VALIDITE);
        assertThat(testAgreement.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAgreement.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAgreement.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testAgreement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAgreement.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);
        assertThat(testAgreement.getNiveauAgreement()).isEqualTo(DEFAULT_NIVEAU_AGREEMENT);

        // Validate the Agreement in Elasticsearch
        verify(mockAgreementSearchRepository, times(1)).save(testAgreement);
    }

    @Test
    @Transactional
    public void createAgreementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agreementRepository.findAll().size();

        // Create the Agreement with an existing ID
        agreement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgreementMockMvc.perform(post("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeCreate);

        // Validate the Agreement in Elasticsearch
        verify(mockAgreementSearchRepository, times(0)).save(agreement);
    }

    @Test
    @Transactional
    public void getAllAgreements() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get all the agreementList
        restAgreementMockMvc.perform(get("/api/agreements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreementId").value(hasItem(DEFAULT_AGREEMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].numeroAgrement").value(hasItem(DEFAULT_NUMERO_AGREMENT.toString())))
            .andExpect(jsonPath("$.[*].dateAttibution").value(hasItem(DEFAULT_DATE_ATTIBUTION.toString())))
            .andExpect(jsonPath("$.[*].dateDebValidite").value(hasItem(DEFAULT_DATE_DEB_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].dateFinValidite").value(hasItem(DEFAULT_DATE_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())))
            .andExpect(jsonPath("$.[*].niveauAgreement").value(hasItem(DEFAULT_NIVEAU_AGREEMENT)));
    }
    
    @Test
    @Transactional
    public void getAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", agreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agreement.getId().intValue()))
            .andExpect(jsonPath("$.agreementId").value(DEFAULT_AGREEMENT_ID.intValue()))
            .andExpect(jsonPath("$.numeroAgrement").value(DEFAULT_NUMERO_AGREMENT.toString()))
            .andExpect(jsonPath("$.dateAttibution").value(DEFAULT_DATE_ATTIBUTION.toString()))
            .andExpect(jsonPath("$.dateDebValidite").value(DEFAULT_DATE_DEB_VALIDITE.toString()))
            .andExpect(jsonPath("$.dateFinValidite").value(DEFAULT_DATE_FIN_VALIDITE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()))
            .andExpect(jsonPath("$.niveauAgreement").value(DEFAULT_NIVEAU_AGREEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingAgreement() throws Exception {
        // Get the agreement
        restAgreementMockMvc.perform(get("/api/agreements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Update the agreement
        Agreement updatedAgreement = agreementRepository.findById(agreement.getId()).get();
        // Disconnect from session so that the updates on updatedAgreement are not directly saved in db
        em.detach(updatedAgreement);
        updatedAgreement
            .agreementId(UPDATED_AGREEMENT_ID)
            .numeroAgrement(UPDATED_NUMERO_AGREMENT)
            .dateAttibution(UPDATED_DATE_ATTIBUTION)
            .dateDebValidite(UPDATED_DATE_DEB_VALIDITE)
            .dateFinValidite(UPDATED_DATE_FIN_VALIDITE)
            .status(UPDATED_STATUS)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateLastModif(UPDATED_DATE_LAST_MODIF)
            .niveauAgreement(UPDATED_NIVEAU_AGREEMENT);

        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgreement)))
            .andExpect(status().isOk());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);
        Agreement testAgreement = agreementList.get(agreementList.size() - 1);
        assertThat(testAgreement.getAgreementId()).isEqualTo(UPDATED_AGREEMENT_ID);
        assertThat(testAgreement.getNumeroAgrement()).isEqualTo(UPDATED_NUMERO_AGREMENT);
        assertThat(testAgreement.getDateAttibution()).isEqualTo(UPDATED_DATE_ATTIBUTION);
        assertThat(testAgreement.getDateDebValidite()).isEqualTo(UPDATED_DATE_DEB_VALIDITE);
        assertThat(testAgreement.getDateFinValidite()).isEqualTo(UPDATED_DATE_FIN_VALIDITE);
        assertThat(testAgreement.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAgreement.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAgreement.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testAgreement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAgreement.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);
        assertThat(testAgreement.getNiveauAgreement()).isEqualTo(UPDATED_NIVEAU_AGREEMENT);

        // Validate the Agreement in Elasticsearch
        verify(mockAgreementSearchRepository, times(1)).save(testAgreement);
    }

    @Test
    @Transactional
    public void updateNonExistingAgreement() throws Exception {
        int databaseSizeBeforeUpdate = agreementRepository.findAll().size();

        // Create the Agreement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgreementMockMvc.perform(put("/api/agreements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agreement)))
            .andExpect(status().isBadRequest());

        // Validate the Agreement in the database
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Agreement in Elasticsearch
        verify(mockAgreementSearchRepository, times(0)).save(agreement);
    }

    @Test
    @Transactional
    public void deleteAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);

        int databaseSizeBeforeDelete = agreementRepository.findAll().size();

        // Get the agreement
        restAgreementMockMvc.perform(delete("/api/agreements/{id}", agreement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agreement> agreementList = agreementRepository.findAll();
        assertThat(agreementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Agreement in Elasticsearch
        verify(mockAgreementSearchRepository, times(1)).deleteById(agreement.getId());
    }

    @Test
    @Transactional
    public void searchAgreement() throws Exception {
        // Initialize the database
        agreementRepository.saveAndFlush(agreement);
        when(mockAgreementSearchRepository.search(queryStringQuery("id:" + agreement.getId())))
            .thenReturn(Collections.singletonList(agreement));
        // Search the agreement
        restAgreementMockMvc.perform(get("/api/_search/agreements?query=id:" + agreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].agreementId").value(hasItem(DEFAULT_AGREEMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].numeroAgrement").value(hasItem(DEFAULT_NUMERO_AGREMENT)))
            .andExpect(jsonPath("$.[*].dateAttibution").value(hasItem(DEFAULT_DATE_ATTIBUTION.toString())))
            .andExpect(jsonPath("$.[*].dateDebValidite").value(hasItem(DEFAULT_DATE_DEB_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].dateFinValidite").value(hasItem(DEFAULT_DATE_FIN_VALIDITE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())))
            .andExpect(jsonPath("$.[*].niveauAgreement").value(hasItem(DEFAULT_NIVEAU_AGREEMENT)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agreement.class);
        Agreement agreement1 = new Agreement();
        agreement1.setId(1L);
        Agreement agreement2 = new Agreement();
        agreement2.setId(agreement1.getId());
        assertThat(agreement1).isEqualTo(agreement2);
        agreement2.setId(2L);
        assertThat(agreement1).isNotEqualTo(agreement2);
        agreement1.setId(null);
        assertThat(agreement1).isNotEqualTo(agreement2);
    }
}
