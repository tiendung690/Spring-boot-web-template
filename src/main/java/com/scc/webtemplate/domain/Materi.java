package com.scc.webtemplate.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materi")
public class Materi implements Serializable {
	
	private static final long serialVersionUID = -4559055049597299398L;
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	public String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	public String description;
	
	public Materi() {}

	public Materi(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
