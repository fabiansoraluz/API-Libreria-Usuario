package com.sabersinfin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sabersinfin.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	public boolean existsByUsername(String username);
	public Usuario findByUsernameAndPassword(String username, String password);
	
	public boolean existsByCelular(String celular);
	public boolean existsByCorreo(String correo);
}
