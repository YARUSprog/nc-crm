package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.domain.request.DiscountRowRequest;
import com.netcracker.crm.dto.DiscountDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface DiscountService {

    Discount create(DiscountDto discountDto);
    boolean update(DiscountDto discountDto);

    Discount getDiscountById(Long id);

    List<AutocompleteDto> getAutocompleteDto(String pattern);
    Map<String, Object> getDiscountRows(DiscountRowRequest rowRequest);
}
