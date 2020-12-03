package com.sales.online.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank(message = "Rellenar el campo nombre")
  private String name;

  @NotBlank(message = "Rellenar el campo de dirección")
  private String email;
  
  @NotBlank(message = "Rellenar el campo contraseña")
  private String password;
  
  @NotBlank(message = "No hay coincidencia en las contraseñas")
  @Column(insertable = false,updatable = false)
  private String confirmPassword;

  @NotBlank(message = "Rellenar el campo correo")
  private String address;

  public User() {}

  public User(int id, String name, String email, String password,String address) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.password=password;
    this.address=address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, email, id, name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof User)) {
      return false;
    }
    User other = (User) obj;
    return Objects.equals(address, other.address)
        && Objects.equals(email, other.email)
        && id == other.id
        && Objects.equals(name, other.name);
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
  }
}
