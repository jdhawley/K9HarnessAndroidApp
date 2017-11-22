package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.K9BackEndApp;

import com.mycompany.myapp.domain.Dog;
import com.mycompany.myapp.repository.DogRepository;
import com.mycompany.myapp.service.DogService;
import com.mycompany.myapp.service.dto.DogDTO;
import com.mycompany.myapp.service.mapper.DogMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DogResource REST controller.
 *
 * @see DogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = K9BackEndApp.class)
public class DogResourceIntTest {

    private static final Double DEFAULT_CORETEMP = 1D;
    private static final Double UPDATED_CORETEMP = 2D;

    private static final Double DEFAULT_RESPIRATORYRATE = 1D;
    private static final Double UPDATED_RESPIRATORYRATE = 2D;

    private static final Double DEFAULT_ABTEMP = 1D;
    private static final Double UPDATED_ABTEMP = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_HEARTRATE = 1D;
    private static final Double UPDATED_HEARTRATE = 2D;

    private static final Double DEFAULT_MAXCORETEMP = 1D;
    private static final Double UPDATED_MAXCORETEMP = 2D;

    private static final Double DEFAULT_MAXRESPIRATORYRATE = 1D;
    private static final Double UPDATED_MAXRESPIRATORYRATE = 2D;

    private static final Double DEFAULT_MAXABTEMP = 1D;
    private static final Double UPDATED_MAXABTEMP = 2D;

    private static final Double DEFAULT_MAXHEARTRATE = 1D;
    private static final Double UPDATED_MAXHEARTRATE = 2D;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private DogService dogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDogMockMvc;

    private Dog dog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DogResource dogResource = new DogResource(dogService);
        this.restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dog createEntity(EntityManager em) {
        Dog dog = new Dog()
            .coretemp(DEFAULT_CORETEMP)
            .respiratoryrate(DEFAULT_RESPIRATORYRATE)
            .abtemp(DEFAULT_ABTEMP)
            .name(DEFAULT_NAME)
            .heartrate(DEFAULT_HEARTRATE)
            .maxcoretemp(DEFAULT_MAXCORETEMP)
            .maxrespiratoryrate(DEFAULT_MAXRESPIRATORYRATE)
            .maxabtemp(DEFAULT_MAXABTEMP)
            .maxheartrate(DEFAULT_MAXHEARTRATE);
        return dog;
    }

    @Before
    public void initTest() {
        dog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDog() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate + 1);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getCoretemp()).isEqualTo(DEFAULT_CORETEMP);
        assertThat(testDog.getRespiratoryrate()).isEqualTo(DEFAULT_RESPIRATORYRATE);
        assertThat(testDog.getAbtemp()).isEqualTo(DEFAULT_ABTEMP);
        assertThat(testDog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDog.getHeartrate()).isEqualTo(DEFAULT_HEARTRATE);
        assertThat(testDog.getMaxcoretemp()).isEqualTo(DEFAULT_MAXCORETEMP);
        assertThat(testDog.getMaxrespiratoryrate()).isEqualTo(DEFAULT_MAXRESPIRATORYRATE);
        assertThat(testDog.getMaxabtemp()).isEqualTo(DEFAULT_MAXABTEMP);
        assertThat(testDog.getMaxheartrate()).isEqualTo(DEFAULT_MAXHEARTRATE);
    }

    @Test
    @Transactional
    public void createDogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog with an existing ID
        dog.setId(1L);
        DogDTO dogDTO = dogMapper.toDto(dog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDogs() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get all the dogList
        restDogMockMvc.perform(get("/api/dogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dog.getId().intValue())))
            .andExpect(jsonPath("$.[*].coretemp").value(hasItem(DEFAULT_CORETEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].respiratoryrate").value(hasItem(DEFAULT_RESPIRATORYRATE.doubleValue())))
            .andExpect(jsonPath("$.[*].abtemp").value(hasItem(DEFAULT_ABTEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].heartrate").value(hasItem(DEFAULT_HEARTRATE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxcoretemp").value(hasItem(DEFAULT_MAXCORETEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].maxrespiratoryrate").value(hasItem(DEFAULT_MAXRESPIRATORYRATE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxabtemp").value(hasItem(DEFAULT_MAXABTEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].maxheartrate").value(hasItem(DEFAULT_MAXHEARTRATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", dog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dog.getId().intValue()))
            .andExpect(jsonPath("$.coretemp").value(DEFAULT_CORETEMP.doubleValue()))
            .andExpect(jsonPath("$.respiratoryrate").value(DEFAULT_RESPIRATORYRATE.doubleValue()))
            .andExpect(jsonPath("$.abtemp").value(DEFAULT_ABTEMP.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.heartrate").value(DEFAULT_HEARTRATE.doubleValue()))
            .andExpect(jsonPath("$.maxcoretemp").value(DEFAULT_MAXCORETEMP.doubleValue()))
            .andExpect(jsonPath("$.maxrespiratoryrate").value(DEFAULT_MAXRESPIRATORYRATE.doubleValue()))
            .andExpect(jsonPath("$.maxabtemp").value(DEFAULT_MAXABTEMP.doubleValue()))
            .andExpect(jsonPath("$.maxheartrate").value(DEFAULT_MAXHEARTRATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDog() throws Exception {
        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog
        Dog updatedDog = dogRepository.findOne(dog.getId());
        updatedDog
            .coretemp(UPDATED_CORETEMP)
            .respiratoryrate(UPDATED_RESPIRATORYRATE)
            .abtemp(UPDATED_ABTEMP)
            .name(UPDATED_NAME)
            .heartrate(UPDATED_HEARTRATE)
            .maxcoretemp(UPDATED_MAXCORETEMP)
            .maxrespiratoryrate(UPDATED_MAXRESPIRATORYRATE)
            .maxabtemp(UPDATED_MAXABTEMP)
            .maxheartrate(UPDATED_MAXHEARTRATE);
        DogDTO dogDTO = dogMapper.toDto(updatedDog);

        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getCoretemp()).isEqualTo(UPDATED_CORETEMP);
        assertThat(testDog.getRespiratoryrate()).isEqualTo(UPDATED_RESPIRATORYRATE);
        assertThat(testDog.getAbtemp()).isEqualTo(UPDATED_ABTEMP);
        assertThat(testDog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDog.getHeartrate()).isEqualTo(UPDATED_HEARTRATE);
        assertThat(testDog.getMaxcoretemp()).isEqualTo(UPDATED_MAXCORETEMP);
        assertThat(testDog.getMaxrespiratoryrate()).isEqualTo(UPDATED_MAXRESPIRATORYRATE);
        assertThat(testDog.getMaxabtemp()).isEqualTo(UPDATED_MAXABTEMP);
        assertThat(testDog.getMaxheartrate()).isEqualTo(UPDATED_MAXHEARTRATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);
        int databaseSizeBeforeDelete = dogRepository.findAll().size();

        // Get the dog
        restDogMockMvc.perform(delete("/api/dogs/{id}", dog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dog.class);
        Dog dog1 = new Dog();
        dog1.setId(1L);
        Dog dog2 = new Dog();
        dog2.setId(dog1.getId());
        assertThat(dog1).isEqualTo(dog2);
        dog2.setId(2L);
        assertThat(dog1).isNotEqualTo(dog2);
        dog1.setId(null);
        assertThat(dog1).isNotEqualTo(dog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DogDTO.class);
        DogDTO dogDTO1 = new DogDTO();
        dogDTO1.setId(1L);
        DogDTO dogDTO2 = new DogDTO();
        assertThat(dogDTO1).isNotEqualTo(dogDTO2);
        dogDTO2.setId(dogDTO1.getId());
        assertThat(dogDTO1).isEqualTo(dogDTO2);
        dogDTO2.setId(2L);
        assertThat(dogDTO1).isNotEqualTo(dogDTO2);
        dogDTO1.setId(null);
        assertThat(dogDTO1).isNotEqualTo(dogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dogMapper.fromId(null)).isNull();
    }
}
