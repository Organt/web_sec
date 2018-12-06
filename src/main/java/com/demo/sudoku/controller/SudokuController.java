package com.demo.sudoku.controller;

import com.demo.sudoku.model.Generator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SudokuController {

    @GetMapping("/sudoku")
    public String generateSudoku(Model model) {
        model.addAttribute("sudoku", Generator.generate());
        return "sudoku";
    }
}
