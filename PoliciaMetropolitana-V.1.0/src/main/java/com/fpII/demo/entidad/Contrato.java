package com.fpII.demo.entidad;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contratos")
public class Contrato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8407861393975005029L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="fecha_contrato")
	private Date fechacontrato;
	
	@OneToOne
	@JoinColumn(name = "id_vigilante")
	private Vigilante vigilante;
	
	@OneToOne
	@JoinColumn(name = "id_sucursal")
	private Sucursal sucursal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechacontrato() {
		return fechacontrato;
	}

	public void setFechacontrato(Date fechacontrato) {
		this.fechacontrato = fechacontrato;
	}

	public Vigilante getVigilante() {
		return vigilante;
	}

	public void setVigilante(Vigilante vigilante) {
		this.vigilante = vigilante;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechacontrato == null) ? 0 : fechacontrato.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
		result = prime * result + ((vigilante == null) ? 0 : vigilante.hashCode());
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
		Contrato other = (Contrato) obj;
		if (fechacontrato == null) {
			if (other.fechacontrato != null)
				return false;
		} else if (!fechacontrato.equals(other.fechacontrato))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		if (vigilante == null) {
			if (other.vigilante != null)
				return false;
		} else if (!vigilante.equals(other.vigilante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contrato [id=" + id + ", fechacontrato=" + fechacontrato + ", vigilante=" + vigilante + ", sucursal="
				+ sucursal + "]";
	}
}