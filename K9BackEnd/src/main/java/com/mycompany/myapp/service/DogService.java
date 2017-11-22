package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dog.
 */
public interface DogService {

    /**
     * Save a dog.
     *
     * @param dogDTO the entity to save
     * @return the persisted entity
     */
    DogDTO save(DogDTO dogDTO);

    /**
     *  Get all the dogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DogDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" dog.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DogDTO findOne(Long id);

    /**
     *  Delete the "id" dog.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
