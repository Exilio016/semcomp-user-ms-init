package com.amdocs.usp.userms.controller;

import com.amdocs.usp.userms.business.service.UserService;
import com.amdocs.usp.userms.view.object.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.amdocs.usp.userms.controller.UserController.REQUEST_MAPPING;

@Controller
@RequestMapping(REQUEST_MAPPING)
public class UserController {
    protected static final String REQUEST_MAPPING = "/user";
    private static final String REQUEST_GET_USER = "/{id}";
    private static final String REQUEST_LOGIN = "/login";

    private UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserVO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping(REQUEST_GET_USER)
    public ResponseEntity<UserVO> get(@PathVariable("id") int id) {
        UserVO data = service.get(id);
        if (data == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
    }

    @PostMapping(REQUEST_LOGIN)
    public ResponseEntity<UserVO> login(@Valid @RequestBody UserVO data) {
        UserVO user = service.login(data.getUsername(), data.getPassword());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserVO> save(@Valid @RequestBody UserVO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(data));
    }

}
