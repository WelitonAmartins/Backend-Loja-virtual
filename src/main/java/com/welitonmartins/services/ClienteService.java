package com.welitonmartins.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welitonmartins.model.Cliente;
import com.welitonmartins.repositories.ClienteRepository;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//metado que busca por id
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
