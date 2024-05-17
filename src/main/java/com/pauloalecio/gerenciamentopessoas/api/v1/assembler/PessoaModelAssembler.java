package com.pauloalecio.gerenciamentopessoas.api.v1.assembler;

import com.pauloalecio.gerenciamentopessoas.api.v1.ApiLinks;
import com.pauloalecio.gerenciamentopessoas.api.v1.controller.PessoaController;
import com.pauloalecio.gerenciamentopessoas.api.v1.model.PessoaModel;
import com.pauloalecio.gerenciamentopessoas.domain.model.Pessoa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PessoaModelAssembler extends RepresentationModelAssemblerSupport<Pessoa, PessoaModel> {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ApiLinks apiLinks;

  public PessoaModelAssembler() {
    super(PessoaController.class, PessoaModel.class);
  }

  public PessoaModel toModel(Pessoa pessoa) {
    PessoaModel pessoaModel = createModelWithId(pessoa.getId(), pessoa);
    modelMapper.map(pessoa, pessoaModel);

      pessoaModel.add(apiLinks.linkToPessoas("pessoas"));

    return pessoaModel;
  }

  @Override
  public CollectionModel<PessoaModel> toCollectionModel(Iterable<? extends Pessoa> entities) {
    CollectionModel<PessoaModel> collectionModel = super.toCollectionModel(entities);

    collectionModel.add(apiLinks.linkToPessoas());

    return collectionModel;
  }
}
