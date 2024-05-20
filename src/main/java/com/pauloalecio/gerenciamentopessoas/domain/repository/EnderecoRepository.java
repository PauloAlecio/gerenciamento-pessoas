package com.pauloalecio.gerenciamentopessoas.domain.repository;

import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
  List<Endereco> findByPessoaId(Long pessoaId );
  Optional<Endereco> findByIdAndPessoaId(Long enderecoId,Long pessoaId );
}