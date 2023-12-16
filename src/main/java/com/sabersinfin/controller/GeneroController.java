package com.sabersinfin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sabersinfin.entity.Genero;
import com.sabersinfin.servicesImpl.GeneroServices;

@RestController
@RequestMapping("api/genero")
public class GeneroController {

	@Autowired
	private GeneroServices serGenero;
	
	@GetMapping("/lista")
	public ResponseEntity<List<Genero>> lista(){
		return  new ResponseEntity<>(serGenero.listarTodos(),HttpStatus.OK);
	}
}

