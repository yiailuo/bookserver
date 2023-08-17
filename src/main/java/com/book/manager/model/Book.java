
package com.book.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BOOK")
public class Book extends BaseEntity {

	/**
	 * 书名
	 */
	@Column(name = "NAME_", length = 60)
	String							name;

	/**
	 * 所属分类
	 */
	@Column(name = "TYPE_ID_", length = 60)
	String							typeid;

	/**
	 * 所属书架
	 */
	@Column(name = "BOOKSELF_ID_", length = 60)
	String							bookselfid;

	/**
	 * 出版社
	 */
	@Column(name = "PUBLISHER_", length = 60)
	String							publisher;
	
	/**
	 * 作者
	 */
	@Column(name = "AUTHOR_", length = 60)
	String							author;


	/**
	 * 价格
	 */
	@Column(name = "PRICE_", length = 10)
	String							price;

	/**
	 * 借阅量
	 */
	@Column(name = "BORROW_", length = 10)
	Integer							borrow;

	/**
	 * 库存
	 */
	@Column(name = "STOCK_", length = 10)
	Integer							stock;

	/**
	 * 分类名称
	 */
	@Transient
	private String typename;

	/**
	 * 书架号
	 */
	@Transient
	private String bookselfname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getBorrow() {
		return borrow;
	}

	public void setBorrow(Integer borrow) {
		this.borrow = borrow;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getBookselfid() {
		return bookselfid;
	}

	public void setBookselfid(String bookselfid) {
		this.bookselfid = bookselfid;
	}

	public String getBookselfname() {
		return bookselfname;
	}

	public void setBookselfname(String bookselfname) {
		this.bookselfname = bookselfname;
	}
}
