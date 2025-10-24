package com.santander.cpe.porweb.app.resource;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.santander.cpe.porweb.api.AppArsenalApiDelegate;
import com.santander.cpe.porweb.model.AppArsenalRequestDTO;
import com.santander.cpe.porweb.model.AppArsenalResponseDTO;
import com.santander.cpe.porweb.app.service.AppArsenalService;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

/** Resource responsible for handling AppArsenal operations. */

@Slf4j
@Component
public class AppArsenalResource implements AppArsenalApiDelegate {

  /** Service. */
  @Autowired
  private AppArsenalService appArsenalService;

  /**
   * Method responsible for retrieving all AppArsenal available.
   *
   * @param page index.
   * @param size of the page to be returned.
   * @return ResponseEntity containing a Page of AppArsenalResponseDTO objects.
   */
  @Override
  public CompletableFuture<ResponseEntity<List<AppArsenalResponseDTO>>> getPageable(
        Integer pagina, Integer size, final Pageable page) {
        Page<AppArsenalResponseDTO> pg1 = appArsenalService.getPageable(page);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok().body(pg1.toList()));
  }
  /**
   * Method responsible for retriving a AppArsenal by ID.
   *
   * @param id that is to be found.
   * @return ResponseEntity containing the equivalent AppArsenalResponseDTO object.
   */
  @Override
  public CompletableFuture<ResponseEntity<AppArsenalResponseDTO>> getById(Long id) {
        AppArsenalResponseDTO appArsenalResponseDTO = appArsenalService.getById(id);

        AppArsenalResponseDTO sanitizedAppArsenalResponseDTO = new AppArsenalResponseDTO();

        sanitizedAppArsenalResponseDTO.setId(appArsenalResponseDTO.getId());
        sanitizedAppArsenalResponseDTO.setOtherInfo(
              AppArsenalResource.sanitizeString(appArsenalResponseDTO.getOtherInfo()));

        log.info("Searching for AppArsenal with ID {}", id);

        return CompletableFuture.supplyAsync(
              () -> ResponseEntity.ok().body(sanitizedAppArsenalResponseDTO));
  }

  /**
   * Method responsible for creating a AppArsenal.
   *
   * @param body information that is to be creadted.
   * @return RespondeEntity containing the URI and equivalent AppArsenalResponseDTO object.
   */
  @Override
  public CompletableFuture<ResponseEntity<AppArsenalResponseDTO>> create(AppArsenalRequestDTO body) {
        AppArsenalResponseDTO saveAppArsenal = appArsenalService.create(body);
        URI locationResource =
        ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(saveAppArsenal.getId())
          .toUri();
        log.info("Successfully created AppArsenal with ID {}", saveAppArsenal.getId());
        return CompletableFuture.supplyAsync(() -> ResponseEntity.created(locationResource).body(saveAppArsenal));
  }

  /**
   * Method responsible for deleting a AppArsenal.
   *
   * @param id that is to be deleted.
   * @return ResponseEntity with no content.
   */
  @Override
  public CompletableFuture<ResponseEntity<Void>> delete(Long id) {
        appArsenalService.delete(id);
        log.info("Successfully deleted AppArsenal with ID {}", id);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.noContent().build());
  }

  /**
   * Method responsible for updating a AppArsenal.
   *
   * @param id that is to be updated.
   * @param body information that is to be updated.
   * @return ResponseEntity containing the updated AppArsenalResponseDTO object.
   */
  @Override
  public CompletableFuture<ResponseEntity<AppArsenalResponseDTO>> update(Long id, AppArsenalRequestDTO body) {
        AppArsenalResponseDTO appArsenalUpdate = appArsenalService.update(id, body);
        log.info("Successfully updated AppArsenal with ID {}", id);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(appArsenalUpdate));
  }

  public static String sanitizeString(String data) {
    String sanitizedString = Jsoup.clean(data, Safelist.basic());
    return sanitizedString;
  }
}
