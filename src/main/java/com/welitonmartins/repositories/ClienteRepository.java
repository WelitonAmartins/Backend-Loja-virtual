package com.welitonmartins.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.welitonmartins.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	@Transactional(readOnly=true)//essa operacao nao necessita de ser envolvida como uma transação de bando de dados, isso faz ela ficar mais rapida
	Cliente findByEmail(String email);
}
