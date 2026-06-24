package com.uchechukwu.store.service;


import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.api_builder_response.ApiResponseBuilder;
import com.uchechukwu.store.core.GetCurrentUser;
import com.uchechukwu.store.customCache.CustomCacheEvict;
import com.uchechukwu.store.customCache.CustomCacheable;
import com.uchechukwu.store.dtos.request.AddressRequest;
import com.uchechukwu.store.dtos.request.UpdateAddressRequest;
import com.uchechukwu.store.dtos.response.AddressPageResponse;
import com.uchechukwu.store.dtos.response.AddressResponse;
import com.uchechukwu.store.entities.Address;
import com.uchechukwu.store.exceptions.BadRequestException;
import com.uchechukwu.store.exceptions.ResourceNotFoundException;
import com.uchechukwu.store.mappers.AddressMapper;
import com.uchechukwu.store.repositories.AddressRepository;
import com.uchechukwu.store.validators.ValidatedSortedData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepo;
    private final GetCurrentUser getCurrentUser;
    private final ValidatedSortedData validatedSortedData;


    @Transactional(readOnly = true)
    @CustomCacheable(value = "single-address", key = "#addressId")
    public AddressResponse getAddressForUser(UUID addressId) {
        var user = getCurrentUser.getCurrentUser();
        var address = addressRepo.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found or unauthorized access."));
        return AddressMapper.response(address);
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "user-addresses", key = "@getCurrentUser.getCurrentUser().id")
    public List<AddressResponse> getAllAddressesForUser() {
        var user = getCurrentUser.getCurrentUser();
        return addressRepo.findByUserId(user.getId()).stream()
                .map(AddressMapper::response)
                .toList();
    }

    @Transactional(readOnly = true)
    @CustomCacheable(value = "admin-address", key = "#addressId")
    public AddressResponse adminGetSingleAddress(UUID addressId) {
        var address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address record with ID " + addressId + " does not exist."));
        return AddressMapper.response(address);
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "admin-addresses", key = "'all-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    public AddressPageResponse adminGetAllAddresses(String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        var result = addressRepo.findAll(pageable)
                .map(AddressMapper::response);
        return new AddressPageResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isFirst(),
                result.isLast()
        );
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "admin-addresses", key = "'user-' + #userId + '-' + #sort + '-' + #sortingValue1 + '-' + #page + '-' + #size")
    public Page<AddressResponse> adminGetAddressesByUserId(UUID userId, String sort, String sortingValue1, int page, int size) {
        var pageable = validatedSortedData.getValidatedPageableData(sort, sortingValue1, page, size);
        return addressRepo.findByUserId(userId, pageable)
                .map(AddressMapper::response);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"admin-addresses", "user-addresses", "admin-addresses", "single-address"})
    public AddressResponse createAddress(AddressRequest request) {

        var user = getCurrentUser.getCurrentUser();

        var address = AddressMapper.create(request, user);


        if (Boolean.TRUE.equals(request.setDefault())) {
            addressRepo.clearDefaultByUserId(user.getId());
            address.setDefault(true);
        }


        if (addressRepo.countByUserId(user.getId()) == 0) {
            address.setDefault(true);
        }

        var savedAddress = addressRepo.save(address);

        return AddressMapper.response(savedAddress);
    }

    @Transactional
    @CustomCacheEvict(cacheNames = {"admin-addresses", "user-addresses", "admin-addresses", "single-address"})
    public AddressResponse updateAddress(UUID addressId, UpdateAddressRequest request) {

        var user = getCurrentUser.getCurrentUser();


        var address = addressRepo.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found or you do not have permission to modify it."));

        if (request.isDefault()) {

            addressRepo.findByUserId(user.getId()).stream()
                    .filter(Address::isDefault)
                    .forEach(oldDefault -> oldDefault.setDefault(false));

            address.setDefault(true);
        }
        if (request.street() != null) {
            address.setStreet(request.street());
        }
        if (request.city() != null) {
            address.setCity(request.city());
        }
        if (request.state() != null) {
            address.setState(request.state());
        }
        if (request.localGovernment() != null) {
            address.setLocalGovernment(request.localGovernment());
        }
        if (request.zip() != null) {
            address.setZip(request.zip());
        }


        var updatedAddress = addressRepo.save(address);
        return AddressMapper.response(updatedAddress);
    }

    @Transactional

    @CustomCacheEvict(cacheNames = {"admin-addresses", "user-addresses", "admin-addresses", "single-address"})

    public ResponseEntity<ApiResponse<Void>> deleteAddress(UUID addressId) {

        var user = getCurrentUser.getCurrentUser();

        var address = addressRepo.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Address not found or you do not have permission to delete it."));


        var addressCount = addressRepo.countByUserId(user.getId());
        if (addressCount <= 1) {
            throw new BadRequestException("You must keep at least one shipping address on file.");
        }


        addressRepo.delete(address);


        return ApiResponseBuilder.deletedResponse("Address successfully removed from your profile");
    }


    @Transactional
    @CustomCacheEvict(cacheNames = {"admin-addresses", "user-addresses", "admin-addresses", "single-address"})
    public AddressResponse setDefaultAddress(UUID addressId) {
        var user = getCurrentUser.getCurrentUser();


        var targetAddress = addressRepo.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found or unauthorized access."));


        if (targetAddress.isDefault()) {
            return AddressMapper.response(targetAddress);
        }


        addressRepo.findByUserIdAndIsDefaultTrue(user.getId())
                .ifPresent(oldDefault -> {
                    oldDefault.setDefault(false);
                    addressRepo.save(oldDefault);
                });


        targetAddress.setDefault(true);
        var updatedAddress = addressRepo.save(targetAddress);

        return AddressMapper.response(updatedAddress);
    }


    @Transactional(readOnly = true)
    @CustomCacheable(value = "user-addresses", key = "'default-' + @getCurrentUser.getCurrentUser().id")
    public AddressResponse getDefaultAddress() {
        var user = getCurrentUser.getCurrentUser();

        return addressRepo.findByUserIdAndIsDefaultTrue(user.getId())
                .map(AddressMapper::response)
                .orElseGet(() -> {

                    return addressRepo.findByUserId(user.getId()).stream()
                            .findFirst()
                            .map(AddressMapper::response)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "No delivery address found. Please add a shipping destination to complete your order."));
                });
    }

    @Transactional(readOnly = true)
    public String getAddress(UUID userId) {
        Address address = addressRepo.findByUserIdAndIsDefaultTrue(userId)
                .orElseGet(() -> addressRepo.findByUserId(userId).stream()
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "No delivery address found. Please add a shipping destination to complete your order.")));


        return formatFullAddressString(address);
    }


    private String formatFullAddressStringCustom(Address address) {
        StringBuilder sb = new StringBuilder();

        if (address.getStreet() != null && !address.getStreet().isBlank()) {
            sb.append(address.getStreet());
        }
        if (address.getLocalGovernment() != null && !address.getLocalGovernment().isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(address.getLocalGovernment());
        }
        if (address.getCity() != null && !address.getCity().isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(address.getCity());
        }
        if (address.getState() != null && !address.getState().isBlank()) {
            if (!sb.isEmpty()) sb.append(" ").append(address.getState());
        }
        if (address.getZip() != null && !address.getZip().isBlank()) {
            if (!sb.isEmpty()) sb.append(" ").append(address.getZip());
        }

        return sb.toString().trim();
    }

    private String formatFullAddressString(Address address) {
        return Stream.of(
                        address.getStreet(),
                        address.getLocalGovernment(),
                        address.getCity(),
                        address.getState(),
                        address.getZip()
                )
                .filter(field -> field != null && !field.isBlank())
                .collect(Collectors.joining(", "));
    }


}
