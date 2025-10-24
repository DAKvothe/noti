
package com.santander.cpe.porweb.infra.dataprovider;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.santander.cpe.porweb.domain.AppArsenalProvider;
import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.cpe.porweb.infra.repository.model.AppArsenalData;
import com.santander.cpe.porweb.infra.repository.AppArsenalRepository;
import com.santander.cpe.porweb.infra.dataprovider.mapper.AppArsenalRepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.santander.ars.error.exceptions.BusinessException;
import com.santander.cpe.porweb.infra.handler.exception.AppArsenalErrorCode;

import java.util.Optional;

/**
 * AppArsenal Provider Impl.
 */
@Component
@RequiredArgsConstructor
public class AppArsenalProviderImpl implements AppArsenalProvider {


    /** Repository used for AppArsenal operations. */
    private final AppArsenalRepository repository;

    /** Mapper used for AppArsenalData to AppArsenal operations. */
    private final AppArsenalRepositoryMapper mapper;

    @Override
    public Page<AppArsenal> getPageable(Pageable pageable) {
        return new PageImpl<>(
            repository
                .findAll(pageable)
                .stream()
                .map(mapper::toDomain)
                .toList(),
            pageable,
            repository
                .findAll(pageable)
                .getNumberOfElements());
    }

    @Override
    public AppArsenal getById(Long appArsenalId) {
        Optional<AppArsenalData> appArsenalData = this.repository.findById(appArsenalId);

        return mapper.toDomain(appArsenalData
                .orElseThrow(() -> new BusinessException(AppArsenalErrorCode.ERROR_APPARSENAL_NOT_FOUND)));
    }

    @Override
    public AppArsenal create(AppArsenal appArsenal) {
        return mapper.toDomain(repository.save(mapper.toDataRepository(appArsenal)));
    }

    @Override
    public AppArsenal update(Long id, AppArsenal appArsenalRequest) {
        AppArsenal appArsenal = getById(id);

        return mapper.toDomain(repository.save(
            AppArsenalData.builder()
                .id(appArsenal.getId())
                .otherInfo(appArsenalRequest.getOtherInfo())
                .build()));
    }

    @Override
    public void delete(Long appArsenalId) {
        repository.deleteById(appArsenalId);
    }
}