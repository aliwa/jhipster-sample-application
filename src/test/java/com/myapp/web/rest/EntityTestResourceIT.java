package com.myapp.web.rest;

import static com.myapp.web.rest.TestUtil.sameInstant;
import static com.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.EntityTest;
import com.myapp.repository.EntityTestRepository;
import com.myapp.service.dto.EntityTestDTO;
import com.myapp.service.mapper.EntityTestMapper;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EntityTestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntityTestResourceIT {

    private static final String DEFAULT_A_STRING = "}w`X@UMx.8";
    private static final String UPDATED_A_STRING = "m!\"@z%R4\"%.w";

    private static final Integer DEFAULT_A_INTEGER = 10;
    private static final Integer UPDATED_A_INTEGER = 11;

    private static final Long DEFAULT_A_LONG = 10L;
    private static final Long UPDATED_A_LONG = 11L;

    private static final BigDecimal DEFAULT_A_BIG_DECIMAL = new BigDecimal(10);
    private static final BigDecimal UPDATED_A_BIG_DECIMAL = new BigDecimal(11);

    private static final Float DEFAULT_A_FLOAT = 10F;
    private static final Float UPDATED_A_FLOAT = 11F;

    private static final Double DEFAULT_A_DOUBLE = 10D;
    private static final Double UPDATED_A_DOUBLE = 11D;

    private static final Boolean DEFAULT_A_BOOLEAN = false;
    private static final Boolean UPDATED_A_BOOLEAN = true;

    private static final LocalDate DEFAULT_A_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_A_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_A_ZONED_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_A_ZONED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Instant DEFAULT_A_INSTANT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_A_INSTANT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Duration DEFAULT_A_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_A_DURATION = Duration.ofHours(12);

    private static final UUID DEFAULT_A_UUID = UUID.randomUUID();
    private static final UUID UPDATED_A_UUID = UUID.randomUUID();

    private static final byte[] DEFAULT_A_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_A_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_A_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_A_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_A_ANY_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_A_ANY_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_A_ANY_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_A_ANY_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_A_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_A_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_A_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_A_TEXT_BLOB = "AAAAAAAAAA";
    private static final String UPDATED_A_TEXT_BLOB = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/entity-tests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntityTestRepository entityTestRepository;

    @Autowired
    private EntityTestMapper entityTestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntityTestMockMvc;

    private EntityTest entityTest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityTest createEntity(EntityManager em) {
        EntityTest entityTest = new EntityTest()
            .aString(DEFAULT_A_STRING)
            .aInteger(DEFAULT_A_INTEGER)
            .aLong(DEFAULT_A_LONG)
            .aBigDecimal(DEFAULT_A_BIG_DECIMAL)
            .aFloat(DEFAULT_A_FLOAT)
            .aDouble(DEFAULT_A_DOUBLE)
            .aBoolean(DEFAULT_A_BOOLEAN)
            .aLocalDate(DEFAULT_A_LOCAL_DATE)
            .aZonedDateTime(DEFAULT_A_ZONED_DATE_TIME)
            .aInstant(DEFAULT_A_INSTANT)
            .aDuration(DEFAULT_A_DURATION)
            .aUUID(DEFAULT_A_UUID)
            .aBlob(DEFAULT_A_BLOB)
            .aBlobContentType(DEFAULT_A_BLOB_CONTENT_TYPE)
            .aAnyBlob(DEFAULT_A_ANY_BLOB)
            .aAnyBlobContentType(DEFAULT_A_ANY_BLOB_CONTENT_TYPE)
            .aImageBlob(DEFAULT_A_IMAGE_BLOB)
            .aImageBlobContentType(DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(DEFAULT_A_TEXT_BLOB);
        return entityTest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityTest createUpdatedEntity(EntityManager em) {
        EntityTest entityTest = new EntityTest()
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aBoolean(UPDATED_A_BOOLEAN)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aInstant(UPDATED_A_INSTANT)
            .aDuration(UPDATED_A_DURATION)
            .aUUID(UPDATED_A_UUID)
            .aBlob(UPDATED_A_BLOB)
            .aBlobContentType(UPDATED_A_BLOB_CONTENT_TYPE)
            .aAnyBlob(UPDATED_A_ANY_BLOB)
            .aAnyBlobContentType(UPDATED_A_ANY_BLOB_CONTENT_TYPE)
            .aImageBlob(UPDATED_A_IMAGE_BLOB)
            .aImageBlobContentType(UPDATED_A_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);
        return entityTest;
    }

    @BeforeEach
    public void initTest() {
        entityTest = createEntity(em);
    }

    @Test
    @Transactional
    void createEntityTest() throws Exception {
        int databaseSizeBeforeCreate = entityTestRepository.findAll().size();
        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);
        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isCreated());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeCreate + 1);
        EntityTest testEntityTest = entityTestList.get(entityTestList.size() - 1);
        assertThat(testEntityTest.getaString()).isEqualTo(DEFAULT_A_STRING);
        assertThat(testEntityTest.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testEntityTest.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testEntityTest.getaBigDecimal()).isEqualByComparingTo(DEFAULT_A_BIG_DECIMAL);
        assertThat(testEntityTest.getaFloat()).isEqualTo(DEFAULT_A_FLOAT);
        assertThat(testEntityTest.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testEntityTest.getaBoolean()).isEqualTo(DEFAULT_A_BOOLEAN);
        assertThat(testEntityTest.getaLocalDate()).isEqualTo(DEFAULT_A_LOCAL_DATE);
        assertThat(testEntityTest.getaZonedDateTime()).isEqualTo(DEFAULT_A_ZONED_DATE_TIME);
        assertThat(testEntityTest.getaInstant()).isEqualTo(DEFAULT_A_INSTANT);
        assertThat(testEntityTest.getaDuration()).isEqualTo(DEFAULT_A_DURATION);
        assertThat(testEntityTest.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testEntityTest.getaBlob()).isEqualTo(DEFAULT_A_BLOB);
        assertThat(testEntityTest.getaBlobContentType()).isEqualTo(DEFAULT_A_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaAnyBlob()).isEqualTo(DEFAULT_A_ANY_BLOB);
        assertThat(testEntityTest.getaAnyBlobContentType()).isEqualTo(DEFAULT_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaImageBlob()).isEqualTo(DEFAULT_A_IMAGE_BLOB);
        assertThat(testEntityTest.getaImageBlobContentType()).isEqualTo(DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaTextBlob()).isEqualTo(DEFAULT_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void createEntityTestWithExistingId() throws Exception {
        // Create the EntityTest with an existing ID
        entityTest.setId(1L);
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        int databaseSizeBeforeCreate = entityTestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkaStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaString(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaIntegerIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaInteger(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLongIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaLong(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBigDecimalIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaBigDecimal(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaFloatIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaFloat(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDoubleIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaDouble(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBooleanIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaBoolean(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLocalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaLocalDate(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaZonedDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaZonedDateTime(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaInstantIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaInstant(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaDuration(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaUUIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = entityTestRepository.findAll().size();
        // set the field null
        entityTest.setaUUID(null);

        // Create the EntityTest, which fails.
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        restEntityTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isBadRequest());

        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntityTests() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        // Get all the entityTestList
        restEntityTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].aString").value(hasItem(DEFAULT_A_STRING)))
            .andExpect(jsonPath("$.[*].aInteger").value(hasItem(DEFAULT_A_INTEGER)))
            .andExpect(jsonPath("$.[*].aLong").value(hasItem(DEFAULT_A_LONG.intValue())))
            .andExpect(jsonPath("$.[*].aBigDecimal").value(hasItem(sameNumber(DEFAULT_A_BIG_DECIMAL))))
            .andExpect(jsonPath("$.[*].aFloat").value(hasItem(DEFAULT_A_FLOAT.doubleValue())))
            .andExpect(jsonPath("$.[*].aDouble").value(hasItem(DEFAULT_A_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].aBoolean").value(hasItem(DEFAULT_A_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].aLocalDate").value(hasItem(DEFAULT_A_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].aZonedDateTime").value(hasItem(sameInstant(DEFAULT_A_ZONED_DATE_TIME))))
            .andExpect(jsonPath("$.[*].aInstant").value(hasItem(DEFAULT_A_INSTANT.toString())))
            .andExpect(jsonPath("$.[*].aDuration").value(hasItem(DEFAULT_A_DURATION.toString())))
            .andExpect(jsonPath("$.[*].aUUID").value(hasItem(DEFAULT_A_UUID.toString())))
            .andExpect(jsonPath("$.[*].aBlobContentType").value(hasItem(DEFAULT_A_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_A_BLOB))))
            .andExpect(jsonPath("$.[*].aAnyBlobContentType").value(hasItem(DEFAULT_A_ANY_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aAnyBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_A_ANY_BLOB))))
            .andExpect(jsonPath("$.[*].aImageBlobContentType").value(hasItem(DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].aImageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_A_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].aTextBlob").value(hasItem(DEFAULT_A_TEXT_BLOB.toString())));
    }

    @Test
    @Transactional
    void getEntityTest() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        // Get the entityTest
        restEntityTestMockMvc
            .perform(get(ENTITY_API_URL_ID, entityTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entityTest.getId().intValue()))
            .andExpect(jsonPath("$.aString").value(DEFAULT_A_STRING))
            .andExpect(jsonPath("$.aInteger").value(DEFAULT_A_INTEGER))
            .andExpect(jsonPath("$.aLong").value(DEFAULT_A_LONG.intValue()))
            .andExpect(jsonPath("$.aBigDecimal").value(sameNumber(DEFAULT_A_BIG_DECIMAL)))
            .andExpect(jsonPath("$.aFloat").value(DEFAULT_A_FLOAT.doubleValue()))
            .andExpect(jsonPath("$.aDouble").value(DEFAULT_A_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.aBoolean").value(DEFAULT_A_BOOLEAN.booleanValue()))
            .andExpect(jsonPath("$.aLocalDate").value(DEFAULT_A_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.aZonedDateTime").value(sameInstant(DEFAULT_A_ZONED_DATE_TIME)))
            .andExpect(jsonPath("$.aInstant").value(DEFAULT_A_INSTANT.toString()))
            .andExpect(jsonPath("$.aDuration").value(DEFAULT_A_DURATION.toString()))
            .andExpect(jsonPath("$.aUUID").value(DEFAULT_A_UUID.toString()))
            .andExpect(jsonPath("$.aBlobContentType").value(DEFAULT_A_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.aBlob").value(Base64Utils.encodeToString(DEFAULT_A_BLOB)))
            .andExpect(jsonPath("$.aAnyBlobContentType").value(DEFAULT_A_ANY_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.aAnyBlob").value(Base64Utils.encodeToString(DEFAULT_A_ANY_BLOB)))
            .andExpect(jsonPath("$.aImageBlobContentType").value(DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.aImageBlob").value(Base64Utils.encodeToString(DEFAULT_A_IMAGE_BLOB)))
            .andExpect(jsonPath("$.aTextBlob").value(DEFAULT_A_TEXT_BLOB.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEntityTest() throws Exception {
        // Get the entityTest
        restEntityTestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntityTest() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();

        // Update the entityTest
        EntityTest updatedEntityTest = entityTestRepository.findById(entityTest.getId()).get();
        // Disconnect from session so that the updates on updatedEntityTest are not directly saved in db
        em.detach(updatedEntityTest);
        updatedEntityTest
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aBoolean(UPDATED_A_BOOLEAN)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aInstant(UPDATED_A_INSTANT)
            .aDuration(UPDATED_A_DURATION)
            .aUUID(UPDATED_A_UUID)
            .aBlob(UPDATED_A_BLOB)
            .aBlobContentType(UPDATED_A_BLOB_CONTENT_TYPE)
            .aAnyBlob(UPDATED_A_ANY_BLOB)
            .aAnyBlobContentType(UPDATED_A_ANY_BLOB_CONTENT_TYPE)
            .aImageBlob(UPDATED_A_IMAGE_BLOB)
            .aImageBlobContentType(UPDATED_A_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(updatedEntityTest);

        restEntityTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entityTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isOk());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
        EntityTest testEntityTest = entityTestList.get(entityTestList.size() - 1);
        assertThat(testEntityTest.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testEntityTest.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testEntityTest.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testEntityTest.getaBigDecimal()).isEqualTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testEntityTest.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testEntityTest.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testEntityTest.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testEntityTest.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testEntityTest.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testEntityTest.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testEntityTest.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testEntityTest.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testEntityTest.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testEntityTest.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testEntityTest.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaImageBlob()).isEqualTo(UPDATED_A_IMAGE_BLOB);
        assertThat(testEntityTest.getaImageBlobContentType()).isEqualTo(UPDATED_A_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void putNonExistingEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entityTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntityTestWithPatch() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();

        // Update the entityTest using partial update
        EntityTest partialUpdatedEntityTest = new EntityTest();
        partialUpdatedEntityTest.setId(entityTest.getId());

        partialUpdatedEntityTest
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aBoolean(UPDATED_A_BOOLEAN)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aInstant(UPDATED_A_INSTANT)
            .aBlob(UPDATED_A_BLOB)
            .aBlobContentType(UPDATED_A_BLOB_CONTENT_TYPE);

        restEntityTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntityTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntityTest))
            )
            .andExpect(status().isOk());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
        EntityTest testEntityTest = entityTestList.get(entityTestList.size() - 1);
        assertThat(testEntityTest.getaString()).isEqualTo(DEFAULT_A_STRING);
        assertThat(testEntityTest.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testEntityTest.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testEntityTest.getaBigDecimal()).isEqualByComparingTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testEntityTest.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testEntityTest.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testEntityTest.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testEntityTest.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testEntityTest.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testEntityTest.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testEntityTest.getaDuration()).isEqualTo(DEFAULT_A_DURATION);
        assertThat(testEntityTest.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testEntityTest.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testEntityTest.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaAnyBlob()).isEqualTo(DEFAULT_A_ANY_BLOB);
        assertThat(testEntityTest.getaAnyBlobContentType()).isEqualTo(DEFAULT_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaImageBlob()).isEqualTo(DEFAULT_A_IMAGE_BLOB);
        assertThat(testEntityTest.getaImageBlobContentType()).isEqualTo(DEFAULT_A_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaTextBlob()).isEqualTo(DEFAULT_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void fullUpdateEntityTestWithPatch() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();

        // Update the entityTest using partial update
        EntityTest partialUpdatedEntityTest = new EntityTest();
        partialUpdatedEntityTest.setId(entityTest.getId());

        partialUpdatedEntityTest
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aBoolean(UPDATED_A_BOOLEAN)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aInstant(UPDATED_A_INSTANT)
            .aDuration(UPDATED_A_DURATION)
            .aUUID(UPDATED_A_UUID)
            .aBlob(UPDATED_A_BLOB)
            .aBlobContentType(UPDATED_A_BLOB_CONTENT_TYPE)
            .aAnyBlob(UPDATED_A_ANY_BLOB)
            .aAnyBlobContentType(UPDATED_A_ANY_BLOB_CONTENT_TYPE)
            .aImageBlob(UPDATED_A_IMAGE_BLOB)
            .aImageBlobContentType(UPDATED_A_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);

        restEntityTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntityTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntityTest))
            )
            .andExpect(status().isOk());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
        EntityTest testEntityTest = entityTestList.get(entityTestList.size() - 1);
        assertThat(testEntityTest.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testEntityTest.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testEntityTest.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testEntityTest.getaBigDecimal()).isEqualByComparingTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testEntityTest.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testEntityTest.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testEntityTest.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testEntityTest.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testEntityTest.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testEntityTest.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testEntityTest.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testEntityTest.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testEntityTest.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testEntityTest.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testEntityTest.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaImageBlob()).isEqualTo(UPDATED_A_IMAGE_BLOB);
        assertThat(testEntityTest.getaImageBlobContentType()).isEqualTo(UPDATED_A_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testEntityTest.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void patchNonExistingEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entityTestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntityTest() throws Exception {
        int databaseSizeBeforeUpdate = entityTestRepository.findAll().size();
        entityTest.setId(count.incrementAndGet());

        // Create the EntityTest
        EntityTestDTO entityTestDTO = entityTestMapper.toDto(entityTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityTestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entityTestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntityTest in the database
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntityTest() throws Exception {
        // Initialize the database
        entityTestRepository.saveAndFlush(entityTest);

        int databaseSizeBeforeDelete = entityTestRepository.findAll().size();

        // Delete the entityTest
        restEntityTestMockMvc
            .perform(delete(ENTITY_API_URL_ID, entityTest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityTest> entityTestList = entityTestRepository.findAll();
        assertThat(entityTestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
