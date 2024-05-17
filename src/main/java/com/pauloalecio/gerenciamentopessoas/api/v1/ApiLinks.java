package com.pauloalecio.gerenciamentopessoas.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.pauloalecio.gerenciamentopessoas.api.v1.controller.EnderecoController;
import com.pauloalecio.gerenciamentopessoas.api.v1.controller.PessoaController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.stereotype.Component;

@Component
public class ApiLinks {

  public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
      new TemplateVariable("page", VariableType.REQUEST_PARAM),
      new TemplateVariable("size", VariableType.REQUEST_PARAM),
      new TemplateVariable("sort", VariableType.REQUEST_PARAM));


  public Link linkToPessoas(String rel) {
    return linkTo(PessoaController.class).withRel(rel);
  }

  public Link linkToPessoas() {
    return linkToPessoas(IanaLinkRelations.SELF.value());
  }

  public Link linkToEndereco(String rel) {
    return linkTo(EnderecoController.class).withRel(rel);
  }

  public Link linkToEndereco() {
    return linkToEndereco(IanaLinkRelations.SELF.value());
  }

}
