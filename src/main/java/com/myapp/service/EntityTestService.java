package com.myapp.service;

import com.myapp.domain.EntityTest;
import com.myapp.repository.EntityTestRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EntityTest}.
 */
@Service
@Transactional
public class EntityTestService {

    private final Logger log = LoggerFactory.getLogger(EntityTestService.class);

    private final EntityTestRepository entityTestRepository;

    public EntityTestService(EntityTestRepository entityTestRepository) {
        this.entityTestRepository = entityTestRepository;
    }

    /**
     * Save a entityTest.
     *
     * @param entityTest the entity to save.
     * @return the persisted entity.
     */
    public EntityTest save(EntityTest entityTest) {
        log.debug("Request to save EntityTest : {}", entityTest);
        return entityTestRepository.save(entityTest);
    }

    /**
     * Partially update a entityTest.
     *
     * @param entityTest the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntityTest> partialUpdate(EntityTest entityTest) {
        log.debug("Request to partially update EntityTest : {}", entityTest);

        return entityTestRepository
            .findById(entityTest.getId())
            .map(
                existingEntityTest -> {
                    if (entityTest.getaString() != null) {
                        existingEntityTest.setaString(entityTest.getaString());
                    }
                    if (entityTest.getaInteger() != null) {
                        existingEntityTest.setaInteger(entityTest.getaInteger());
                    }
                    if (entityTest.getaLong() != null) {
                        existingEntityTest.setaLong(entityTest.getaLong());
                    }
                    if (entityTest.getaBigDecimal() != null) {
                        existingEntityTest.setaBigDecimal(entityTest.getaBigDecimal());
                    }
                    if (entityTest.getaFloat() != null) {
                        existingEntityTest.setaFloat(entityTest.getaFloat());
                    }
                    if (entityTest.getaDouble() != null) {
                        existingEntityTest.setaDouble(entityTest.getaDouble());
                    }
                    if (entityTest.getaBoolean() != null) {
                        existingEntityTest.setaBoolean(entityTest.getaBoolean());
                    }
                    if (entityTest.getaLocalDate() != null) {
                        existingEntityTest.setaLocalDate(entityTest.getaLocalDate());
                    }
                    if (entityTest.getaZonedDateTime() != null) {
                        existingEntityTest.setaZonedDateTime(entityTest.getaZonedDateTime());
                    }
                    if (entityTest.getaInstant() != null) {
                        existingEntityTest.setaInstant(entityTest.getaInstant());
                    }
                    if (entityTest.getaDuration() != null) {
                        existingEntityTest.setaDuration(entityTest.getaDuration());
                    }
                    if (entityTest.getaUUID() != null) {
                        existingEntityTest.setaUUID(entityTest.getaUUID());
                    }
                    if (entityTest.getaBlob() != null) {
                        existingEntityTest.setaBlob(entityTest.getaBlob());
                    }
                    if (entityTest.getaBlobContentType() != null) {
                        existingEntityTest.setaBlobContentType(entityTest.getaBlobContentType());
                    }
                    if (entityTest.getaAnyBlob() != null) {
                        existingEntityTest.setaAnyBlob(entityTest.getaAnyBlob());
                    }
                    if (entityTest.getaAnyBlobContentType() != null) {
                        existingEntityTest.setaAnyBlobContentType(entityTest.getaAnyBlobContentType());
                    }
                    if (entityTest.getaImageBlob() != null) {
                        existingEntityTest.setaImageBlob(entityTest.getaImageBlob());
                    }
                    if (entityTest.getaImageBlobContentType() != null) {
                        existingEntityTest.setaImageBlobContentType(entityTest.getaImageBlobContentType());
                    }
                    if (entityTest.getaTextBlob() != null) {
                        existingEntityTest.setaTextBlob(entityTest.getaTextBlob());
                    }

                    return existingEntityTest;
                }
            )
            .map(entityTestRepository::save);
    }

    /**
     * Get all the entityTests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EntityTest> findAll() {
        log.debug("Request to get all EntityTests");
        return entityTestRepository.findAll();
    }

    /**
     * Get one entityTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntityTest> findOne(Long id) {
        log.debug("Request to get EntityTest : {}", id);
        return entityTestRepository.findById(id);
    }

    /**
     * Delete the entityTest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EntityTest : {}", id);
        entityTestRepository.deleteById(id);
    }
}
