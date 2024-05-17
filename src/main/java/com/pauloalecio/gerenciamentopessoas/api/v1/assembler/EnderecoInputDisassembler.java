package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInput;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.input.EnderecoInputId;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoInputDisassembler {

  @Autowired
  private ModelMapper modelMapper;

  public Endereco toDomainObject(EnderecoInput enderecoInput) {
    return modelMapper.map(enderecoInput, Endereco.class);
  }

  public void copyToDomainObject(EnderecoInputId enderecoInput, Endereco endereco) {
    modelMapper.map(enderecoInput, endereco);
  }
}
