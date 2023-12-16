package com.sabersinfin.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sabersinfin.entity.CambioClaveDTO;
import com.sabersinfin.entity.LogginEntity;
import com.sabersinfin.entity.Rol;
import com.sabersinfin.entity.Usuario;
import com.sabersinfin.servicesImpl.UsuarioServices;
import com.sabersinfin.utils.NotFoundException;
import com.sabersinfin.utils.EncriptadorSHA256;


@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServices SUsuario;
	

	@GetMapping("/lista")
	public ResponseEntity<?> listar(){
		List<Usuario> lista = SUsuario.listarTodos();
		if(lista.size()>0) {
			return new ResponseEntity<List<Usuario>>(lista,HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"No hay usuarios registrados\" }");
	}
	
	@GetMapping("buscar/{id}")
	public ResponseEntity<?> buscar(@PathVariable int id){
		Usuario bean = SUsuario.buscarPorId(id);
		if(bean != null) {
			return new ResponseEntity<Usuario>(bean,HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Usuario no encontrada\" }");
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestBody Usuario bean) {
	    if (SUsuario.existeUsername(bean.getUsername())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Username se encuentra registrado\" }");
	    } else if (SUsuario.existeCelular(bean.getCelular())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Celular ya registrado\" }");
	    } else if (SUsuario.existeCorreo(bean.getCorreo())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Correo ya registrado\" }");
	    }

	    bean.setRegistro(LocalDate.now());
	    bean.setEstado(1);

	    Rol r = new Rol();
	    r.setId(2);
	    bean.setRol(r);

	    // Encriptar la contraseña utilizando SHA-256
	    String contrasenaEncriptada = EncriptadorSHA256.encriptarSHA256(bean.getPassword());
	    bean.setPassword(contrasenaEncriptada);

	    Usuario usuario = SUsuario.registrar(bean);

	    if (usuario != null) {
	        return ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\": \"Usuario Registrado correctamente\" }");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Error al registrar usuario\" }");
	}

	@PostMapping("/loggin")
	public ResponseEntity<?> loggin(@RequestBody LogginEntity bean){
	    // Encriptar la password para validar usuario
	    String contrasenaEncriptada = EncriptadorSHA256.encriptarSHA256(bean.getPassword());
	    bean.setPassword(contrasenaEncriptada);

	    Usuario user = SUsuario.loggin(bean.getUsername(), contrasenaEncriptada);
	    if (user != null) {
	        String jsonResponse = String.format("{ \"id\": %d, \"descripcion\": \"%s\" }", user.getId(), user.getRol().getDescripcion());
	        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Username o password incorrecto\" }");
	}
	
	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizar(@RequestBody Usuario bean){
		Usuario u = SUsuario.buscarPorId(bean.getId());
		
		if(u != null) {
			bean.setEstado(u.getEstado());
			bean.setRol(u.getRol());
			bean.setRegistro(u.getRegistro());
			bean.setRol(u.getRol());
			bean.setPassword(u.getPassword());			
			
			SUsuario.actualizar(bean);
			return ResponseEntity.status(HttpStatus.CREATED).body("{ \"message\": \"Usuario Actualizado correctamente\" }");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Error al actualizar usuario\" }");
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id){
		boolean response = SUsuario.eliminarPorId(id);
		if(response) {
			return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"Usuario eliminado\" }");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"message\": \"Error al eliminar usuario\" }");
	}
	

    /*
	@PutMapping("/cambiar-clave/{id}")
	public ResponseEntity<?> cambiarContrasena(@PathVariable Integer id, @RequestBody CambioClaveDTO request) {
	    Usuario usuario = SUsuario.buscarPorId(id);
	    
	    if (usuario == null) {
	        throw new NotFoundException();
	    }

	    if (!encoder.matches(request.getContrasenaActual(), usuario.getClave())) {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"La contraseña actual es incorrecta\"}");
	    }
	    
	    if (request.getNuevaContrasena().equals(request.getContrasenaActual())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"La nueva contraseña no puede ser igual a la actual\"}");
	    }

	    if (!request.getNuevaContrasena().equals(request.getConfirmacionContrasena())) {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"Las contraseñas no coinciden\"}");
	    }

	    usuario.setClave(encoder.encode(request.getNuevaContrasena()));
	    serUsuario.actualizar(usuario);

	    return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\": \"Contraseña actualizada correctamente\"}");
	}

*/
	
	

	
	

}
