package br.com.carloser7.asstecnica.domain.repository;

import br.com.carloser7.asstecnica.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {
}
