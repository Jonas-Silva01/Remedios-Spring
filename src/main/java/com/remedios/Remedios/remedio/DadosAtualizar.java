package com.remedios.Remedios.remedio;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizar(
		
		@NotNull
		Long id, 
		String nome, 
		Via via, 
		Laboratorio laboratorio) {
	
}
