package com.aliwa.myapp.service.mapper;

import com.aliwa.myapp.domain.*;
import com.aliwa.myapp.service.dto.ADTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link A} and its DTO {@link ADTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AMapper extends EntityMapper<ADTO, A> {}
