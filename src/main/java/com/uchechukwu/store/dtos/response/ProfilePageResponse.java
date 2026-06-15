package com.uchechukwu.store.dtos.response;

import java.util.List;

public record ProfilePageResponse(
        List<ProfileResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
