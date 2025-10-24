
package com.santander.cpe.porweb.infra.dataprovider.mapper;

import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.cpe.porweb.infra.repository.model.AppArsenalData;
import org.mapstruct.Mapper;

/**
 * AppArsenal Repository Mapper.
 */
@Mapper(componentModel = "spring")
public interface AppArsenalRepositoryMapper {

  /**
   * To domain mapper.
   * @param appArsenalData {@link AppArsenalData}
   * @return {@link AppArsenal}
   */
  AppArsenal toDomain(AppArsenalData appArsenalData);

  /**
   * To data repository mapper.
   * @param appArsenal {@link AppArsenal}
   * @return {@link AppArsenalData}
   */
  AppArsenalData toDataRepository(AppArsenal appArsenal);
}