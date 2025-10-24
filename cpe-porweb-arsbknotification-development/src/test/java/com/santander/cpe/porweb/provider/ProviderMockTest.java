package com.santander.cpe.porweb.provider;

import com.santander.cpe.porweb.ArsenalApplication;
import com.santander.cpe.porweb.domain.AppArsenalProvider;
import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.ars.error.exceptions.BusinessException;
import com.santander.cpe.porweb.infra.repository.AppArsenalRepository;
import com.santander.cpe.porweb.infra.repository.model.AppArsenalData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.aot.DisabledInAotMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ArsenalApplication.class})
public class ProviderMockTest {

  @MockBean private AppArsenalRepository appArsenalRepository;
  @Autowired private AppArsenalProvider appArsenalProvider;

  @Test
  void testCreateProvider() {
    AppArsenal appArsenal = new AppArsenal();
    appArsenal.setId(123L);
    appArsenal.setOtherInfo("info2");

    AppArsenalData appArsenalData = AppArsenalData.builder().id(123L).otherInfo("info2").build();

    Mockito.when(appArsenalRepository.save(any())).thenReturn(appArsenalData);

    AppArsenal response = appArsenalProvider.create(appArsenal);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetByIdProvider() {
    AppArsenalData mockArsDto = AppArsenalData.builder().id(123L).otherInfo("info2").build();

    Optional<AppArsenalData> optional = Optional.of(mockArsDto);

    Mockito.when(appArsenalRepository.findById(123L)).thenReturn(optional);

    AppArsenal response = appArsenalProvider.getById(123L);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetByIdNotFoundProvider() {
    AppArsenalData mockArsDto = new AppArsenalData();

    Optional<AppArsenalData> optional = Optional.of(mockArsDto);

    Mockito.when(appArsenalRepository.findById(123L)).thenReturn(optional);

    assertThrows(BusinessException.class, () -> {
       appArsenalProvider.getById(1234L);
    });
  }

  @Test
  void testDeleteProvider() {
    AppArsenal appArsenal = new AppArsenal();
    appArsenal.setId(123L);
    appArsenal.setOtherInfo("info2");

    appArsenalProvider.delete(123L);

    Mockito.verify(appArsenalRepository, Mockito.times(1)).deleteById(123L);
  }

  @Test
  void testUpdateProvider() {
    AppArsenal appArsenal = new AppArsenal();
    appArsenal.setId(123L);
    appArsenal.setOtherInfo("info2");

    AppArsenalData appArsenalData = AppArsenalData.builder().id(123L).otherInfo("info2").build();

    Optional<AppArsenalData> optional = Optional.of(appArsenalData);

    Mockito.when(appArsenalRepository.findById(123L)).thenReturn(optional);
    Mockito.when(appArsenalRepository.save(any())).thenReturn(appArsenalData);

    AppArsenal response = appArsenalProvider.update(123L,appArsenal);

    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetAllProvider() {
    AppArsenal appArsenal = new AppArsenal();
    appArsenal.setId(123L);
    appArsenal.setOtherInfo("info2");

    List<AppArsenal> list = new ArrayList<>();
    list.add(appArsenal);
    Page<AppArsenal> page = new PageImpl<>(list, Pageable.ofSize(1),1);


    List<AppArsenalData> listReturn = new ArrayList<>();
    AppArsenalData appArsenalData = new AppArsenalData();
    listReturn.add(appArsenalData);

    Page<AppArsenalData> pageReturn = new PageImpl<>(listReturn, Pageable.ofSize(1),1);

    Mockito.when(appArsenalRepository.findAll(Pageable.ofSize(1))).thenReturn(pageReturn);

    Page<AppArsenal> responseDTO = appArsenalProvider.getPageable(Pageable.ofSize(1));
    Assertions.assertNotNull(responseDTO);
  }
}
