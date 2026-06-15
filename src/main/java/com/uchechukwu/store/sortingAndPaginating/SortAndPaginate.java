package com.uchechukwu.store.sortingAndPaginating;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortAndPaginate {

    public Sort getSortedData(String sortBy) {
        return Sort.by(sortBy);
    }

    public Sort getSortedDataWithDirection(
            String sortBy,
            String direction) {

        return "desc".equalsIgnoreCase(direction)
                ? getSortedData(sortBy).descending()
                : getSortedData(sortBy).ascending();
    }

    public Pageable getPageableWithDirection(
            int page,
            int size,
            String sortBy,
            String direction) {

        var sort = getSortedDataWithDirection(sortBy, direction);

        return PageRequest.of(page, size, sort);
    }

    public Pageable getPageableWithoutDirection(
            int page,
            int size,
            String sortBy) {

        var sort = getSortedData(sortBy);

        return PageRequest.of(page, size, sort);
    }

}
