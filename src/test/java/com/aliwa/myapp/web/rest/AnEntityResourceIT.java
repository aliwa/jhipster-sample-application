package com.aliwa.myapp.web.rest;

import static com.aliwa.myapp.web.rest.TestUtil.sameInstant;
import static com.aliwa.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aliwa.myapp.IntegrationTest;
import com.aliwa.myapp.domain.AnEntity;
import com.aliwa.myapp.domain.enumeration.AEnum;
import com.aliwa.myapp.repository.AnEntityRepository;
import com.aliwa.myapp.service.dto.AnEntityDTO;
import com.aliwa.myapp.service.mapper.AnEntityMapper;
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
 * Integration tests for the {@link AnEntityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnEntityResourceIT {

    private static final String DEFAULT_A_STRING = "Qhuqky9";
    private static final String UPDATED_A_STRING = "Kzdwjp6";

    private static final Integer DEFAULT_A_INTEGER = 100;
    private static final Integer UPDATED_A_INTEGER = 101;

    private static final Long DEFAULT_A_LONG = 100L;
    private static final Long UPDATED_A_LONG = 101L;

    private static final BigDecimal DEFAULT_A_BIG_DECIMAL = new BigDecimal(100);
    private static final BigDecimal UPDATED_A_BIG_DECIMAL = new BigDecimal(101);

    private static final Float DEFAULT_A_FLOAT = 100F;
    private static final Float UPDATED_A_FLOAT = 101F;

    private static final Double DEFAULT_A_DOUBLE = 100D;
    private static final Double UPDATED_A_DOUBLE = 101D;

    private static final AEnum DEFAULT_A_ENUM = AEnum.FRENCH;
    private static final AEnum UPDATED_A_ENUM = AEnum.ENGLISH;

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

    private static final byte[] DEFAULT_IMAGE_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_A_TEXT_BLOB = "AAAAAAAAAA";
    private static final String UPDATED_A_TEXT_BLOB = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/an-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnEntityRepository anEntityRepository;

    @Autowired
    private AnEntityMapper anEntityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnEntityMockMvc;

    private AnEntity anEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnEntity createEntity(EntityManager em) {
        AnEntity anEntity = new AnEntity()
            .aString(DEFAULT_A_STRING)
            .aInteger(DEFAULT_A_INTEGER)
            .aLong(DEFAULT_A_LONG)
            .aBigDecimal(DEFAULT_A_BIG_DECIMAL)
            .aFloat(DEFAULT_A_FLOAT)
            .aDouble(DEFAULT_A_DOUBLE)
            .aEnum(DEFAULT_A_ENUM)
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
            .imageBlob(DEFAULT_IMAGE_BLOB)
            .imageBlobContentType(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(DEFAULT_A_TEXT_BLOB);
        return anEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnEntity createUpdatedEntity(EntityManager em) {
        AnEntity anEntity = new AnEntity()
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aEnum(UPDATED_A_ENUM)
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
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);
        return anEntity;
    }

    @BeforeEach
    public void initTest() {
        anEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createAnEntity() throws Exception {
        int databaseSizeBeforeCreate = anEntityRepository.findAll().size();
        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);
        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AnEntity testAnEntity = anEntityList.get(anEntityList.size() - 1);
        assertThat(testAnEntity.getaString()).isEqualTo(DEFAULT_A_STRING);
        assertThat(testAnEntity.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testAnEntity.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testAnEntity.getaBigDecimal()).isEqualByComparingTo(DEFAULT_A_BIG_DECIMAL);
        assertThat(testAnEntity.getaFloat()).isEqualTo(DEFAULT_A_FLOAT);
        assertThat(testAnEntity.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testAnEntity.getaEnum()).isEqualTo(DEFAULT_A_ENUM);
        assertThat(testAnEntity.getaBoolean()).isEqualTo(DEFAULT_A_BOOLEAN);
        assertThat(testAnEntity.getaLocalDate()).isEqualTo(DEFAULT_A_LOCAL_DATE);
        assertThat(testAnEntity.getaZonedDateTime()).isEqualTo(DEFAULT_A_ZONED_DATE_TIME);
        assertThat(testAnEntity.getaInstant()).isEqualTo(DEFAULT_A_INSTANT);
        assertThat(testAnEntity.getaDuration()).isEqualTo(DEFAULT_A_DURATION);
        assertThat(testAnEntity.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testAnEntity.getaBlob()).isEqualTo(DEFAULT_A_BLOB);
        assertThat(testAnEntity.getaBlobContentType()).isEqualTo(DEFAULT_A_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaAnyBlob()).isEqualTo(DEFAULT_A_ANY_BLOB);
        assertThat(testAnEntity.getaAnyBlobContentType()).isEqualTo(DEFAULT_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testAnEntity.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaTextBlob()).isEqualTo(DEFAULT_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void createAnEntityWithExistingId() throws Exception {
        // Create the AnEntity with an existing ID
        anEntity.setId(1L);
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        int databaseSizeBeforeCreate = anEntityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkaStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaString(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaIntegerIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaInteger(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLongIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaLong(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBigDecimalIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaBigDecimal(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaFloatIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaFloat(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDoubleIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaDouble(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaEnumIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaEnum(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBooleanIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaBoolean(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLocalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaLocalDate(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaZonedDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaZonedDateTime(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaInstantIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaInstant(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaDuration(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaUUIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = anEntityRepository.findAll().size();
        // set the field null
        anEntity.setaUUID(null);

        // Create the AnEntity, which fails.
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        restAnEntityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isBadRequest());

        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnEntities() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        // Get all the anEntityList
        restAnEntityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].aString").value(hasItem(DEFAULT_A_STRING)))
            .andExpect(jsonPath("$.[*].aInteger").value(hasItem(DEFAULT_A_INTEGER)))
            .andExpect(jsonPath("$.[*].aLong").value(hasItem(DEFAULT_A_LONG.intValue())))
            .andExpect(jsonPath("$.[*].aBigDecimal").value(hasItem(sameNumber(DEFAULT_A_BIG_DECIMAL))))
            .andExpect(jsonPath("$.[*].aFloat").value(hasItem(DEFAULT_A_FLOAT.doubleValue())))
            .andExpect(jsonPath("$.[*].aDouble").value(hasItem(DEFAULT_A_DOUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].aEnum").value(hasItem(DEFAULT_A_ENUM.toString())))
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
            .andExpect(jsonPath("$.[*].imageBlobContentType").value(hasItem(DEFAULT_IMAGE_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB))))
            .andExpect(jsonPath("$.[*].aTextBlob").value(hasItem(DEFAULT_A_TEXT_BLOB.toString())));
    }

    @Test
    @Transactional
    void getAnEntity() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        // Get the anEntity
        restAnEntityMockMvc
            .perform(get(ENTITY_API_URL_ID, anEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anEntity.getId().intValue()))
            .andExpect(jsonPath("$.aString").value(DEFAULT_A_STRING))
            .andExpect(jsonPath("$.aInteger").value(DEFAULT_A_INTEGER))
            .andExpect(jsonPath("$.aLong").value(DEFAULT_A_LONG.intValue()))
            .andExpect(jsonPath("$.aBigDecimal").value(sameNumber(DEFAULT_A_BIG_DECIMAL)))
            .andExpect(jsonPath("$.aFloat").value(DEFAULT_A_FLOAT.doubleValue()))
            .andExpect(jsonPath("$.aDouble").value(DEFAULT_A_DOUBLE.doubleValue()))
            .andExpect(jsonPath("$.aEnum").value(DEFAULT_A_ENUM.toString()))
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
            .andExpect(jsonPath("$.imageBlobContentType").value(DEFAULT_IMAGE_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BLOB)))
            .andExpect(jsonPath("$.aTextBlob").value(DEFAULT_A_TEXT_BLOB.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnEntity() throws Exception {
        // Get the anEntity
        restAnEntityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnEntity() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();

        // Update the anEntity
        AnEntity updatedAnEntity = anEntityRepository.findById(anEntity.getId()).get();
        // Disconnect from session so that the updates on updatedAnEntity are not directly saved in db
        em.detach(updatedAnEntity);
        updatedAnEntity
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aEnum(UPDATED_A_ENUM)
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
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(updatedAnEntity);

        restAnEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
        AnEntity testAnEntity = anEntityList.get(anEntityList.size() - 1);
        assertThat(testAnEntity.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testAnEntity.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testAnEntity.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testAnEntity.getaBigDecimal()).isEqualTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testAnEntity.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testAnEntity.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testAnEntity.getaEnum()).isEqualTo(UPDATED_A_ENUM);
        assertThat(testAnEntity.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testAnEntity.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testAnEntity.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testAnEntity.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testAnEntity.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testAnEntity.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testAnEntity.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testAnEntity.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testAnEntity.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testAnEntity.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void putNonExistingAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anEntityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnEntityWithPatch() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();

        // Update the anEntity using partial update
        AnEntity partialUpdatedAnEntity = new AnEntity();
        partialUpdatedAnEntity.setId(anEntity.getId());

        partialUpdatedAnEntity
            .aString(UPDATED_A_STRING)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aInstant(UPDATED_A_INSTANT)
            .aDuration(UPDATED_A_DURATION)
            .aBlob(UPDATED_A_BLOB)
            .aBlobContentType(UPDATED_A_BLOB_CONTENT_TYPE)
            .aAnyBlob(UPDATED_A_ANY_BLOB)
            .aAnyBlobContentType(UPDATED_A_ANY_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);

        restAnEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnEntity))
            )
            .andExpect(status().isOk());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
        AnEntity testAnEntity = anEntityList.get(anEntityList.size() - 1);
        assertThat(testAnEntity.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testAnEntity.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testAnEntity.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testAnEntity.getaBigDecimal()).isEqualByComparingTo(DEFAULT_A_BIG_DECIMAL);
        assertThat(testAnEntity.getaFloat()).isEqualTo(DEFAULT_A_FLOAT);
        assertThat(testAnEntity.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testAnEntity.getaEnum()).isEqualTo(DEFAULT_A_ENUM);
        assertThat(testAnEntity.getaBoolean()).isEqualTo(DEFAULT_A_BOOLEAN);
        assertThat(testAnEntity.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testAnEntity.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testAnEntity.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testAnEntity.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testAnEntity.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testAnEntity.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testAnEntity.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testAnEntity.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testAnEntity.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void fullUpdateAnEntityWithPatch() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();

        // Update the anEntity using partial update
        AnEntity partialUpdatedAnEntity = new AnEntity();
        partialUpdatedAnEntity.setId(anEntity.getId());

        partialUpdatedAnEntity
            .aString(UPDATED_A_STRING)
            .aInteger(UPDATED_A_INTEGER)
            .aLong(UPDATED_A_LONG)
            .aBigDecimal(UPDATED_A_BIG_DECIMAL)
            .aFloat(UPDATED_A_FLOAT)
            .aDouble(UPDATED_A_DOUBLE)
            .aEnum(UPDATED_A_ENUM)
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
            .imageBlob(UPDATED_IMAGE_BLOB)
            .imageBlobContentType(UPDATED_IMAGE_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);

        restAnEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnEntity))
            )
            .andExpect(status().isOk());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
        AnEntity testAnEntity = anEntityList.get(anEntityList.size() - 1);
        assertThat(testAnEntity.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testAnEntity.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testAnEntity.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testAnEntity.getaBigDecimal()).isEqualByComparingTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testAnEntity.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testAnEntity.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testAnEntity.getaEnum()).isEqualTo(UPDATED_A_ENUM);
        assertThat(testAnEntity.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testAnEntity.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testAnEntity.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testAnEntity.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testAnEntity.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testAnEntity.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testAnEntity.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testAnEntity.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testAnEntity.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testAnEntity.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testAnEntity.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void patchNonExistingAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anEntityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnEntity() throws Exception {
        int databaseSizeBeforeUpdate = anEntityRepository.findAll().size();
        anEntity.setId(count.incrementAndGet());

        // Create the AnEntity
        AnEntityDTO anEntityDTO = anEntityMapper.toDto(anEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnEntityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnEntity in the database
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnEntity() throws Exception {
        // Initialize the database
        anEntityRepository.saveAndFlush(anEntity);

        int databaseSizeBeforeDelete = anEntityRepository.findAll().size();

        // Delete the anEntity
        restAnEntityMockMvc
            .perform(delete(ENTITY_API_URL_ID, anEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnEntity> anEntityList = anEntityRepository.findAll();
        assertThat(anEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
