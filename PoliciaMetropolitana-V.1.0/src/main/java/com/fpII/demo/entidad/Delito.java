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
@Table(name = "delitos")
public class Delito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976761856492631103L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="fecha_delito")
	private Date fechadelito;
	private int sentencia;
	
	@OneToOne
	@JoinColumn(name = "id_delincuente")
	private Delincuente delincuente;
	
	@OneToOne
	@JoinColumn(name = "id_sucursal")
	private Sucursal sucursal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechadelito() {
		return fechadelito;
	}

	public void setFechadelito(Date fechadelito) {
		this.fechadelito = fechadelito;
	}

	public int getSentencia() {
		return sentencia;
	}

	public void setSentencia(int sentencia) {
		this.sentencia = sentencia;
	}

	public Delincuente getDelincuente() {
		return delincuente;
	}

	public void setDelincuente(Delincuente delincuente) {
		this.delincuente = delincuente;
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
		result = prime * result + ((delincuente == null) ? 0 : delincuente.hashCode());
		result = prime * result + ((fechadelito == null) ? 0 : fechadelito.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + sentencia;
		result = prime * result + ((sucursal == null) ? 0 : sucursal.hashCode());
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
		Delito other = (Delito) obj;
		if (delincuente == null) {
			if (other.delincuente != null)
				return false;
		} else if (!delincuente.equals(other.delincuente))
			return false;
		if (fechadelito == null) {
			if (other.fechadelito != null)
				return false;
		} else if (!fechadelito.equals(other.fechadelito))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sentencia != other.sentencia)
			return false;
		if (sucursal == null) {
			if (other.sucursal != null)
				return false;
		} else if (!sucursal.equals(other.sucursal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Delito [id=" + id + ", fechadelito=" + fechadelito + ", sentencia=" + sentencia + ", delincuente="
				+ delincuente + ", sucursal=" + sucursal + "]";
	}
}