package com.uchechukwu.store.dtos.response;

import java.util.List;

public record PaymentTransactionsPageResponse(List<PaymentTransactionsResponse> content, int number, int size,
                                              long totalElements, int totalPages, boolean first, boolean last) {
}
