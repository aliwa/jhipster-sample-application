package com.aliwa.myapp.service;

import com.aliwa.myapp.domain.A;
import com.aliwa.myapp.repository.ARepository;
import com.aliwa.myapp.service.dto.ADTO;
import com.aliwa.myapp.service.mapper.AMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link A}.
 */
@Service
@Transactional
public class AService {

    private final Logger log = LoggerFactory.getLogger(AService.class);

    private final ARepository aRepository;

    private final AMapper aMapper;

    public AService(ARepository aRepository, AMapper aMapper) {
        this.aRepository = aRepository;
        this.aMapper = aMapper;
    }

    /**
     * Save a a.
     *
     * @param aDTO the entity to save.
     * @return the persisted entity.
     */
    public ADTO save(ADTO aDTO) {
        log.debug("Request to save A : {}", aDTO);
        A a = aMapper.toEntity(aDTO);
        a = aRepository.save(a);
        return aMapper.toDto(a);
    }

    /**
     * Partially update a a.
     *
     * @param aDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ADTO> partialUpdate(ADTO aDTO) {
        log.debug("Request to partially update A : {}", aDTO);

        return aRepository
            .findById(aDTO.getId())
            .map(existingA -> {
                aMapper.partialUpdate(existingA, aDTO);

                return existingA;
            })
            .map(aRepository::save)
            .map(aMapper::toDto);
    }

    /**
     * Get all the aS.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ADTO> findAll() {
        log.debug("Request to get all AS");
        return aRepository.findAll().stream().map(aMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one a by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADTO> findOne(Long id) {
        log.debug("Request to get A : {}", id);
        return aRepository.findById(id).map(aMapper::toDto);
    }

    /**
     * Delete the a by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete A : {}", id);
        aRepository.deleteById(id);
    }
}
