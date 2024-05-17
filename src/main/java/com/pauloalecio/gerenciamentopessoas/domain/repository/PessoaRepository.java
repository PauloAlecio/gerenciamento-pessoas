package com.pauloalecio.gerenciamentopessoas.domain.repository;

import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}