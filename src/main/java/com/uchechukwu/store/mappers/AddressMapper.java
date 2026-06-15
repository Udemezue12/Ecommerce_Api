package com.uchechukwu.store.mappers;

import com.uchechukwu.store.dtos.request.AddressRequest;
import com.uchechukwu.store.dtos.response.AddressResponse;
import com.uchechukwu.store.entities.Address;
import com.uchechukwu.store.entities.User;

public class AddressMapper {
    public static AddressResponse response(Address address) {
        var user = address.getUser();
        return new AddressResponse(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                address.getStreet(),
                address.getCity(),
                address.getLocalGovernment(),
                address.getZip(),
                address.getState()
        );
    }

    public static Address create(AddressRequest request, User user) {
        return Address.builder()
                .zip(request.zip())
                .state(request.state())
                .city(request.city())
                .isDefault(request.setDefault())
                .localGovernment(request.localGovernment())
                .street(request.street())
                .user(user)
                .build();

    }
}
