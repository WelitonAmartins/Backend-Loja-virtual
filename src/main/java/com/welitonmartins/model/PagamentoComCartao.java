package com.welitonmartins.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.welitonmartins.model.enums.EstadoPagemento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	private Integer numerosDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	

	public PagamentoComCartao(Integer id, EstadoPagemento estado, Pedido pedido, Integer numerosDeParcelas) {
		super(id, estado, pedido);
		this.numerosDeParcelas = numerosDeParcelas;
	}



	public Integer getNumerosDeParcelas() {
		return numerosDeParcelas;
	}

	public void setNumerosDeParcelas(Integer numerosDeParcelas) {
		this.numerosDeParcelas = numerosDeParcelas;
	}
	

}
