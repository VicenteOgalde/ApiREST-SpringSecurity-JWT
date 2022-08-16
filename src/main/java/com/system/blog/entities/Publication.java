package com.system.blog.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="publications",uniqueConstraints =
{@UniqueConstraint(columnNames = "title")})//constraint for not to repeat a same name
public class Publication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="title",nullable = false)
	private String title;
	@Column(name="description",nullable = false)
	private String description;
	@Column(name="content", nullable = false)
	private String content;
	
	@OneToMany(mappedBy = "publication", cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<Comment> comments;

}
