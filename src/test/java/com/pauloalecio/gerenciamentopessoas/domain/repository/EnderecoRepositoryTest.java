package com.pauloalecio.gerenciamentopessoas.domain.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EnderecoRepositoryTest {

  @Mock
  private EnderecoRepository enderecoRepository;

  @Test
  void testFindByPessoaId() {
    Long pessoaId = 1L;
    List<Endereco> enderecos = new ArrayList<>();
    when(enderecoRepository.findByPessoaId(pessoaId)).thenReturn(enderecos);
    List<Endereco> result = enderecoRepository.findByPessoaId(pessoaId);
    assertThat(result).isEqualTo(enderecos);
  }

  @Test
  void testFindByIdAndPessoaId() {
    Long enderecoId = 1L;
    Long pessoaId = 1L;
    Endereco endereco = new Endereco();
    when(enderecoRepository.findByIdAndPessoaId(enderecoId, pessoaId)).thenReturn(Optional.of(endereco));
    Optional<Endereco> result = enderecoRepository.findByIdAndPessoaId(enderecoId, pessoaId);
    assertThat(result.isPresent()).isTrue();
    assertThat(result.get()).isEqualTo(endereco);
  }

  @Test
  void deleteInBatch() {
    List<Endereco> enderecos = new ArrayList<>();
    enderecoRepository.deleteInBatch(enderecos);
    verify(enderecoRepository).deleteInBatch(enderecos);
  }



}