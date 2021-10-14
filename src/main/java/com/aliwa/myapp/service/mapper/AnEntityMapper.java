package com.aliwa.myapp.service.mapper;

import com.aliwa.myapp.domain.*;
import com.aliwa.myapp.service.dto.AnEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnEntity} and its DTO {@link AnEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnEntityMapper extends EntityMapper<AnEntityDTO, AnEntity> {}
