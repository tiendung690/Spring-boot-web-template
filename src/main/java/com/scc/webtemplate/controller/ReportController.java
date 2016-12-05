package com.scc.webtemplate.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scc.webtemplate.repository.ProductRepository;

@Controller 
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ProductRepository productRepository;
	
    @RequestMapping("/products")
    public void daftarMateri(ModelMap mm){
        mm.addAttribute("dataDalamReport", productRepository.findAll());
        mm.addAttribute("tanggalCetak", new Date());
        mm.addAttribute("format", "pdf");
    }
	
}
