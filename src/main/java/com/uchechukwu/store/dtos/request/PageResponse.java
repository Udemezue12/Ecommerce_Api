package com.uchechukwu.store.dtos.request;

import java.util.List;

import com.uchechukwu.store.dtos.response.DeliveryManyResponse;

public record PageResponse(
                List<DeliveryManyResponse> content,
                int page,
                int size,
                long totalElements,
                int totalPages,
                boolean first,
                boolean last) {
}
