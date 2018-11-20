package com.welitonmartins.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.welitonmartins.model.PagamentoComBoleto;

// class service auxilar para gerar o boleto com 7 dias de venciamento
@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
	
	
	
}
