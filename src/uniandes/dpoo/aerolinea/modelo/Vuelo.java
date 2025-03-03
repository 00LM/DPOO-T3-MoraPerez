package uniandes.dpoo.aerolinea.modelo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.modelo.cliente.*;
import uniandes.dpoo.aerolinea.tiquetes.*;

public class Vuelo {
	private Ruta ruta; 
	private String fecha; 
	private Avion avion; 
	private Map<String,Tiquete> tiquetes;
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) {
		this.ruta = ruta;
		this.fecha = fecha;
		this.avion = avion;
		this.tiquetes = new HashMap<String,Tiquete>();
	}
	
	public Ruta getRuta() {
		return ruta;
	}
	public String getFecha() {
		return fecha;
	}
	public Avion getAvion() {
		return avion;
	}
	
	public Collection<Tiquete> getTiquetes() {
	    Collection<Tiquete> listaTiquetes = new ArrayList<>();
	    for (String elemento : this.tiquetes.keySet()) {
	        listaTiquetes.add(this.tiquetes.get(elemento)); 
	    }
	    return listaTiquetes; 
	}

	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) throws VueloSobrevendidoException {
		int tarifa = calculadora.calcularTarifa(this, cliente);
		int tot = 0;
		for (int i=0; i<cantidad; i++ ) {
			Tiquete tiquete =GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
			boolean v = false;
			while (v ==false){
			if (GeneradorTiquetes.validarTiquete(tiquete.getCodigo())!= true) {
				GeneradorTiquetes.registrarTiquete(tiquete);
				tiquetes.put(tiquete.getCodigo(), tiquete);
				cliente.agregarTiquete(tiquete);
				v = true;
				}
			else {
				 tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
				}
			}
			tot += tiquete.getTarifa();
		}
		if (tiquetes.size()>=avion.getCapacidad()) {
			throw new VueloSobrevendidoException(this);
		}
		else {
			return tot;
		}
	}

}
