package com.uchechukwu.store.validators;

import com.uchechukwu.store.sortingAndPaginating.SortAndPaginate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatedSortedData {
    private final SortAndPaginate sortAndPaginate;
    private final RequestValidators requestValidate;

    public Sort getValidatedSortedData(String sort, String sortingValue1) {
        var sorted = requestValidate.validateRequestParam(sort, sortingValue1);
        return sortAndPaginate.getSortedData(sorted);
    }

    public Sort getValidatedSortedDatas(String sort, String sortingValue1, String sortingValue2) {
        var sorted = requestValidate.validateRequestParams(sort, sortingValue1, sortingValue2);
        return sortAndPaginate.getSortedData(sorted);
    }

    public Pageable getValidatedPageableData(String sort, String sortingValue1, int page, int size) {
        var validated = requestValidate.validateRequestParam(sort, sortingValue1);
        return sortAndPaginate.getPageableWithoutDirection(page, size, validated);

    }

}
