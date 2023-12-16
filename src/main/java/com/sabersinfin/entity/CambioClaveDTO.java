package com.sabersinfin.entity;

import lombok.Data;

@Data
public class CambioClaveDTO {

	private String passwordActual;
    private String nuevaPassword;
    private String confirmacionNuevoPassword;
}
