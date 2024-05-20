package com.pauloalecio.gerenciamentopessoas.domain.service;

import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.repository.EnderecoRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

  @InjectMocks
  private EnderecoService enderecoService;

  @Mock
  private EnderecoRepository enderecoRepository;

  private Endereco endereco;

  @BeforeEach
  void setUp() {
    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua José Bonifácio");
    endereco.setCep("54321-876");
    endereco.setNumero("20");
    endereco.setCidade("Belo Horizonte");
    endereco.setEstado("MG");
  }

  @Test
  void listarEnderecos() {
    when(enderecoRepository.findAll()).thenReturn(Collections.singletonList(endereco));

    List<Endereco> result = enderecoService.listarEnderecos();

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(enderecoRepository, times(1)).findAll();
  }

  @Test
  void buscarEnderecoPorId() {
    when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

    Endereco result = enderecoService.buscarEnderecoPorId(1L);

    assertNotNull(result);
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(enderecoRepository, times(1)).findById(1L);
  }

  @Test
  void buscarEndereco() {
    when(enderecoRepository.findByPessoaId(1L)).thenReturn(Collections.singletonList(endereco));

    List<Endereco> result = enderecoService.buscarEndereco(1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(enderecoRepository, times(1)).findByPessoaId(1L);
  }

  @Test
  void buscarEnderecoPorPessoaId() {
    when(enderecoRepository.findByIdAndPessoaId(1L, 1L)).thenReturn(Optional.of(endereco));

    Endereco result = enderecoService.buscarEnderecoPorPessoaId(1L, 1L);

    assertNotNull(result);
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(enderecoRepository, times(1)).findByIdAndPessoaId(1L, 1L);
  }

  @Test
  void criarEndereco() {
    when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

    Endereco result = enderecoService.criarEndereco(endereco);

    assertNotNull(result);
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(enderecoRepository, times(1)).save(any(Endereco.class));
  }

  @Test
  void atualizarEndereco() {
    when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

    Endereco result = enderecoService.atualizarEndereco(endereco);

    assertNotNull(result);
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(enderecoRepository, times(1)).save(any(Endereco.class));
  }

  @Test
  void deletarEndereco() {
    when(enderecoRepository.existsById(1L)).thenReturn(true);
    doNothing().when(enderecoRepository).deleteById(1L);

    enderecoService.deletarEndereco(1L);

    verify(enderecoRepository, times(1)).existsById(1L);
    verify(enderecoRepository, times(1)).deleteById(1L);
  }
}
