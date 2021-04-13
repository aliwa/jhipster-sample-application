package com.myapp.service;

import com.myapp.domain.EntityTest;
import com.myapp.repository.EntityTestRepository;
import com.myapp.service.dto.EntityTestDTO;
import com.myapp.service.mapper.EntityTestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final EntityTestMapper entityTestMapper;

    public EntityTestService(EntityTestRepository entityTestRepository, EntityTestMapper entityTestMapper) {
        this.entityTestRepository = entityTestRepository;
        this.entityTestMapper = entityTestMapper;
    }

    /**
     * Save a entityTest.
     *
     * @param entityTestDTO the entity to save.
     * @return the persisted entity.
     */
    public EntityTestDTO save(EntityTestDTO entityTestDTO) {
        log.debug("Request to save EntityTest : {}", entityTestDTO);
        EntityTest entityTest = entityTestMapper.toEntity(entityTestDTO);
        entityTest = entityTestRepository.save(entityTest);
        return entityTestMapper.toDto(entityTest);
    }

    /**
     * Partially update a entityTest.
     *
     * @param entityTestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntityTestDTO> partialUpdate(EntityTestDTO entityTestDTO) {
        log.debug("Request to partially update EntityTest : {}", entityTestDTO);

        return entityTestRepository
            .findById(entityTestDTO.getId())
            .map(
                existingEntityTest -> {
                    entityTestMapper.partialUpdate(existingEntityTest, entityTestDTO);
                    return existingEntityTest;
                }
            )
            .map(entityTestRepository::save)
            .map(entityTestMapper::toDto);
    }

    /**
     * Get all the entityTests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EntityTestDTO> findAll() {
        log.debug("Request to get all EntityTests");
        return entityTestRepository.findAll().stream().map(entityTestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entityTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntityTestDTO> findOne(Long id) {
        log.debug("Request to get EntityTest : {}", id);
        return entityTestRepository.findById(id).map(entityTestMapper::toDto);
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
