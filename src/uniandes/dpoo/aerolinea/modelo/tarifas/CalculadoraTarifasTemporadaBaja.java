package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{

	protected static final int COSTO_POR_KM_NATURAL = 600;
	protected static final int COSTO_POR_KM_CORPORATIVO = 900;
	protected static final double DESCUENTO_PEQ = 0.02;
	protected static final double DESCUENTO_MEDIANAS = 0.1;
	protected static final double DESCUENTO_GRANDES = 0.2;
	
	
	
	@Override
	protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		Ruta ruta = vuelo.getRuta();
		if (cliente.getTipoCliente() == "Corporativo") {
			return COSTO_POR_KM_CORPORATIVO * Aeropuerto.calcularDistancia(ruta.getOrigen(), ruta.getDestino());
		}
		else {
			return Aeropuerto.calcularDistancia(ruta.getOrigen(), ruta.getDestino())*COSTO_POR_KM_NATURAL;
		}
	}

	@Override
	protected double calcularPorcentajeDescuento(Cliente cliente) {
		String typ = cliente.getTipoCliente();
		if (typ.equals("Corporativo")) {
			ClienteCorporativo clienteCorp = (ClienteCorporativo) cliente;
			if (clienteCorp.getTamanoEmpresa()== 1) {
				return DESCUENTO_GRANDES;
			}
			else if (clienteCorp.getTamanoEmpresa()==2) {
				return DESCUENTO_MEDIANAS;
			}
			else if (clienteCorp.getTamanoEmpresa()==3) {
				return DESCUENTO_PEQ;
			}
			else {
				return 0;
			}
		
		}
		else {
			return 0;
		}
		
	}


}
