package com.myapp.web.rest;

import static com.myapp.web.rest.TestUtil.sameInstant;
import static com.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myapp.IntegrationTest;
import com.myapp.domain.A;
import com.myapp.domain.enumeration.AEnum;
import com.myapp.repository.ARepository;
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
 * Integration tests for the {@link AResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AResourceIT {

    private static final String DEFAULT_A_STRING = "Vk3";
    private static final String UPDATED_A_STRING = "Xlmpyx6";

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

    private static final String ENTITY_API_URL = "/api/as";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ARepository aRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAMockMvc;

    private A a;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static A createEntity(EntityManager em) {
        A a = new A()
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
        return a;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static A createUpdatedEntity(EntityManager em) {
        A a = new A()
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
        return a;
    }

    @BeforeEach
    public void initTest() {
        a = createEntity(em);
    }

    @Test
    @Transactional
    void createA() throws Exception {
        int databaseSizeBeforeCreate = aRepository.findAll().size();
        // Create the A
        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isCreated());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeCreate + 1);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getaString()).isEqualTo(DEFAULT_A_STRING);
        assertThat(testA.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testA.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testA.getaBigDecimal()).isEqualByComparingTo(DEFAULT_A_BIG_DECIMAL);
        assertThat(testA.getaFloat()).isEqualTo(DEFAULT_A_FLOAT);
        assertThat(testA.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testA.getaEnum()).isEqualTo(DEFAULT_A_ENUM);
        assertThat(testA.getaBoolean()).isEqualTo(DEFAULT_A_BOOLEAN);
        assertThat(testA.getaLocalDate()).isEqualTo(DEFAULT_A_LOCAL_DATE);
        assertThat(testA.getaZonedDateTime()).isEqualTo(DEFAULT_A_ZONED_DATE_TIME);
        assertThat(testA.getaInstant()).isEqualTo(DEFAULT_A_INSTANT);
        assertThat(testA.getaDuration()).isEqualTo(DEFAULT_A_DURATION);
        assertThat(testA.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testA.getaBlob()).isEqualTo(DEFAULT_A_BLOB);
        assertThat(testA.getaBlobContentType()).isEqualTo(DEFAULT_A_BLOB_CONTENT_TYPE);
        assertThat(testA.getaAnyBlob()).isEqualTo(DEFAULT_A_ANY_BLOB);
        assertThat(testA.getaAnyBlobContentType()).isEqualTo(DEFAULT_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testA.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testA.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testA.getaTextBlob()).isEqualTo(DEFAULT_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void createAWithExistingId() throws Exception {
        // Create the A with an existing ID
        a.setId(1L);

        int databaseSizeBeforeCreate = aRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkaStringIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaString(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaIntegerIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaInteger(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLongIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaLong(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBigDecimalIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaBigDecimal(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaFloatIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaFloat(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDoubleIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaDouble(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaEnumIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaEnum(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaBooleanIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaBoolean(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaLocalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaLocalDate(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaZonedDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaZonedDateTime(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaInstantIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaInstant(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaDuration(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkaUUIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = aRepository.findAll().size();
        // set the field null
        a.setaUUID(null);

        // Create the A, which fails.

        restAMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isBadRequest());

        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAS() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        // Get all the aList
        restAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(a.getId().intValue())))
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
    void getA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        // Get the a
        restAMockMvc
            .perform(get(ENTITY_API_URL_ID, a.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(a.getId().intValue()))
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
    void getNonExistingA() throws Exception {
        // Get the a
        restAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        int databaseSizeBeforeUpdate = aRepository.findAll().size();

        // Update the a
        A updatedA = aRepository.findById(a.getId()).get();
        // Disconnect from session so that the updates on updatedA are not directly saved in db
        em.detach(updatedA);
        updatedA
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

        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedA.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedA))
            )
            .andExpect(status().isOk());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testA.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testA.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testA.getaBigDecimal()).isEqualTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testA.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testA.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testA.getaEnum()).isEqualTo(UPDATED_A_ENUM);
        assertThat(testA.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testA.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testA.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testA.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testA.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testA.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testA.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testA.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testA.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testA.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testA.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testA.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testA.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void putNonExistingA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, a.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a))
            )
            .andExpect(status().isBadRequest());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(a))
            )
            .andExpect(status().isBadRequest());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAWithPatch() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        int databaseSizeBeforeUpdate = aRepository.findAll().size();

        // Update the a using partial update
        A partialUpdatedA = new A();
        partialUpdatedA.setId(a.getId());

        partialUpdatedA
            .aString(UPDATED_A_STRING)
            .aEnum(UPDATED_A_ENUM)
            .aBoolean(UPDATED_A_BOOLEAN)
            .aLocalDate(UPDATED_A_LOCAL_DATE)
            .aZonedDateTime(UPDATED_A_ZONED_DATE_TIME)
            .aDuration(UPDATED_A_DURATION)
            .aAnyBlob(UPDATED_A_ANY_BLOB)
            .aAnyBlobContentType(UPDATED_A_ANY_BLOB_CONTENT_TYPE)
            .aTextBlob(UPDATED_A_TEXT_BLOB);

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedA))
            )
            .andExpect(status().isOk());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testA.getaInteger()).isEqualTo(DEFAULT_A_INTEGER);
        assertThat(testA.getaLong()).isEqualTo(DEFAULT_A_LONG);
        assertThat(testA.getaBigDecimal()).isEqualByComparingTo(DEFAULT_A_BIG_DECIMAL);
        assertThat(testA.getaFloat()).isEqualTo(DEFAULT_A_FLOAT);
        assertThat(testA.getaDouble()).isEqualTo(DEFAULT_A_DOUBLE);
        assertThat(testA.getaEnum()).isEqualTo(UPDATED_A_ENUM);
        assertThat(testA.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testA.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testA.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testA.getaInstant()).isEqualTo(DEFAULT_A_INSTANT);
        assertThat(testA.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testA.getaUUID()).isEqualTo(DEFAULT_A_UUID);
        assertThat(testA.getaBlob()).isEqualTo(DEFAULT_A_BLOB);
        assertThat(testA.getaBlobContentType()).isEqualTo(DEFAULT_A_BLOB_CONTENT_TYPE);
        assertThat(testA.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testA.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testA.getImageBlob()).isEqualTo(DEFAULT_IMAGE_BLOB);
        assertThat(testA.getImageBlobContentType()).isEqualTo(DEFAULT_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testA.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void fullUpdateAWithPatch() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        int databaseSizeBeforeUpdate = aRepository.findAll().size();

        // Update the a using partial update
        A partialUpdatedA = new A();
        partialUpdatedA.setId(a.getId());

        partialUpdatedA
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

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedA.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedA))
            )
            .andExpect(status().isOk());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
        A testA = aList.get(aList.size() - 1);
        assertThat(testA.getaString()).isEqualTo(UPDATED_A_STRING);
        assertThat(testA.getaInteger()).isEqualTo(UPDATED_A_INTEGER);
        assertThat(testA.getaLong()).isEqualTo(UPDATED_A_LONG);
        assertThat(testA.getaBigDecimal()).isEqualByComparingTo(UPDATED_A_BIG_DECIMAL);
        assertThat(testA.getaFloat()).isEqualTo(UPDATED_A_FLOAT);
        assertThat(testA.getaDouble()).isEqualTo(UPDATED_A_DOUBLE);
        assertThat(testA.getaEnum()).isEqualTo(UPDATED_A_ENUM);
        assertThat(testA.getaBoolean()).isEqualTo(UPDATED_A_BOOLEAN);
        assertThat(testA.getaLocalDate()).isEqualTo(UPDATED_A_LOCAL_DATE);
        assertThat(testA.getaZonedDateTime()).isEqualTo(UPDATED_A_ZONED_DATE_TIME);
        assertThat(testA.getaInstant()).isEqualTo(UPDATED_A_INSTANT);
        assertThat(testA.getaDuration()).isEqualTo(UPDATED_A_DURATION);
        assertThat(testA.getaUUID()).isEqualTo(UPDATED_A_UUID);
        assertThat(testA.getaBlob()).isEqualTo(UPDATED_A_BLOB);
        assertThat(testA.getaBlobContentType()).isEqualTo(UPDATED_A_BLOB_CONTENT_TYPE);
        assertThat(testA.getaAnyBlob()).isEqualTo(UPDATED_A_ANY_BLOB);
        assertThat(testA.getaAnyBlobContentType()).isEqualTo(UPDATED_A_ANY_BLOB_CONTENT_TYPE);
        assertThat(testA.getImageBlob()).isEqualTo(UPDATED_IMAGE_BLOB);
        assertThat(testA.getImageBlobContentType()).isEqualTo(UPDATED_IMAGE_BLOB_CONTENT_TYPE);
        assertThat(testA.getaTextBlob()).isEqualTo(UPDATED_A_TEXT_BLOB);
    }

    @Test
    @Transactional
    void patchNonExistingA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, a.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(a))
            )
            .andExpect(status().isBadRequest());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(a))
            )
            .andExpect(status().isBadRequest());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamA() throws Exception {
        int databaseSizeBeforeUpdate = aRepository.findAll().size();
        a.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(a)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the A in the database
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteA() throws Exception {
        // Initialize the database
        aRepository.saveAndFlush(a);

        int databaseSizeBeforeDelete = aRepository.findAll().size();

        // Delete the a
        restAMockMvc.perform(delete(ENTITY_API_URL_ID, a.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<A> aList = aRepository.findAll();
        assertThat(aList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
