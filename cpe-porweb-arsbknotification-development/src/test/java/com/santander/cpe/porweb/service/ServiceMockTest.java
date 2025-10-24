package com.santander.cpe.porweb.service;

import com.santander.cpe.porweb.ArsenalApplication;
import com.santander.cpe.porweb.app.service.AppArsenalService;
import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.cpe.porweb.domain.usecase.AppArsenalUseCase;
import com.santander.cpe.porweb.model.AppArsenalRequestDTO;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;
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

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ArsenalApplication.class})
public class ServiceMockTest {

  @MockBean private AppArsenalUseCase useCase;
  @Autowired private AppArsenalService appArsenalService;

  @Test
  void testCreateService() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(useCase.create(any())).thenReturn(mockArsDto);

    AppArsenalResponseDTO response = appArsenalService.create(AppArsenalRequestDTO.builder().otherInfo("info2").build());
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetByIdService() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(useCase.getById(any())).thenReturn(mockArsDto);

    AppArsenalResponseDTO response = appArsenalService.getById(123L);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testDeleteService() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    appArsenalService.delete(123L);

    Mockito.verify(useCase, Mockito.times(1)).delete(any());
  }

  @Test
  void testUpdateService() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(useCase.update(any(),any())).thenReturn(mockArsDto);

    AppArsenalResponseDTO response = appArsenalService.update(123L,AppArsenalRequestDTO.builder().otherInfo("info2").build());
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetAllService() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    List<AppArsenal> list = new ArrayList<>();
    list.add(mockArsDto);
    Page<AppArsenal> page = new PageImpl<>(list, Pageable.ofSize(1),1);

    Mockito.when(useCase.getPageable(Pageable.ofSize(1))).thenReturn(page);

    Page<AppArsenalResponseDTO> response = appArsenalService.getPageable(Pageable.ofSize(1));
    Assertions.assertNotNull(response);
  }
}
