package com.fpII.demo.entidad;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jueces")
public class Juez implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9091957868214556746L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date inicio_actividad;
	private int anios;
	
	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInicio_actividad() {
		return inicio_actividad;
	}

	public void setInicio_actividad(Date inicio_actividad) {
		this.inicio_actividad = inicio_actividad;
	}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anios;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio_actividad == null) ? 0 : inicio_actividad.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Juez other = (Juez) obj;
		if (anios != other.anios)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicio_actividad == null) {
			if (other.inicio_actividad != null)
				return false;
		} else if (!inicio_actividad.equals(other.inicio_actividad))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Juez [id=" + id + ", inicio_actividad=" + inicio_actividad + ", anios=" + anios
				+ ", persona=" + persona + "]";
	}
}
