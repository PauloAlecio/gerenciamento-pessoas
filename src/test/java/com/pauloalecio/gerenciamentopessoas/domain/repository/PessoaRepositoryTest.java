package com.pauloalecio.gerenciamentopessoas.domain.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PessoaRepositoryTest {
  @Mock
  private PessoaRepository pessoaRepository;

  @Test
  void deleteInBatch() {
    List<Pessoa> pessoas = new ArrayList<>();
    pessoaRepository.deleteInBatch(pessoas);
    verify(pessoaRepository).deleteInBatch(pessoas);
  }
}