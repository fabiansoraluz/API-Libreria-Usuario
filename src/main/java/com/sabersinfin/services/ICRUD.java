package com.sabersinfin.services;

import java.util.List;

public interface ICRUD<T,ID> {

	T registrar(T bean);
	T actualizar(T bean);
	boolean eliminarPorId(ID cod);
	T buscarPorId(ID cod);
	List<T> listarTodos();
}
