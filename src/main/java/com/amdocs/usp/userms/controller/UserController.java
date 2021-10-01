package com.amdocs.usp.userms.controller;

import static com.amdocs.usp.userms.controller.UserController.REQUEST_MAPPING;

import javax.validation.Valid;

import com.amdocs.usp.userms.business.service.UserService;
import com.amdocs.usp.userms.view.object.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class UserController {

    protected static final String REQUEST_MAPPING = "/user";

    private static final String REQUEST_GET_USER = "/{id}";
    private static final String REQUEST_FIND_USER = "/find/{value}";
    private static final String REQUEST_DELETE_USER = "/{id}";
    private static final String REQUEST_LOGIN = "/login";

    private UserService service;

    @Autowired
    public UserController(@Qualifier(UserService.QUALIFIER) UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public ResponseEntity getAll(final Pageable pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(pageRequest));
    }

    @GetMapping(REQUEST_GET_USER)
    public ResponseEntity get(@PathVariable("id") int id) {
        UserVO data = service.get(id);
        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping(REQUEST_FIND_USER)
    public ResponseEntity get(@PathVariable("value") String value) {
        UserVO data = service.findUserByIdOrUsername(value);
        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping(REQUEST_LOGIN)
    public ResponseEntity login(@Valid @RequestBody UserVO data){
        UserVO user = service.login(data.getUsername(), data.getPassword());
        if(user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody UserVO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(data));
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody UserVO data) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(data));
    }

    @DeleteMapping(REQUEST_DELETE_USER)
    public ResponseEntity delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
