package com.santander.cpe.porweb.usecase;

import com.santander.cpe.porweb.ArsenalApplication;
import com.santander.cpe.porweb.domain.AppArsenalProvider;
import com.santander.cpe.porweb.domain.entity.AppArsenal;
import com.santander.cpe.porweb.domain.usecase.AppArsenalUseCase;
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
public class UseCaseMockTest {

  @MockBean private AppArsenalProvider appArsenalProvider;
  @Autowired private AppArsenalUseCase appArsenalUseCase;

  @Test
  void testCreateUseCase() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(appArsenalProvider.create(any())).thenReturn(mockArsDto);

    AppArsenal response = appArsenalUseCase.create(mockArsDto);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetUseCase() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(appArsenalProvider.getById(any())).thenReturn(mockArsDto);

    AppArsenal response = appArsenalUseCase.getById(123L);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testDeleteUseCase() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    appArsenalUseCase.delete(123L);

    Mockito.verify(appArsenalProvider, Mockito.times(1)).delete(any());
  }

  @Test
  void testPutUseCase() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    Mockito.when(appArsenalProvider.update(any(),any())).thenReturn(mockArsDto);

    AppArsenal response = appArsenalUseCase.update(123L,mockArsDto);
    Assertions.assertEquals(123L,response.getId(), "Are equals");
    Assertions.assertEquals("info2",response.getOtherInfo(), "Are equals");
  }

  @Test
  void testGetAllUseCase() {
    AppArsenal mockArsDto = new AppArsenal();
    mockArsDto.setId(123L);
    mockArsDto.setOtherInfo("info2");

    List<AppArsenal> list = new ArrayList<>();
    list.add(mockArsDto);
    Page<AppArsenal> page = new PageImpl<>(list, Pageable.ofSize(1),1);

    Mockito.when(appArsenalProvider.getPageable(Pageable.ofSize(1))).thenReturn(page);

    Page<AppArsenal> response = appArsenalUseCase.getPageable(Pageable.ofSize(1));
    Assertions.assertNotNull(response);
  }
}
