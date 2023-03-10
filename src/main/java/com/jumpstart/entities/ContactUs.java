package com.jumpstart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
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
	@Column(name = "query_sl_no", unique = true)
	private String conid;
	private String name;
	private String email;
	private String invoice;

	@Lob
	private String queries;

	@Lob
	private String response;
	private String resAt;

	@OneToOne
	private User respondent;

}
