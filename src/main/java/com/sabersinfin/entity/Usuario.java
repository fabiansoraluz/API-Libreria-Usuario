package com.sabersinfin.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario", nullable = false, unique = true, length = 5)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "paterno", nullable = false)
    private String paterno;
    
    @Column(name = "materno", nullable = false)
    private String materno;

	@Column(name ="celular", nullable = false,unique = true)
	private String celular;
	
	@Column(name ="correo",nullable = false,unique = true)
	private String correo;
    
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate registro;

    @Column(name = "username", length = 30, nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

    @Column(name = "estado", nullable = false)
    private int estado;
    
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
    
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Libro> libros;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Vistas> vistas;
    
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Favoritos> favoritos;

}
