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
@Table(name = "vigilantes")
public class Vigilante implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3457739237236622724L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date fecha_nacimiento;
	private int arma;
	private int edad;
	
	@OneToOne
	@JoinColumn(name = "id_vigilante")
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public int getArma() {
		return arma;
	}

	public void setArma(int arma) {
		this.arma = arma;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arma;
		result = prime * result + edad;
		result = prime * result + ((fecha_nacimiento == null) ? 0 : fecha_nacimiento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Vigilante other = (Vigilante) obj;
		if (arma != other.arma)
			return false;
		if (edad != other.edad)
			return false;
		if (fecha_nacimiento == null) {
			if (other.fecha_nacimiento != null)
				return false;
		} else if (!fecha_nacimiento.equals(other.fecha_nacimiento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vigilante [id=" + id + ", fecha_nacimiento=" + fecha_nacimiento + ", arma=" + arma + ", edad=" + edad
				+ ", usuario=" + usuario + "]";
	}
}