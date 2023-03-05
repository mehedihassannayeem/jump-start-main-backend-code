package com.jumpstart.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@Setter
@Getter
@ToString
public class Role {

	@Id
	private int rid;

	private String name;

}
