package com.uchechukwu.store.controllers;

import com.uchechukwu.store.api_builder_response.ApiResponse;
import com.uchechukwu.store.dtos.request.AddressRequest;
import com.uchechukwu.store.dtos.request.UpdateAddressRequest;
import com.uchechukwu.store.dtos.response.AddressPageResponse;
import com.uchechukwu.store.dtos.response.AddressResponse;
import com.uchechukwu.store.service.AddressService;
import com.uchechukwu.store.utilities.rateLimiter.RateLimit;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Address", description = "Endpoints for address creation and management")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/address/create")
    @RateLimit
    public AddressResponse createAddress(@Valid @RequestBody AddressRequest request) {
        return addressService.createAddress(request);
    }

    @PatchMapping("/address/{addressId}/update")
    @RateLimit
    public AddressResponse updateAddress(@PathVariable UUID addressId, @Valid @RequestBody UpdateAddressRequest request) {
        return addressService.updateAddress(addressId, request);
    }

    @DeleteMapping("/address/{addressId}/delete")
    @RateLimit
    public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable UUID addressId) {
        return addressService.deleteAddress(addressId);
    }

    @GetMapping("/address/{addressId}/get")
    @RateLimit
    public AddressResponse getAddressForUser(@PathVariable UUID addressId) {
        return addressService.getAddressForUser(addressId);
    }

    @GetMapping("/address/set/{addressId}/default")
    @RateLimit
    public AddressResponse getDefaultAddressForUser(@PathVariable UUID addressId) {
        return addressService.setDefaultAddress(addressId);
    }

    @GetMapping("/address/get/default")
    @RateLimit
    public AddressResponse getDefaultAddressForUser() {
        return addressService.getDefaultAddress();
    }


    @GetMapping("/address/all")
    @RateLimit
    public List<AddressResponse> getAllAddressesForUser() {
        return addressService.getAllAddressesForUser();
    }

    @GetMapping("/admin/address/{addressId}/get")
    @RateLimit
    public AddressResponse getAddressForAdmin(@PathVariable UUID addressId) {
        return addressService.adminGetSingleAddress(addressId);
    }

    @GetMapping("/admin/address/all")
    @RateLimit
    public AddressPageResponse getAllAddressesForAdmin(@RequestParam(required = false, defaultValue = "", name = "sort") String sort, @RequestParam(required = false, defaultValue = "0", name = "page") int page, @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return addressService.adminGetAllAddresses(sort, "state", page, size);
    }

    @GetMapping("/admin/address/{userId}/get")
    @RateLimit
    public Page<AddressResponse> getAllUserAddressesForAdmin(@PathVariable UUID userId, @RequestParam(required = false, defaultValue = "", name = "sort") String sort, @RequestParam(required = false, defaultValue = "0", name = "page") int page,
                                                             @RequestParam(required = false, defaultValue = "10", name = "size") int size) {
        return addressService.adminGetAddressesByUserId(userId, sort, "state", page, size);
    }

}
