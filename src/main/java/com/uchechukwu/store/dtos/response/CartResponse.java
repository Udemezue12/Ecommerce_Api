package com.uchechukwu.store.dtos.response;


import java.time.LocalDateTime;
import java.util.UUID;

public record CartResponse(
        UUID id,
        LocalDateTime dateCreated

) {

}

