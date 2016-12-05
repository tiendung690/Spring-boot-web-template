package com.scc.webtemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scc.webtemplate.repository.ProductRepository;

@Controller
@RequestMapping("/pages")
public class DashboardController {
	
	@Autowired
	private ProductRepository productRepository;	
	
    @RequestMapping("dashboard")
    public ModelMap daftarMateri(){
        ModelMap mm = new ModelMap();
        mm.addAttribute("productsCount", productRepository.count());
        return mm;
    }		    
    
}
