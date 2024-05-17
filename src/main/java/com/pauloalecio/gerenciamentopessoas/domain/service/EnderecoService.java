package com.pauloalecio.gerenciamentopessoas.domain.service;

import com.pauloalecio.gerenciamentopessoas.domain.exception.EnderecoNaoEncontradoException;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import com.pauloalecio.gerenciamentopessoas.domain.repository.EnderecoRepository;
import com.pauloalecio.gerenciamentopessoas.exception.EnderecoException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EnderecoService {

  private final EnderecoRepository enderecoRepository;

  public List<Endereco> listarEnderecos() {
    return enderecoRepository.findAll();
  }

  public Endereco buscarEnderecoPorId(Long id) {
   return enderecoRepository.findById(id).orElseThrow(() -> new EnderecoNaoEncontradoException(id));
  }

  public Endereco criarEndereco(Endereco endereco) {
    return enderecoRepository.save(endereco);
  }

  @Transactional
  public Endereco atualizarEndereco(Endereco endereco) {
   return enderecoRepository.save(endereco);
  }

  public void deletarEndereco(Long id) {
    boolean exists = enderecoRepository.existsById(id);
    if (!exists){
      throw new EnderecoNaoEncontradoException(id);
    }
    enderecoRepository.deleteById(id);

  }
}