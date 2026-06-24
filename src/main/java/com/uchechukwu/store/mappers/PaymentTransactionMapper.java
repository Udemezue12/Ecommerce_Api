package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.response.PaymentTransactionResponse;
import com.uchechukwu.store.dtos.response.PaymentTransactionsResponse;
import com.uchechukwu.store.entities.PaymentTransaction;

import java.util.Collections;
import java.util.stream.Collectors;

public class PaymentTransactionMapper {
    public static PaymentTransactionsResponse manyResponseDto(PaymentTransaction transaction) {
        return new PaymentTransactionsResponse(
                transaction.getId(),
                transaction.getGeneratedReference(),
                transaction.getPaymentProviderTransactionId(),
                transaction.getCurrency(),
                transaction.getPaymentChannel(),
                transaction.getPaymentMethod(),
                transaction.getStatus(),
                transaction.getAmount(),
                transaction.getOrder().getId(),
                transaction.getOrderProcessed(),
                transaction.getUser().getName(),
                transaction.getPaidAt(),
                transaction.getCreatedAt()
        );
    }

    public static PaymentTransactionResponse toResponseDto(
            PaymentTransaction transaction) {

        if (transaction == null) {
            return null;
        }

        PaymentTransactionResponse.OrderDto orderDto = null;

        if (transaction.getOrder() != null) {

            var itemsDto =
                    transaction.getOrder().getItems() != null
                            ? transaction.getOrder()
                            .getItems()
                            .stream()
                            .map(item ->
                                 new PaymentTransactionResponse.OrderItemDto(
                                         item.getId(),
                                         item.getProduct().getId(),
                                         item.getProduct().getName(),
                                         item.getQuantity(),
                                         item.getUnitPrice()))
                            .collect(Collectors.toList())
                            : Collections.<PaymentTransactionResponse.OrderItemDto>emptyList();

            orderDto = new PaymentTransactionResponse.OrderDto(
                    transaction.getOrder().getId(),
                    itemsDto
            );
        }

        return new PaymentTransactionResponse(
                transaction.getId(),
                transaction.getGeneratedReference(),
                transaction.getPaymentProviderTransactionId(),
                transaction.getCurrency(),
                transaction.getPaymentChannel(),
                transaction.getPaymentMethod(),
                transaction.getStatus(),
                transaction.getAmount(),

                transaction.getOrderProcessed(),
                transaction.getUser() != null
                        ? transaction.getUser().getId()
                        : null,
                orderDto,
                transaction.getPaidAt(),
                transaction.getCreatedAt()
        );
    }
}
