package com.pauloalecio.gerenciamentopessoas.domain.service;


import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import com.pauloalecio.gerenciamentopessoas.domain.repository.PessoaRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

  @InjectMocks
  private PessoaService pessoaService;

  @Mock
  private PessoaRepository pessoaRepository;

  @Mock
  private EnderecoService enderecoService;

  private Pessoa pessoa;
  private Endereco endereco;

  @BeforeEach
  void setUp() {
    pessoa = new Pessoa();
    pessoa.setId(1L);
    pessoa.setNome("Alex Pas");
    pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));

    endereco = new Endereco();
    endereco.setId(1L);
    endereco.setLogradouro("Rua Antonio Fraga");
    endereco.setCep("12345-678");
    endereco.setNumero("10");
    endereco.setCidade("Bauru");
    endereco.setEstado("SP");
    endereco.setPessoa(pessoa);
  }

  @Test
  void listarPessoas() {
    when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(pessoa));

    List<Pessoa> result = pessoaService.listarPessoas();

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(pessoaRepository, times(1)).findAll();
  }

  @Test
  void buscarPessoaPorId() {
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

    Pessoa result = pessoaService.buscarPessoaPorId(1L);

    assertNotNull(result);
    assertEquals(pessoa.getId(), result.getId());
    assertEquals(pessoa.getNome(), result.getNome());
    assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    assertEquals(pessoa.getEnderecos(), result.getEnderecos());
    verify(pessoaRepository, times(1)).findById(1L);
  }

  @Test
  void criarPessoa() {
    when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

    Pessoa result = pessoaService.criarPessoa(pessoa);

    assertNotNull(result);
    assertEquals(pessoa.getId(), result.getId());
    assertEquals(pessoa.getNome(), result.getNome());
    assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    assertEquals(pessoa.getEnderecos(), result.getEnderecos());
    verify(pessoaRepository, times(1)).save(any(Pessoa.class));
  }

  @Test
  void atualizarPessoa() {
    when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

    Pessoa result = pessoaService.atualizarPessoa(pessoa);

    assertNotNull(result);
    assertEquals(pessoa.getId(), result.getId());
    assertEquals(pessoa.getNome(), result.getNome());
    assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    assertEquals(pessoa.getEnderecos(), result.getEnderecos());
    verify(pessoaRepository, times(1)).save(any(Pessoa.class));
  }

  @Test
  void deletarPessoa() {
    when(pessoaRepository.existsById(1L)).thenReturn(true);
    doNothing().when(pessoaRepository).deleteById(1L);

    pessoaService.deletarPessoa(1L);

    verify(pessoaRepository, times(1)).existsById(1L);
    verify(pessoaRepository, times(1)).deleteById(1L);
  }

  @Test
  void adicionarEndereco() {
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    when(enderecoService.criarEndereco(any(Endereco.class))).thenReturn(endereco);

    Endereco result = pessoaService.adicionarEndereco(1L, endereco);

    assertNotNull(result);
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(pessoaRepository, times(1)).findById(1L);
    verify(enderecoService, times(1)).criarEndereco(any(Endereco.class));
  }

  @Test
  void editarEndereco() {
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    when(enderecoService.atualizarEndereco(any(Endereco.class))).thenReturn(endereco);

    Endereco result = pessoaService.editarEndereco(1L, endereco);

    assertNotNull(result);
    assertEquals(endereco.getLogradouro(), result.getLogradouro());
    assertEquals(endereco.getId(), result.getId());
    assertEquals(endereco.getNumero(), result.getNumero());
    assertEquals(endereco.getCep(), result.getCep());
    assertEquals(endereco.getCidade(), result.getCidade());
    assertEquals(endereco.getEstado(), result.getEstado());
    assertEquals(endereco.getPessoa(), result.getPessoa());
    verify(pessoaRepository, times(1)).findById(1L);
    verify(enderecoService, times(1)).atualizarEndereco(any(Endereco.class));
  }

  @Test
  void definirEnderecoPrincipal() {
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);

    pessoaService.definirEnderecoPrincipal(1L, 1L);

    assertEquals(endereco.getId(), pessoa.getEndereco_principal_id());
    verify(pessoaRepository, times(1)).findById(1L);
    verify(enderecoService, times(1)).buscarEnderecoPorId(1L);
    verify(pessoaRepository, times(1)).save(pessoa);
  }

  @Test
  void excluirEndereco() {
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));
    when(enderecoService.buscarEnderecoPorId(1L)).thenReturn(endereco);
    doNothing().when(enderecoService).deletarEndereco(1L);

    pessoa.setEndereco_principal_id(1L);

    pessoaService.excluirEndereco(1L, 1L);

    assertNull(pessoa.getEndereco_principal_id());
    verify(pessoaRepository, times(1)).findById(1L);
    verify(enderecoService, times(1)).buscarEnderecoPorId(1L);
    verify(enderecoService, times(1)).deletarEndereco(1L);
  }

  @Test
  void obterEnderecoPrincipal() {
    pessoa.setEndereco_principal_id(1L);
    when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

    Long result = pessoaService.obterEnderecoPrincipal(1L);

    assertNotNull(result);
    assertEquals(pessoa.getEndereco_principal_id(), result);
    verify(pessoaRepository, times(1)).findById(1L);
  }
}
