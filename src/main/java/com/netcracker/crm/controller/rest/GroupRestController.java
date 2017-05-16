package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.request.GroupRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.GroupDto;
import com.netcracker.crm.service.entity.GroupService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.GroupValidator;
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
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_GROUP_CREATED;

/**
 * Created by Pasha on 01.05.2017.
 */
@RestController
@RequestMapping(value = "/groups")
public class GroupRestController {
    private final GroupService groupService;
    private final ResponseGenerator<Group> generator;
    private final GroupValidator groupValidator;
    private final BindingResultHandler bindingResultHandler;

    @Autowired
    public GroupRestController(GroupService groupService, GroupValidator groupValidator,
                                  ResponseGenerator<Group> generator, BindingResultHandler bindingResultHandler) {
        this.groupService = groupService;
        this.groupValidator = groupValidator;
        this.generator = generator;
        this.bindingResultHandler = bindingResultHandler;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<?> createGroup(@Valid GroupDto groupDto, BindingResult bindingResult){
        groupValidator.validate(groupDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }
        Group group = groupService.create(groupDto);
        if (group.getId() > 0){
            return generator.getHttpResponse(group.getId(), SUCCESS_MESSAGE, SUCCESS_GROUP_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/autocomplete")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<List<AutocompleteDto>> getAutocompleteDto(String pattern){
        return new ResponseEntity<>(groupService.getAutocompleteGroup(pattern), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<Map<String, Object>> getGroupRows(GroupRowRequest request){
        return new ResponseEntity<>(groupService.getGroupPage(request), HttpStatus.OK);
    }

}
