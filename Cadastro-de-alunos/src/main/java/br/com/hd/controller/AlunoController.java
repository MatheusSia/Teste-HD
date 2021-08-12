package br.com.hd.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.hd.controller.dto.AlunoDto;
import br.com.hd.controller.form.AlunoForm;
import br.com.hd.modelo.Aluno;
import br.com.hd.repository.AlunoRepository;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@PostMapping
	public ResponseEntity<AlunoDto> cadastro(@Valid @RequestBody AlunoForm form, UriComponentsBuilder uriBuilder) {
		List<Aluno> alunos = alunoRepository.findAll();
		for (Aluno aluno : alunos) {
			if (form.getCpf().equals(aluno.getCpf())) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		Aluno aluno = form.converter();
		alunoRepository.save(aluno);

		URI uri = uriBuilder.path("/aluno/{id}").buildAndExpand(aluno.getId()).toUri();
		return ResponseEntity.created(uri).body(new AlunoDto(aluno));
	}

	@GetMapping
	public Page<AlunoDto> listar(
			@PageableDefault(sort = "ensino", direction = Direction.ASC, page = 0, size = 15) Pageable paginacao) {
		Page<Aluno> alunos = alunoRepository.findAll(paginacao);
		return AlunoDto.converter(alunos);
	}

	@GetMapping("/{nome}")
	public ResponseEntity<AlunoDto> buscaUsuarioNome(@PathVariable String nome) {
		Optional<Aluno> aluno = alunoRepository.findByNome(nome);
		if (aluno.isPresent()) {
			return ResponseEntity.ok(new AlunoDto(aluno.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/ordemensino")
	public List<Aluno> listaOrdenadaPorEnsinoESerie() {
		return alunoRepository.listaOrdenadaPorEnsinoESerie();
	}
}
