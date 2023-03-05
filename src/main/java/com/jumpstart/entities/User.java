package com.jumpstart.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Column(nullable = false)
	private String name;

	private boolean localImage = false;
	private String imageUrl;

	private String dob;
	private String gender;
	private String phnum;
	private String nid;

	private String street;
	private String state;
	private String city;
	private String country;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "uid"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "rid"))
	private Set<Role> roles = new HashSet<>();

	/*
	 * @OneToMany(mappedBy = "postedUser", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY)
	 * 
	 * @JsonManagedReference private List<Post> posts = new ArrayList<>();
	 * 
	 * @OneToMany(mappedBy = "commentedUser", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY)
	 * 
	 * @JsonManagedReference private List<Comment> comments = new ArrayList<>();
	 */

}
