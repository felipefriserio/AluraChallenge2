package br.com.alura.challenge.backend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authorities")
public class Roles implements Serializable {

	@Id
	@Column(name = "username",length=100)
	private String username;
	
	@Id
	@Column(name = "authority",length=100)
	private String authority; ;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Roles roles = (Roles) o;
		return Objects.equals(username, roles.username) && Objects.equals(authority, roles.authority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, authority);
	}
}