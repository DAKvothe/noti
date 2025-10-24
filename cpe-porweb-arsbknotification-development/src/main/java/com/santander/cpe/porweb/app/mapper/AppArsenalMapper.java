package com.santander.cpe.porweb.app.mapper;

import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.cpe.porweb.model.AppArsenalRequestDTO;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;
import org.mapstruct.Mapper;

/**
 * Default mapper {@link Mapper}
 */
@Mapper(componentModel = "spring")
public interface AppArsenalMapper {

	/**
	 * To DTO Mapper
	 * @param appArsenal {@link AppArsenal}
	 * @return {@link AppArsenalResponseDTO}
	 */
	AppArsenalResponseDTO toDTO(AppArsenal appArsenal);

	/**
	 * To Model mapper.
	 * @param appArsenalRequestDTO {@link AppArsenalRequestDTO}
	 * @return {@link AppArsenal}
	 */
	AppArsenal toModel(AppArsenalRequestDTO appArsenalRequestDTO);
}