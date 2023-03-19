package com.logan.search.blog.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ApiOperation(value = "hello")
    @GetMapping
    public String hello() {
        return "Hello !!!";
    }
}

