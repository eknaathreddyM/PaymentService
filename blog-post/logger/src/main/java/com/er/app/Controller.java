package com.er.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class Controller {


    @PostMapping
    public void get(@RequestParam MultipartFile file) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(file.getInputStream()));

        CSVPa
    }
}
