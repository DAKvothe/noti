package com.santander.cpe.porweb.resource;

import com.santander.cpe.porweb.ArsenalApplication;
import com.santander.cpe.porweb.app.service.AppArsenalService;
import com.santander.ars.error.util.MessageHelper;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.aot.DisabledInAotMode;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ArsenalApplication.class})
public class ResourceMockTest {

  @Autowired protected TestRestTemplate testRestTemplate;
  @MockBean private MessageHelper messageHelper;
  @MockBean private AppArsenalService appArsenalService;

  private final static String TEST_URI = "/api/v1/apparsenal";

  @Test
  void testPostResource() {
    AppArsenalResponseDTO mockArsDto = AppArsenalResponseDTO.builder().id(123L).otherInfo("info2").build();

    Mockito.when(appArsenalService.create(any())).thenReturn(mockArsDto);
    ResponseEntity<AppArsenalResponseDTO> responseEntity =
            testRestTemplate.postForEntity(TEST_URI, mockArsDto, AppArsenalResponseDTO.class);

    messageHelper.resolve("teste", "teste");

    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }

  @Test
  void testGetResource() {
    AppArsenalResponseDTO mockArsDto = AppArsenalResponseDTO.builder().id(123L).otherInfo("info1").build();

    Mockito.when(appArsenalService.getById(any())).thenReturn(mockArsDto);

    ResponseEntity<AppArsenalResponseDTO> responseEntity = testRestTemplate.exchange(TEST_URI + "/123", HttpMethod.GET, null, AppArsenalResponseDTO.class);

    messageHelper.resolve("teste", "teste");

    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }

  @Test
  void testDeleteResource() {

    ResponseEntity<AppArsenalResponseDTO> responseEntity = testRestTemplate.exchange(TEST_URI + "/123", HttpMethod.DELETE, null, AppArsenalResponseDTO.class);

    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }

  @Test
  void testPutResource() {
    AppArsenalResponseDTO mockArsDto = AppArsenalResponseDTO.builder().id(123L).otherInfo("info5").build();

    testRestTemplate.put(TEST_URI + "/123",mockArsDto, AppArsenalResponseDTO.class);

    Mockito.verify(appArsenalService, Mockito.times(1)).update(any(),any());
  }
  @Test
  void testGetAllResource() {
    AppArsenalResponseDTO mockArsDto = AppArsenalResponseDTO.builder().id(123L).otherInfo("info5").build();

    List<AppArsenalResponseDTO> list = new ArrayList<>();
    list.add(mockArsDto);
    Page<AppArsenalResponseDTO> page = new PageImpl<>(list,Pageable.ofSize(1),1);

    Mockito.when(appArsenalService.getPageable(any())).thenReturn(page);

    ResponseEntity<List<AppArsenalResponseDTO>> responseEntity = testRestTemplate.exchange(TEST_URI , HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
  }
}
