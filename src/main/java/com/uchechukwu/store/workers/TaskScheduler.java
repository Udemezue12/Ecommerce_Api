package com.uchechukwu.store.workers;

import com.uchechukwu.store.events.MultipleImagesDeleteEvent;
import com.uchechukwu.store.events.PaymentSuccessEvent;

import com.uchechukwu.store.events.SingleImageDeleteEvent;
import com.uchechukwu.store.tasks.CancelPendingOrders;
import com.uchechukwu.store.tasks.CloudinaryImageDeleteTasks;
import com.uchechukwu.store.tasks.DeleteBlacklistedTokenTask;
import com.uchechukwu.store.tasks.PaymentNotification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Configuration
@RequiredArgsConstructor
public class TaskScheduler {
    private final JobScheduler jobScheduler;
    private final DeleteBlacklistedTokenTask cleanupToken;
    private final PaymentNotification paymentNotification;
    private final CancelPendingOrders cancelPendingOrders;
    private final CloudinaryImageDeleteTasks deleteImages;

    @PostConstruct
    public void scheduleJobs() {
        jobScheduler.scheduleRecurrently(
                "revoked-token-cleanup",
                Cron.weekly(),
                // "0 */2 * * * *",
                cleanupToken::cleanupExpiredTokens);
    }

    @PostConstruct
    public void cancelOrders() {
        jobScheduler.scheduleRecurrently(
                "cancel-orders-that-are-pending",
                Cron.daily(),
                cancelPendingOrders::deletePendingOrder
        );

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(
            PaymentSuccessEvent event) {
        var orderId = event.orderId();
        var name = event.name();
        var email = event.email();
        var transactionId = event.transactionId();
        var phoneNumber = event.phoneNumber();
        jobScheduler.enqueue(
                () -> {

                    paymentNotification.sendPaymentSuccessNotificationEmail(
                            orderId,
                            name,
                            email,
                            transactionId);
                });
        jobScheduler.enqueue(
                () -> paymentNotification.sendPaymentSuccessNotificationSms(
                        phoneNumber,
                        name,
                        orderId

                ));
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleMultipleImageDeletion(MultipleImagesDeleteEvent event) {
        var publicIds = event.publicIds();
        var resourceTypes = event.resourceTypes();

        jobScheduler.enqueue(
                () -> deleteImages.deleteImages(
                        publicIds, resourceTypes
                )
        );
    }

    @TransactionalEventListener(
            phase = TransactionPhase.BEFORE_COMMIT
    )
    public void handleImageDeletion(SingleImageDeleteEvent event) {
        var publicId = event.publicId();
        var resourceType = event.resourceType();

        jobScheduler.enqueue(
                () -> deleteImages.deleteImage(
                        publicId, resourceType
                )
        );
    }

}
