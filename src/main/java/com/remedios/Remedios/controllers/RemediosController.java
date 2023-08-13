package com.remedios.Remedios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remedios.Remedios.remedio.DadosAtualizar;
import com.remedios.Remedios.remedio.DadosCadastrais;
import com.remedios.Remedios.remedio.DadosListagemRemedio;
import com.remedios.Remedios.remedio.Remedio;
import com.remedios.Remedios.remedio.RemedioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
public class RemediosController{
	
	@Autowired
	RemedioRepository repository;
	
	
	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastrais dados) {
		Remedio r = new Remedio (dados);
		repository.save(r);
		
	}

	@GetMapping
	public List<DadosListagemRemedio> list(){
		return repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
	}

	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizar dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizar(dados);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public void inativar (@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();
	}
}
