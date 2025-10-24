package com.santander.cpe.porweb.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.santander.cpe.porweb.app.service.AppArsenalService;
import com.santander.cpe.porweb.model.AppArsenalRequestDTO;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;
import com.santander.cpe.porweb.app.mapper.AppArsenalMapper;
import com.santander.cpe.porweb.domain.usecase.AppArsenalUseCase;
import com.santander.cpe.porweb.domain.entity.AppArsenal;

/**
 * Service Impl.
 */
@Service
@RequiredArgsConstructor
public class AppArsenalServiceImpl implements AppArsenalService {

    private final AppArsenalUseCase appArsenalUseCase;

    private final AppArsenalMapper mapper;

    /** {@inheritDoc}  */
    public Page<AppArsenalResponseDTO> getPageable(Pageable pageable) {
        Page<AppArsenal> pageAppArsenal = appArsenalUseCase.getPageable(pageable);

        return new PageImpl<>(
                    pageAppArsenal
                        .stream()
                        .map(mapper::toDTO)
                        .toList(),
                    pageable,
                    pageAppArsenal
                        .getNumberOfElements());
    }

    /** {@inheritDoc}  */
    public AppArsenalResponseDTO getById(Long id) {
        return mapper.toDTO(this.appArsenalUseCase.getById(id));
    }

    /** {@inheritDoc}  */
    public AppArsenalResponseDTO create(AppArsenalRequestDTO appArsenalRequest) {
        return mapper.toDTO(this.appArsenalUseCase.create(mapper.toModel(appArsenalRequest)));
    }

    /** {@inheritDoc}  */
    public AppArsenalResponseDTO update(Long id, AppArsenalRequestDTO appArsenalRequest) {
        return mapper.toDTO(this.appArsenalUseCase.update(id, mapper.toModel(appArsenalRequest)));
    }

    /** {@inheritDoc}  */
    public void delete(Long id) {
        this.appArsenalUseCase.delete(id);
    }
}