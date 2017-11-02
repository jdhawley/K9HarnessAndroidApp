package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DogService;
import com.mycompany.myapp.domain.Dog;
import com.mycompany.myapp.repository.DogRepository;
import com.mycompany.myapp.service.dto.DogDTO;
import com.mycompany.myapp.service.mapper.DogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Dog.
 */
@Service
@Transactional
public class DogServiceImpl implements DogService{

    private final Logger log = LoggerFactory.getLogger(DogServiceImpl.class);

    private final DogRepository dogRepository;

    private final DogMapper dogMapper;

    public DogServiceImpl(DogRepository dogRepository, DogMapper dogMapper) {
        this.dogRepository = dogRepository;
        this.dogMapper = dogMapper;
    }

    /**
     * Save a dog.
     *
     * @param dogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DogDTO save(DogDTO dogDTO) {
        log.debug("Request to save Dog : {}", dogDTO);
        Dog dog = dogMapper.toEntity(dogDTO);
        dog = dogRepository.save(dog);
        return dogMapper.toDto(dog);
    }

    /**
     *  Get all the dogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dogs");
        return dogRepository.findAll(pageable)
            .map(dogMapper::toDto);
    }

    /**
     *  Get one dog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DogDTO findOne(Long id) {
        log.debug("Request to get Dog : {}", id);
        Dog dog = dogRepository.findOne(id);
        return dogMapper.toDto(dog);
    }

    /**
     *  Delete the  dog by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dog : {}", id);
        dogRepository.delete(id);
    }
}
