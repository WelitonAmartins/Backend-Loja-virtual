package com.welitonmartins.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.welitonmartins.model.ItemPedido;
import com.welitonmartins.model.PagamentoComBoleto;
import com.welitonmartins.model.Pedido;
import com.welitonmartins.model.enums.EstadoPagemento;
import com.welitonmartins.repositories.ItemPedidoRepository;
import com.welitonmartins.repositories.PagamentoRepository;
import com.welitonmartins.repositories.PedidoRepository;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository IPR;
	
	//metado que busca por id
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);// (null) garantido que está fazendo um novo pedido, qe ele não tenha um id existente
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagemento.PENDENTE);
		obj.getPagamento().setPedido(obj);//pagamento tem que conhecer o pedido dele
		
		//se o pagamento for com boleto
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		IPR.saveAll(obj.getItens());
		return obj;
	}

	

}
