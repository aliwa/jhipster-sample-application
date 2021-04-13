package com.myapp.service.mapper;

import com.myapp.domain.*;
import com.myapp.service.dto.EntityTestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EntityTest} and its DTO {@link EntityTestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntityTestMapper extends EntityMapper<EntityTestDTO, EntityTest> {}
