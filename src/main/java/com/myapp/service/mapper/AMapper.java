package com.myapp.service.mapper;

import com.myapp.domain.*;
import com.myapp.service.dto.ADTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link A} and its DTO {@link ADTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AMapper extends EntityMapper<ADTO, A> {}
