package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.PessoaInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PessoaInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Pessoa toDomainObject(PessoaInput pessoaInput) {
    return modelMapper.map(pessoaInput, Pessoa.class);
  }

  public void copyToDomainObject(PessoaInputId pessoaInput, Pessoa pessoa) {
    modelMapper.map(pessoaInput, pessoa);
  }
}
