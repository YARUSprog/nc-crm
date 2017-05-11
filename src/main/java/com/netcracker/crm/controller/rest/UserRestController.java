package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.UserRowRequest;
import com.netcracker.crm.dto.UserDto;
import com.netcracker.crm.exception.RegistrationException;
import com.netcracker.crm.service.entity.UserService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.*;

/**
 * Created by bpogo on 4/30/2017.
 */
@RestController
@RequestMapping("/users")
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final UserService userService;
    private final UserValidator userValidator;
    private final BindingResultHandler bindingResultHandler;
    private final ResponseGenerator<User> generator;

    @Autowired
    public UserRestController(UserService userService, UserValidator userRowValidator,
                              BindingResultHandler bindingResultHandler, ResponseGenerator generator) {
        this.userService = userService;
        this.userValidator = userRowValidator;
        this.bindingResultHandler = bindingResultHandler;
        this.generator = generator;
    }

    @PostMapping("/registration")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<?> registerUser(@Valid UserDto userDto, BindingResult bindingResult) throws RegistrationException {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            log.error("Validation form error.");
            return bindingResultHandler.handle(bindingResult);
        }

        Long userId = userService.createUser(userDto);

        if (userId > 0) {
            log.info("User with id: " + userId + " successful created.");
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_USER_CREATED, HttpStatus.CREATED);
        }

        log.error("User was not created.");
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid UserDto userDto, BindingResult bindingResult) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        boolean isUpdated = userService.updateUser(userDto);
        if (isUpdated) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_USER_UPDATED, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getUsers(UserRowRequest userRowRequest) {
        return new ResponseEntity<>(userService.getUsers(userRowRequest), HttpStatus.OK);
    }

    @GetMapping("/lastNames")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<List<String>> getLastNames(String pattern) {
        return new ResponseEntity<>(userService.getUserLastNamesByPattern(pattern), HttpStatus.OK);
    }

}