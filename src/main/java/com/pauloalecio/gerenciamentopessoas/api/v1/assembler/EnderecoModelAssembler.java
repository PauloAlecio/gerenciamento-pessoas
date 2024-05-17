package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import com.pauloalecio.gerenciamentopessoas.api.v1.ApiLinks;
import com.pauloalecio.gerenciamentopessoas.api.v1.controller.EnderecoController;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.EnderecoModel;
import com.pauloalecio.gerenciamentopessoas.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EnderecoModelAssembler extends RepresentationModelAssemblerSupport<Endereco, EnderecoModel> {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ApiLinks apiLinks;

  public EnderecoModelAssembler() {
    super(EnderecoController.class, EnderecoModel.class);
  }

  public EnderecoModel toModel(Endereco endereco) {
    EnderecoModel enderecoModel = createModelWithId(endereco.getId(), endereco);
    modelMapper.map(endereco, enderecoModel);

      enderecoModel.add(apiLinks.linkToEndereco("enderecos"));

    return enderecoModel;
  }

  @Override
  public CollectionModel<EnderecoModel> toCollectionModel(Iterable<? extends Endereco> entities) {
    CollectionModel<EnderecoModel> collectionModel = super.toCollectionModel(entities);

    collectionModel.add(apiLinks.linkToEndereco());

    return collectionModel;
  }
}
