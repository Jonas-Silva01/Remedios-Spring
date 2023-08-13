package com.remedios.Remedios.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.remedios.Remedios.remedio.DadosAtualizar;
import com.remedios.Remedios.remedio.DadosCadastrais;
import com.remedios.Remedios.remedio.DadosListagemRemedio;
import com.remedios.Remedios.remedio.DadosRemedioDetalhamento;
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
	public ResponseEntity<DadosRemedioDetalhamento> cadastrar(@RequestBody @Valid DadosCadastrais dados, UriComponentsBuilder uriBuilder) {
		var remedio = new Remedio (dados);
		repository.save(remedio);
		
		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosRemedioDetalhamento(remedio));
	}

	@GetMapping
	public ResponseEntity<List<DadosListagemRemedio>> list(){
		var lista = repository.findAllByAtivoTrue().stream().map(DadosListagemRemedio::new).toList();
		return ResponseEntity.ok(lista);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosRemedioDetalhamento> atualizar(@RequestBody @Valid DadosAtualizar dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizar(dados);
	
		return ResponseEntity.ok(new DadosRemedioDetalhamento(remedio));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("inativar/{id}")
	@Transactional
	public ResponseEntity<Void> inativar (@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.inativar();
	
		return ResponseEntity.noContent().build();
	}

	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<Void> ativar (@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.ativar();
	
		return ResponseEntity.noContent().build();
	} 

	@GetMapping("/{id}")
	public ResponseEntity<DadosRemedioDetalhamento> detalhar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
	
		return ResponseEntity.ok(new DadosRemedioDetalhamento(remedio));
	}
}
