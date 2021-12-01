package com.fpII.demo.entidad;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sucursales")
public class Sucursal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6316321435321267199L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String domicilio;
	private int cant_empleados;
	
	@ManyToOne
	@JoinColumn(name = "id_entidad")
	private EntidadBancaria entidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public int getCant_empleados() {
		return cant_empleados;
	}

	public void setCant_empleados(int cant_empleados) {
		this.cant_empleados = cant_empleados;
	}

	public EntidadBancaria getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadBancaria entidad) {
		this.entidad = entidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cant_empleados;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((domicilio == null) ? 0 : domicilio.hashCode());
		result = prime * result + ((entidad == null) ? 0 : entidad.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Sucursal other = (Sucursal) obj;
		if (cant_empleados != other.cant_empleados)
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (domicilio == null) {
			if (other.domicilio != null)
				return false;
		} else if (!domicilio.equals(other.domicilio))
			return false;
		if (entidad == null) {
			if (other.entidad != null)
				return false;
		} else if (!entidad.equals(other.entidad))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sucursal [id=" + id + ", descripcion=" + descripcion + ", domicilio=" + domicilio + ", cant_empleados="
				+ cant_empleados + ", entidad=" + entidad + "]";
	}
}