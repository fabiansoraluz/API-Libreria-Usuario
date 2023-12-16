package com.sabersinfin.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.sabersinfin.entity.Usuario;
import com.sabersinfin.repository.UsuarioRepository;

@Service
public class UsuarioServices extends ICRUDImpl<Usuario, Integer>{
	
	@Autowired
	private UsuarioRepository repo;

	@Override
	public JpaRepository<Usuario, Integer> getRepository() {
		return repo;
	}


	// Validación
	public boolean existeUsername(String username) {
		return repo.existsByUsername(username);
	}
	// Loggin
	public Usuario loggin(String username,String password){
		return repo.findByUsernameAndPassword(username,password);
	}
	
	public boolean existeCelular(String celular) {
		return repo.existsByCelular(celular);
	}
	public boolean existeCorreo(String correo) {
		return repo.existsByCorreo(correo);
	}

}
