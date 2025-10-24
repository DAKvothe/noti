
package com.santander.cpe.porweb.domain;

import com.santander.cpe.porweb.domain.entity.AppArsenal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * AppArsenal Provider.
 */
public interface AppArsenalProvider {

    /**
     * Get pageable.
     * @param pageable {@link Pageable}
     * @return {@link Page<AppArsenal>}
     */
    Page<AppArsenal> getPageable(Pageable pageable);

    /**
     * Get by id.
     * @param appArsenalId {@link Long}
     * @return {@link AppArsenal}
     */
    AppArsenal getById(Long appArsenalId);

    /**
     * Create new.
     * @param appArsenal {@link AppArsenal}
     * @return {@link AppArsenal}
     */
    AppArsenal create(AppArsenal appArsenal);

    /**
     * Update by id.
     * @param id {@link Long}
     * @param appArsenal {@link AppArsenal}
     * @return {@link AppArsenal}
     */
    AppArsenal update(Long id, AppArsenal appArsenal);

    /**
     * Delete by id.
     * @param appArsenalId {@link Long}
     */
    void delete(Long appArsenalId);
}
