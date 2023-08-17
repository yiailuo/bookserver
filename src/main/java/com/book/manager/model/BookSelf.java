
package com.book.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BOOKSELF")
public class BookSelf extends BaseEntity {

	/**
	 * 书架名
	 */
	@Column(name = "NAME_", length = 60, unique = true)
	String							name;

	/**
	 * 剩余容量
	 */
	@Column(name = "CAPACITY_", length = 60)
	Integer							capacity;

	/**
	 * 已用容量
	 */
	@Column(name = "USES_", length = 60)
	Integer							uses;

	@Transient
	private String capacity1;
	@Transient
	private String capacity2;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getUses() {
		return uses;
	}

	public void setUses(Integer uses) {
		this.uses = uses;
	}

	public String getCapacity1() {
		return capacity1;
	}

	public void setCapacity1(String capacity1) {
		this.capacity1 = capacity1;
	}

	public String getCapacity2() {
		return capacity2;
	}

	public void setCapacity2(String capacity2) {
		this.capacity2 = capacity2;
	}
}
