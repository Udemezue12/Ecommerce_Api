package com.uchechukwu.store.tasks;


import com.uchechukwu.store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelPendingOrders {
    private final OrderService orderService;

    @Job(name = "Cancel Pending Orders", retries = 3)
    public void deletePendingOrder() {
        orderService.cancelOrders();
    }
}
