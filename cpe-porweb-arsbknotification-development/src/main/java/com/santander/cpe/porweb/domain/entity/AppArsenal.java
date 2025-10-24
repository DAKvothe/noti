package com.santander.cpe.porweb.domain.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * AppArsenal.
 */
@Data
public class AppArsenal {

    private long id;

    private String otherInfo;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}