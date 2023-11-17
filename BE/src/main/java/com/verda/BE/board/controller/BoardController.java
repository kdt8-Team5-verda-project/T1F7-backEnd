package com.verda.BE.board.controller;


import com.verda.BE.board.dto.requestdto.BoardRequestDTO;
import com.verda.BE.board.service.BoardSerivce;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardController {
    private BoardSerivce boardSerivce;

//    @PostMapping("/api/v1/boards")
//    public Long save(@RequestBody BoardRequestDTO requestDTO){
//        return boardSerivce.save(requestDTO);
//    }

//    @GetMapping("/")
//    public String list(){
//        return "board";
//    }
//


}
