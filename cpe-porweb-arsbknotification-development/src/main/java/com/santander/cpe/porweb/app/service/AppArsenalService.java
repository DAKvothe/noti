package com.santander.cpe.porweb.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.santander.cpe.porweb.model.AppArsenalRequestDTO;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;

/**
 * AppArsenalService.
 */
public interface  AppArsenalService {

    /**
     * Get pages.
     * @param pageable {@link Pageable}
     * @return {@link Page<AppArsenalResponseDTO>}
     */
    Page<AppArsenalResponseDTO> getPageable(Pageable pageable);

    /**
     * Get by id.
     * @param id {@link Long}
     * @return {@link AppArsenalResponseDTO}
     */
    AppArsenalResponseDTO getById(Long id);

    /**
     * Create new.
     * @param appArsenalRequest {@link AppArsenalRequestDTO}
     * @return {@link AppArsenalResponseDTO}
     */
    AppArsenalResponseDTO create(AppArsenalRequestDTO appArsenalRequest);

    /**
     * Update request.
     * @param id {@link Long}
     * @param appArsenal {@link AppArsenalRequestDTO}
     * @return {@link AppArsenalResponseDTO}
     */
    AppArsenalResponseDTO update(Long id, AppArsenalRequestDTO appArsenalRequest);

    /**
     * Delete by id.
     * @param appArsenalId {@link Long}
     */
    void delete(Long appArsenalId);
}