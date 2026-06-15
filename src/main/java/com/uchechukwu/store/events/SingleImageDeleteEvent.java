package com.uchechukwu.store.events;

import java.util.UUID;

public record SingleImageDeleteEvent(
        UUID profileId,
        String publicId,
        String resourceType
) {
}
