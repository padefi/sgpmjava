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
@Table(name = "sentencias")
public class Sentencia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 667948279342276274L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="fecha_inicio")
	private Date fechainicio;
	@Column(name="fecha_fin")
	private Date fechafin;
	
	@OneToOne
	@JoinColumn(name = "id_delito")
	private Delito delito;
	
	@OneToOne
	@JoinColumn(name = "id_juez")
	private Juez juez;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public Delito getDelito() {
		return delito;
	}

	public void setDelito(Delito delito) {
		this.delito = delito;
	}

	public Juez getJuez() {
		return juez;
	}

	public void setJuez(Juez juez) {
		this.juez = juez;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delito == null) ? 0 : delito.hashCode());
		result = prime * result + ((fechafin == null) ? 0 : fechafin.hashCode());
		result = prime * result + ((fechainicio == null) ? 0 : fechainicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((juez == null) ? 0 : juez.hashCode());
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
		Sentencia other = (Sentencia) obj;
		if (delito == null) {
			if (other.delito != null)
				return false;
		} else if (!delito.equals(other.delito))
			return false;
		if (fechafin == null) {
			if (other.fechafin != null)
				return false;
		} else if (!fechafin.equals(other.fechafin))
			return false;
		if (fechainicio == null) {
			if (other.fechainicio != null)
				return false;
		} else if (!fechainicio.equals(other.fechainicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (juez == null) {
			if (other.juez != null)
				return false;
		} else if (!juez.equals(other.juez))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sentencia [id=" + id + ", fechainicio=" + fechainicio + ", fechafin=" + fechafin + ", delito=" + delito
				+ ", juez=" + juez + "]";
	}
}
