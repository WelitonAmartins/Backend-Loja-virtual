package com.welitonmartins.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welitonmartins.model.Pedido;
import com.welitonmartins.repositories.PedidoRepository;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository cr;
	
	//metado que busca por id
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = cr.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
