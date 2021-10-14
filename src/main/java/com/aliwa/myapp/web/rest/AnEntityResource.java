package com.aliwa.myapp.web.rest;

import com.aliwa.myapp.repository.AnEntityRepository;
import com.aliwa.myapp.service.AnEntityService;
import com.aliwa.myapp.service.dto.AnEntityDTO;
import com.aliwa.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aliwa.myapp.domain.AnEntity}.
 */
@RestController
@RequestMapping("/api")
public class AnEntityResource {

    private final Logger log = LoggerFactory.getLogger(AnEntityResource.class);

    private static final String ENTITY_NAME = "anEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnEntityService anEntityService;

    private final AnEntityRepository anEntityRepository;

    public AnEntityResource(AnEntityService anEntityService, AnEntityRepository anEntityRepository) {
        this.anEntityService = anEntityService;
        this.anEntityRepository = anEntityRepository;
    }

    /**
     * {@code POST  /an-entities} : Create a new anEntity.
     *
     * @param anEntityDTO the anEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anEntityDTO, or with status {@code 400 (Bad Request)} if the anEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/an-entities")
    public ResponseEntity<AnEntityDTO> createAnEntity(@Valid @RequestBody AnEntityDTO anEntityDTO) throws URISyntaxException {
        log.debug("REST request to save AnEntity : {}", anEntityDTO);
        if (anEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new anEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnEntityDTO result = anEntityService.save(anEntityDTO);
        return ResponseEntity
            .created(new URI("/api/an-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /an-entities/:id} : Updates an existing anEntity.
     *
     * @param id the id of the anEntityDTO to save.
     * @param anEntityDTO the anEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anEntityDTO,
     * or with status {@code 400 (Bad Request)} if the anEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/an-entities/{id}")
    public ResponseEntity<AnEntityDTO> updateAnEntity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnEntityDTO anEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnEntity : {}, {}", id, anEntityDTO);
        if (anEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnEntityDTO result = anEntityService.save(anEntityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /an-entities/:id} : Partial updates given fields of an existing anEntity, field will ignore if it is null
     *
     * @param id the id of the anEntityDTO to save.
     * @param anEntityDTO the anEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anEntityDTO,
     * or with status {@code 400 (Bad Request)} if the anEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/an-entities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnEntityDTO> partialUpdateAnEntity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnEntityDTO anEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnEntity partially : {}, {}", id, anEntityDTO);
        if (anEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnEntityDTO> result = anEntityService.partialUpdate(anEntityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anEntityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /an-entities} : get all the anEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anEntities in body.
     */
    @GetMapping("/an-entities")
    public List<AnEntityDTO> getAllAnEntities() {
        log.debug("REST request to get all AnEntities");
        return anEntityService.findAll();
    }

    /**
     * {@code GET  /an-entities/:id} : get the "id" anEntity.
     *
     * @param id the id of the anEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/an-entities/{id}")
    public ResponseEntity<AnEntityDTO> getAnEntity(@PathVariable Long id) {
        log.debug("REST request to get AnEntity : {}", id);
        Optional<AnEntityDTO> anEntityDTO = anEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anEntityDTO);
    }

    /**
     * {@code DELETE  /an-entities/:id} : delete the "id" anEntity.
     *
     * @param id the id of the anEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/an-entities/{id}")
    public ResponseEntity<Void> deleteAnEntity(@PathVariable Long id) {
        log.debug("REST request to delete AnEntity : {}", id);
        anEntityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
