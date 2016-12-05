package com.scc.webtemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scc.webtemplate.repository.MateriRepository;

@Controller
@RequestMapping("/pages")
public class BootstrapElementsController {

	@Autowired
	private MateriRepository materiDao;
	
    @RequestMapping("bootstrap-elements")
    public ModelMap daftarMateri(Pageable pageable){
        ModelMap mm = new ModelMap();
        mm.addAttribute("daftarMateri", materiDao.findAll(pageable));
        return mm;
    }	
	
}
