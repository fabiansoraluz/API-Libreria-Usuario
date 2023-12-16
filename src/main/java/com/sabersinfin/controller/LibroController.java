package com.sabersinfin.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sabersinfin.entity.Libro;

import com.sabersinfin.servicesImpl.LibroServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/libro")
public class LibroController {

	@Autowired
	private LibroServices serLibro;

	@GetMapping("/lista")
	public ResponseEntity<List<Libro>> lista() {

		return new ResponseEntity<>(serLibro.listarTodos(), HttpStatus.OK);
	}
	
	@GetMapping("/listaEnlace")
	public ResponseEntity<List<Libro>> listaEnlace() {

		
		return new ResponseEntity<>(serLibro.listarEnlaces(), HttpStatus.OK);
	}


	@PostMapping("/registrar")
	public ResponseEntity<String> registrar(@Valid @RequestBody Libro bean, BindingResult result) {

		bean.setRegistro(LocalDate.now());
		bean.setEstado(true);

		serLibro.registrar(bean);

		return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Libro registrado\" }");
	}

	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizar(@Valid @RequestBody Libro bean, BindingResult result) {

		Libro lib = serLibro.buscarPorId(bean.getId());

		if (lib == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Libro no encontrado\" }");
		}

		bean.setRegistro(lib.getRegistro());
		bean.setUsuario(lib.getUsuario());

		serLibro.actualizar(bean);

		return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Libro actualizado\" }");
	}

	@GetMapping("/buscarPorGenero/{codigoGenero}")
	public ResponseEntity<?> buscarPorGenero(@PathVariable("codigoGenero") Integer codigoGenero) {
		List<Libro> libros = serLibro.buscarPorGenero(codigoGenero);

		if (libros.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Libro no encontrado\" }");
		}

		return new ResponseEntity<>(libros, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminarLibro(@PathVariable("id") int id) {
		Libro libro = serLibro.buscarPorId(id);

		if (libro == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Libro no encontrado\" }");
		}

		serLibro.eliminarPorId(id);

		return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Libro eliminado\" }");
	}
	
	

}
