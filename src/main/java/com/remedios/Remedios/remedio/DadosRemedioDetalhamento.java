package com.remedios.Remedios.remedio;

import java.time.LocalDate;

public record DadosRemedioDetalhamento(
		
		Long id,
		String nome,
		Via via,
		String lote,
		int quantidade,
		LocalDate validade,
		Laboratorio laboratorio,
		Boolean ativo
		) {

	public DadosRemedioDetalhamento(Remedio remedio) {
		this(remedio.getId(),remedio.getNome(),remedio.getVia(),remedio.getLote(),remedio.getQuantidade(),remedio.getValidade(),remedio.getLaboratorio(),remedio.getAtivo());
	}
}
