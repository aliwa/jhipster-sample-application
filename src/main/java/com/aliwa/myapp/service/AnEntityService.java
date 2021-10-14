package com.aliwa.myapp.service;

import com.aliwa.myapp.domain.AnEntity;
import com.aliwa.myapp.repository.AnEntityRepository;
import com.aliwa.myapp.service.dto.AnEntityDTO;
import com.aliwa.myapp.service.mapper.AnEntityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnEntity}.
 */
@Service
@Transactional
public class AnEntityService {

    private final Logger log = LoggerFactory.getLogger(AnEntityService.class);

    private final AnEntityRepository anEntityRepository;

    private final AnEntityMapper anEntityMapper;

    public AnEntityService(AnEntityRepository anEntityRepository, AnEntityMapper anEntityMapper) {
        this.anEntityRepository = anEntityRepository;
        this.anEntityMapper = anEntityMapper;
    }

    /**
     * Save a anEntity.
     *
     * @param anEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public AnEntityDTO save(AnEntityDTO anEntityDTO) {
        log.debug("Request to save AnEntity : {}", anEntityDTO);
        AnEntity anEntity = anEntityMapper.toEntity(anEntityDTO);
        anEntity = anEntityRepository.save(anEntity);
        return anEntityMapper.toDto(anEntity);
    }

    /**
     * Partially update a anEntity.
     *
     * @param anEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnEntityDTO> partialUpdate(AnEntityDTO anEntityDTO) {
        log.debug("Request to partially update AnEntity : {}", anEntityDTO);

        return anEntityRepository
            .findById(anEntityDTO.getId())
            .map(existingAnEntity -> {
                anEntityMapper.partialUpdate(existingAnEntity, anEntityDTO);

                return existingAnEntity;
            })
            .map(anEntityRepository::save)
            .map(anEntityMapper::toDto);
    }

    /**
     * Get all the anEntities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnEntityDTO> findAll() {
        log.debug("Request to get all AnEntities");
        return anEntityRepository.findAll().stream().map(anEntityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one anEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnEntityDTO> findOne(Long id) {
        log.debug("Request to get AnEntity : {}", id);
        return anEntityRepository.findById(id).map(anEntityMapper::toDto);
    }

    /**
     * Delete the anEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnEntity : {}", id);
        anEntityRepository.deleteById(id);
    }
}
