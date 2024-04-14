package com.capstone.dangdang.controller;

import com.capstone.dangdang.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

}
