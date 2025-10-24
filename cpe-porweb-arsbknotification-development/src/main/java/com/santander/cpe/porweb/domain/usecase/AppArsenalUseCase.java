package com.santander.cpe.porweb.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import com.santander.cpe.porweb.domain.AppArsenalProvider;
import com.santander.cpe.porweb.domain.entity.AppArsenal;

import com.santander.ars.error.exceptions.BusinessException;
import com.santander.cpe.porweb.domain.handler.exception.AppArsenalErrorCode;

/**
 * AppArsenal UseCase.
 */
@Service
@RequiredArgsConstructor
public class AppArsenalUseCase {

    private final AppArsenalProvider appArsenalProvider;

    /**
     * Get pageable.
     * @param pageable {@link Pageable}
     * @return {@link Page<AppArsenal>}
     */
    public Page<AppArsenal> getPageable(Pageable pageable) {
        return appArsenalProvider.getPageable(pageable);
    }

    /**
     * Get by id.
     * @param id {@link Long}
     * @return {@link AppArsenal}
     */
    public AppArsenal getById(Long id) {
        return appArsenalProvider.getById(id);
    }

    /**
     * Create new.
     * @param appArsenal {@link AppArsenal}
     * @return {@link AppArsenal}
     */
    public AppArsenal create(AppArsenal appArsenal) {
        return appArsenalProvider.create(appArsenal);
    }

    /**
     * Update by id.
     * @param id {@link Long}
     * @param appArsenalUpdate {@link AppArsenal}
     * @return {@link AppArsenal}
     */
    public AppArsenal update(Long id, AppArsenal appArsenalUpdate) {
        return appArsenalProvider.update(id, appArsenalUpdate);
    }

    /**
     * Delete by id.
     * @param id {@link Long}
     */
    public void delete(Long id) {
        this.appArsenalProvider.delete(id);
    }
}