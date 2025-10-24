package com.santander.cpe.porweb.infra.repository;

import com.santander.cpe.porweb.infra.repository.model.AppArsenalData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Database repository interface responsible for handling operations provided for AppArsenal.
 */
@Repository
public interface AppArsenalRepository extends JpaRepository<AppArsenalData, Long> {

  /**
   * Find by otherInfo
   * @param otherInfo {@link String}
   * @return {@link List<AppArsenalData>}
   */
  List<AppArsenalData> findByOtherInfoContaining(String otherInfo);

  /**
   * Find by other info.
   * @param otherInfo {@link String}
   * @return {@link AppArsenalData}
   */
  @Query("SELECT e FROM AppArsenalData e WHERE e.otherInfo LIKE %?1")
  AppArsenalData findByOtherInfoBeginningWith(String otherInfo);
}
