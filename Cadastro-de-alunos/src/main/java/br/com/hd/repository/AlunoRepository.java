package br.com.hd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.hd.modelo.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	Optional<Aluno> findByNome(String nome);
	
	@Query("select a from Aluno a order by ensino ASC, serie ASC")
	List<Aluno> listaOrdenadaPorEnsinoESerie();

}
