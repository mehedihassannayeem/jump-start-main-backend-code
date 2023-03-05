package com.jumpstart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_queries")
@NoArgsConstructor
@Setter
@Getter
public class ContactUs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int conid;
	private String name;
	private String email;

	@Lob
	private String queries;

	@Lob
	private String response;
	private String res_at;
	private String respondent;

}
