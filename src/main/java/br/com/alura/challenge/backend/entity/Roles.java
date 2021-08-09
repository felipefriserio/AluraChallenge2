package br.com.alura.challenge.backend.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
	
}