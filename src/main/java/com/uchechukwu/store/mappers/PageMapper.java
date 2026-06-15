package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.PageResponse;
import com.uchechukwu.store.dtos.response.DeliveryManyResponse;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageMapper {
     public static PageResponse toResponse(Page<DeliveryManyResponse> page) {
        return new PageResponse(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
        );
    }
}
