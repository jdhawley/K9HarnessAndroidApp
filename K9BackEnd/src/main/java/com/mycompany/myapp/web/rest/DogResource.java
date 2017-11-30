package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.DogService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.DogDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dog.
 */
@RestController
@RequestMapping("/api")
public class DogResource {

    private final Logger log = LoggerFactory.getLogger(DogResource.class);

    private static final String ENTITY_NAME = "dog";

    private final DogService dogService;

    public DogResource(DogService dogService) {
        this.dogService = dogService;
    }

    /**
     * POST  /dogs : Create a new dog.
     *
     * @param dogDTO the dogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dogDTO, or with status 400 (Bad Request) if the dog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dogs")
    @Timed
    public ResponseEntity<DogDTO> createDog(@RequestBody DogDTO dogDTO) throws URISyntaxException {
        log.debug("REST request to save Dog : {}", dogDTO);
        if (dogDTO.getId() != null) {
            throw new BadRequestAlertException("A new dog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DogDTO result = dogService.save(dogDTO);
        return ResponseEntity.created(new URI("/api/dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dogs : Updates an existing dog.
     *
     * @param dogDTO the dogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dogDTO,
     * or with status 400 (Bad Request) if the dogDTO is not valid,
     * or with status 500 (Internal Server Error) if the dogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dogs")
    @Timed
    public ResponseEntity<DogDTO> updateDog(@RequestBody DogDTO dogDTO) throws URISyntaxException {
        log.debug("REST request to update Dog : {}", dogDTO);
        if (dogDTO.getId() == null) {
            return createDog(dogDTO);
        }
        DogDTO result = dogService.save(dogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dogs : get all the dogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dogs in body
     */
    @GetMapping("/dogs")
    @Timed
    public ResponseEntity<List<DogDTO>> getAllDogs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Dogs");
        Page<DogDTO> page = dogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dogs/:id : get the "id" dog.
     *
     * @param id the id of the dogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dogs/{id}")
    @Timed
    public ResponseEntity<DogDTO> getDog(@PathVariable Long id) {
        log.debug("REST request to get Dog : {}", id);
        DogDTO dogDTO = dogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dogDTO));
    }

    /**
     * DELETE  /dogs/:id : delete the "id" dog.
     *
     * @param id the id of the dogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        log.debug("REST request to delete Dog : {}", id);
        dogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/dogs/name/{name}")
    @Timed
    public ResponseEntity<DogDTO> getDogByName(@PathVariable String name) {
        log.debug("REST request to get Dog : {}", name);
        DogDTO dogDTO = dogService.findOneByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dogDTO));
    }
}
