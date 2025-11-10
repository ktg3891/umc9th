package com.example.umc9th.domain.test.controller;

import com.example.umc9th.domain.test.dto.res.TestResDTO;
import com.example.umc9th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class TempRestController {

    @GetMapping("/exception")
    public ApiResponse<TestResDTO.Exception> exception(
            @RequestParam Long flag
    ) {
        return null;
    }
}
