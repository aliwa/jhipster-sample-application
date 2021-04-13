package com.myapp.web.rest;

import com.myapp.domain.EntityTest;
import com.myapp.repository.EntityTestRepository;
import com.myapp.service.EntityTestService;
import com.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.myapp.domain.EntityTest}.
 */
@RestController
@RequestMapping("/api")
public class EntityTestResource {

    private final Logger log = LoggerFactory.getLogger(EntityTestResource.class);

    private static final String ENTITY_NAME = "entityTest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityTestService entityTestService;

    private final EntityTestRepository entityTestRepository;

    public EntityTestResource(EntityTestService entityTestService, EntityTestRepository entityTestRepository) {
        this.entityTestService = entityTestService;
        this.entityTestRepository = entityTestRepository;
    }

    /**
     * {@code POST  /entity-tests} : Create a new entityTest.
     *
     * @param entityTest the entityTest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityTest, or with status {@code 400 (Bad Request)} if the entityTest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-tests")
    public ResponseEntity<EntityTest> createEntityTest(@Valid @RequestBody EntityTest entityTest) throws URISyntaxException {
        log.debug("REST request to save EntityTest : {}", entityTest);
        if (entityTest.getId() != null) {
            throw new BadRequestAlertException("A new entityTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityTest result = entityTestService.save(entityTest);
        return ResponseEntity
            .created(new URI("/api/entity-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-tests/:id} : Updates an existing entityTest.
     *
     * @param id the id of the entityTest to save.
     * @param entityTest the entityTest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityTest,
     * or with status {@code 400 (Bad Request)} if the entityTest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityTest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-tests/{id}")
    public ResponseEntity<EntityTest> updateEntityTest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntityTest entityTest
    ) throws URISyntaxException {
        log.debug("REST request to update EntityTest : {}, {}", id, entityTest);
        if (entityTest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityTest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntityTest result = entityTestService.save(entityTest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityTest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entity-tests/:id} : Partial updates given fields of an existing entityTest, field will ignore if it is null
     *
     * @param id the id of the entityTest to save.
     * @param entityTest the entityTest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityTest,
     * or with status {@code 400 (Bad Request)} if the entityTest is not valid,
     * or with status {@code 404 (Not Found)} if the entityTest is not found,
     * or with status {@code 500 (Internal Server Error)} if the entityTest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entity-tests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EntityTest> partialUpdateEntityTest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntityTest entityTest
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntityTest partially : {}, {}", id, entityTest);
        if (entityTest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityTest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntityTest> result = entityTestService.partialUpdate(entityTest);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityTest.getId().toString())
        );
    }

    /**
     * {@code GET  /entity-tests} : get all the entityTests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityTests in body.
     */
    @GetMapping("/entity-tests")
    public List<EntityTest> getAllEntityTests() {
        log.debug("REST request to get all EntityTests");
        return entityTestService.findAll();
    }

    /**
     * {@code GET  /entity-tests/:id} : get the "id" entityTest.
     *
     * @param id the id of the entityTest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityTest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-tests/{id}")
    public ResponseEntity<EntityTest> getEntityTest(@PathVariable Long id) {
        log.debug("REST request to get EntityTest : {}", id);
        Optional<EntityTest> entityTest = entityTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityTest);
    }

    /**
     * {@code DELETE  /entity-tests/:id} : delete the "id" entityTest.
     *
     * @param id the id of the entityTest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-tests/{id}")
    public ResponseEntity<Void> deleteEntityTest(@PathVariable Long id) {
        log.debug("REST request to delete EntityTest : {}", id);
        entityTestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
