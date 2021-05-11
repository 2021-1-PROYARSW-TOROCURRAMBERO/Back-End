package edu.eci.arsw.quickmobility.services;

import edu.eci.arsw.quickmobility.model.*;
import edu.eci.arsw.quickmobility.persistence.QuickMobilityException;
import edu.eci.arsw.quickmobility.persistence.QuickmobilityPersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuickMobilityServices extends UserServices {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	QuickmobilityPersistence quickmobilityPersistence;

	public String helloWorld() {
		return "Hello World Hola Mundo";
	}

	public List<Carro> getCarros(String username) throws Exception {
		return quickmobilityPersistence.getUserByUsername(username).getCarros();
	}

	public void addCarroUsuario(String user, Carro carro) throws Exception {
		Usuario usuario = quickmobilityPersistence.getUserByUsername(user);
		usuario.addCarros(carro);
		quickmobilityPersistence.updateUser(usuario);
	}

	public List<Barrio> getBarrios() {
		return quickmobilityPersistence.getBarrio();
	}

	public void addBarrio(Barrio barrio) throws Exception {
		quickmobilityPersistence.addBarrio(barrio);
	}

	public void addCalificacion(String nameConductor, String namePasajero, double calificacion) throws QuickMobilityException {
		quickmobilityPersistence.addCalificacion(nameConductor, namePasajero, calificacion);
	}

	public void updateCarro(Carro carro, Usuario usuario) throws Exception {
		quickmobilityPersistence.updateCarro(carro, usuario);
	}

	public String getUserStatus(String username) throws QuickMobilityException {
		Usuario user = quickmobilityPersistence.getUserByUsername(username);
		String status = "Ninguno";
		if (user.viajesPasajero.size() > 0 || user.viajesConductor.size() > 0) {
			if (!user.viajesPasajero.get(user.viajesPasajero.size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Pasajero";
			} else if (!user.viajesConductor.get(user.viajesConductor.size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Conductor";
			}
		}
		return status;
	}

	public double getAverage(String username, String type) throws QuickMobilityException {
		Usuario usuario = quickmobilityPersistence.getUserByUsername(username);
		double valueToReturn = 0;
		int totalScore = 0;
		if (type.equals("Conductor") && usuario.viajesConductor.size() > 0) {
			for (Conductor c : usuario.viajesConductor) {
				if (c.estado.equals(Estado.Finalizado)) {
					valueToReturn += c.calificacion.valor;
					totalScore += 1;
				}
			}
		} else if(type.equals("Pasajero") && usuario.viajesPasajero.size()>0) {
			for (Pasajero p : usuario.viajesPasajero) {
				if (p.estado.equals(Estado.Finalizado)) {
					valueToReturn += p.calificacion.valor;
					totalScore += 1;
				}
			}
		}
		valueToReturn = valueToReturn / totalScore;

		return valueToReturn;
	}

	public void updateUserBasicInfo(Usuario user) throws QuickMobilityException {
		
		Usuario oldUserWithBasicChanges = getUserByUsername(user.username);
		

		if (user.password != null) {
			String pwd = user.password;
			String encrypt = bCryptPasswordEncoder.encode(pwd);
			oldUserWithBasicChanges.setPassword(encrypt);
		};
		
		oldUserWithBasicChanges.setNombreCompleto(user.nombreCompleto);
		oldUserWithBasicChanges.setDireccionResidencia(user.direccionResidencia);
		oldUserWithBasicChanges.setEmail(user.email);
		oldUserWithBasicChanges.setNumero(user.numero);
		
		quickmobilityPersistence.updateUser(oldUserWithBasicChanges);
	}

	public List<Pasajero> solicitudDeViajePasajero(InformacionPasajero infoPasajero, String usernameConductor)
			throws QuickMobilityException {
		Usuario pasajero = getUserByUsername(infoPasajero.getPasajeroUsername());
		Usuario conductor = getUserByUsername(usernameConductor);
		Conductor viajeConductor = null;

		for (int i = 0; i < conductor.viajesConductor.size(); i++) {
			if (conductor.viajesConductor.get(i).estado.equals(Estado.Disponible)) {

				viajeConductor = conductor.viajesConductor.get(i);
				break;
			}
		}
		Pasajero viajePasajero = new Pasajero();
		viajePasajero.estado = Estado.Disponible;
		viajePasajero.username = pasajero.username;
		viajePasajero.direccionRecogida = infoPasajero.getDireccion();
		if(viajeConductor.posiblesPasajeros == null){
			viajeConductor.setPosiblesPasajeros(new ArrayList<>());
		}
		viajeConductor.posiblesPasajeros.add(viajePasajero);
		pasajero.viajesPasajero.add(viajePasajero);
		quickmobilityPersistence.updateUser(pasajero);
		quickmobilityPersistence.updateUser(conductor);
		return viajeConductor.posiblesPasajeros;

	}

	public List<Conductor> getConductoresDisponibles(){
		return quickmobilityPersistence.getConductoresDisponibles();
	}
	public List<Conductor> getConductoresDisponibles(Viaje travel, String conducNombre)
			throws QuickMobilityException {
		Usuario usuario = getUserByUsername(conducNombre);
		Conductor conductor = new Conductor();

		conductor.setEstado(Estado.Disponible);
		conductor.setPrecio(travel.getPrecio());
		conductor.setDireccionInicio(travel.getOrigen());
		conductor.setDireccionFin(travel.getDestino());

		List<Carro> carros = usuario.getCarros();
		for (Carro c : carros) {
			if (c.getPlaca().equals(travel.getCarro())) {
				conductor.setCarro(c);
				break;
			}
		}
		for(Conductor drivers : usuario.getViajesConductor()){
			if (drivers.getEstado().equals(Estado.Disponible)) {
				throw new QuickMobilityException(QuickMobilityException.DRIVER_NOT_AVAILABLE);
			}
		}
		usuario.viajesConductor.add(conductor);
		quickmobilityPersistence.updateUser(usuario);
		return quickmobilityPersistence.getConductoresDisponibles();
	}

	public JSONObject aceptarORechazarPasajero(NuevoEstado info, String usernamePasajero) throws QuickMobilityException {
		Usuario usuarioPasajero = getUserByUsername(usernamePasajero);
		Usuario usuarioConductor = getUserByUsername(info.getConductorUsername());
		boolean estado = info.getEstado();
		Pasajero pasajero = null;
		for (Pasajero p : usuarioPasajero.viajesPasajero) {
			if (p.estado.equals(Estado.Disponible)) {
				pasajero = p;
				break;
			}
		}
		Conductor conductor = null;
		for (Conductor c : usuarioConductor.viajesConductor) {
			if (c.estado.equals(Estado.Disponible)) {
				conductor = c;
				for (int i = 0; i < c.posiblesPasajeros.size(); i++) {
					if (c.posiblesPasajeros.get(i).username.equals(pasajero.username)) {
						c.posiblesPasajeros.remove(i);
						break;
					}
				}
				break;
			}
		}
		pasajero.conductor = conductor;
		if (estado) {
			pasajero.estado = Estado.Aceptado;
			for (Conductor c : quickmobilityPersistence.getConductoresDisponibles()) {
				for (int i = 0; i < c.posiblesPasajeros.size(); i++) {
					if (c.posiblesPasajeros.get(i).username.equals(usernamePasajero)) {
						c.posiblesPasajeros.remove(i);
						break;
					}
				}
			}
		} else {
			pasajero.estado = Estado.Rechazado;
		}
		quickmobilityPersistence.updateUser(usuarioConductor);
		quickmobilityPersistence.updateUser(usuarioPasajero);
		JSONObject jsonADevolver = new JSONObject(conductor);
		jsonADevolver.put("estadoPasajero", pasajero.estado);
		return jsonADevolver;

	}

	public Conductor estadoConductor(Estado estado, String usernameConductor) throws QuickMobilityException{
		Usuario driverUser = quickmobilityPersistence.getUserByUsername(usernameConductor);
		Conductor driver = null;
		for(Conductor c: driverUser.viajesConductor){
			if(c.estado.equals(Estado.Disponible)){
				driver = c;
				break;
			}
		}
		driver.setEstado(estado);
		quickmobilityPersistence.updateUser(driverUser);
		return driver;
	}

	public Pasajero estadoPasajero(Estado estado,String usernamePasajero) throws QuickMobilityException {
		Usuario usuarioPasajero = quickmobilityPersistence.getUserByUsername(usernamePasajero);
		Pasajero pasajero = null;
		for (Pasajero p : usuarioPasajero.viajesPasajero) {
			if (p.estado.equals(Estado.Disponible) || p.estado.equals(Estado.Aceptado)) {
				pasajero = p;
				break;
			}
		}
		pasajero.setEstado(estado);
		quickmobilityPersistence.updateUser(usuarioPasajero);
		return pasajero;
	}

	public void finishTravel(String driverName, List<Pasajero> passengers) throws QuickMobilityException {
		estadoConductor(Estado.Finalizado,driverName);
		for(Pasajero p: passengers){
			estadoPasajero(Estado.Finalizado,p.username);
		}

	}

	public Conductor getTravelDriver(String usernameDriver) throws QuickMobilityException {
		Usuario driverUser = quickmobilityPersistence.getUserByUsername(usernameDriver);
		Conductor driver = null;
		for (Conductor c: driverUser.getViajesConductor()){
			if(c.getEstado().equals(Estado.Disponible)){
				driver = c;
				break;
			}
		}

		return driver;
	}

	public Pasajero getTravelPassenger(String usernamePassenger) throws QuickMobilityException {
		Usuario passengerUser = quickmobilityPersistence.getUserByUsername(usernamePassenger);
		Pasajero passenger = null;
		for(Pasajero p:passengerUser.getViajesPasajero()){
			if(p.getEstado().equals(Estado.Aceptado)){
				passenger = p;
				break;
			}
		}
		return passenger;
	}
}
