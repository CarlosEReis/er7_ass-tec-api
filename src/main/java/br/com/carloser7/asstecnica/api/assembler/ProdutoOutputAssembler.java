package br.com.carloser7.asstecnica.api.assembler;

import br.com.carloser7.asstecnica.api.model.output.ProdutoOutput;
import br.com.carloser7.asstecnica.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoOutputAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoOutput toOutput(Produto produto) {
        return modelMapper.map(produto, ProdutoOutput.class);
    }

    public Page<ProdutoOutput> toCollectionOutput(Page<Produto> produtos) {
        List<ProdutoOutput> produtoOutputs = produtos.getContent().stream()
            .map(this::toOutput)
            .toList();
        return new PageImpl<>(
            produtoOutputs,
            PageRequest.of(produtos.getNumber(), produtos.getSize()),
            produtos.getTotalElements());
    }
}
