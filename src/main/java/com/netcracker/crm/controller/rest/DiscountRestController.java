package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.dto.DiscountDto;
import com.netcracker.crm.service.entity.DiscountService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.DiscountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_DISCOUNT_CREATED;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
public class DiscountRestController {
    private final DiscountService discountService;
    private final DiscountValidator discountValidator;
    private final ResponseGenerator<Discount> generator;
    private final BindingResultHandler bindingResultHandler;

    @Autowired
    public DiscountRestController(DiscountService discountService, DiscountValidator discountValidator,
                              ResponseGenerator<Discount> generator, BindingResultHandler bindingResultHandler) {
        this.discountService = discountService;
        this.discountValidator = discountValidator;
        this.generator = generator;
        this.bindingResultHandler = bindingResultHandler;
    }


    @RequestMapping(value = "/csr/addDiscount", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@Valid DiscountDto discountDto, BindingResult bindingResult){
        discountValidator.validate(discountDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }
        Discount disc = discountService.persist(discountDto);
        if (disc.getId() > 0){
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_DISCOUNT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/csr/discountByTitle/{title}", method = RequestMethod.GET)
    public List<Discount> discountByTitle(@PathVariable String title){
        return discountService.getProductDiscount(title);
    }
}
