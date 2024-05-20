package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;


import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class EnderecoInputDisassemblerTest {

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private EnderecoInputDisassembler enderecoInputDisassembler;

  @Test
  void testToDomainObject() {

    EnderecoInput enderecoInput = new EnderecoInput();
    Endereco endereco = new Endereco();

    when(modelMapper.map(enderecoInput, Endereco.class)).thenReturn(endereco);

    Endereco result = enderecoInputDisassembler.toDomainObject(enderecoInput);

    verify(modelMapper).map(enderecoInput, Endereco.class);

    assertThat(result).isEqualTo(endereco);
  }

  @Test
  void testCopyToDomainObject() {
    // Dados de teste
    EnderecoInputId enderecoInputId = new EnderecoInputId();
    Endereco endereco = new Endereco();
    // Chamar o método a ser testado
    enderecoInputDisassembler.copyToDomainObject(enderecoInputId, endereco);
    // Verificar se o método do ModelMapper foi chamado corretamente
    verify(modelMapper).map(enderecoInputId, endereco);
  }
}