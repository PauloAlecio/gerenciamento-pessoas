package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class PessoaInputDisassemblerTest {

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private PessoaInputDisassembler pessoaInputDisassembler;

  @Test
  void testToDomainObject() {
    PessoaInput pessoaInput = new PessoaInput();
    Pessoa pessoa = new Pessoa();

    when(modelMapper.map(pessoaInput, Pessoa.class)).thenReturn(pessoa);

    Pessoa result = pessoaInputDisassembler.toDomainObject(pessoaInput);

    verify(modelMapper).map(pessoaInput, Pessoa.class);

    assertThat(result).isEqualTo(pessoa);
  }

  @Test
  void testCopyToDomainObject() {

    PessoaInputId pessoaInputId = new PessoaInputId();
    Pessoa pessoa = new Pessoa();

    pessoaInputDisassembler.copyToDomainObject(pessoaInputId, pessoa);

    verify(modelMapper).map(pessoaInputId, pessoa);
  }
}