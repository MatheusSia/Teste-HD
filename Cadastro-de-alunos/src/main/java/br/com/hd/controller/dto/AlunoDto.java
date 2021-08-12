package br.com.hd.controller.dto;

import org.springframework.data.domain.Page;

import br.com.hd.modelo.Aluno;

public class AlunoDto {

	private String nome;

	private String idade;

	private String ensino;

	private String serie;

	private String email;
	
	private String cpf;

	public AlunoDto(Aluno aluno) {
		this.nome = aluno.getNome();
		this.idade = aluno.getIdade();
		this.ensino = aluno.getEnsino();
		this.serie = aluno.getSerie();
		this.email = aluno.getEmail();
		this.cpf = aluno.getCpf();
	}

	public String getNome() {
		return nome;
	}

	public String getIdade() {
		return idade + " Anos";
	}

	public String getEnsino() {
		return ensino;
	}

	public String getSerie() {
		return serie;
	}

	public String getEmail() {
		return email;
	}
	
	public String getCpf() {
		return cpf;
	}

	public static Page<AlunoDto> converter(Page<Aluno> aluno) {
		return aluno.map(AlunoDto::new);
	}
}
