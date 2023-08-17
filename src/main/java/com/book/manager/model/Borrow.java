
package com.book.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BORROW")
public class Borrow extends BaseEntity{

	/**
	 * 借阅人id
	 */
	@Column(name = "USERID_", length = 60)
	String							userid;

	/**
	 * 图书id
	 */
	@Column(name = "BOOKID_", length = 60)
	String							bookid;

	/**
	 * 借出日期
	 */
	@Column(name = "BORROWDATE_", length = 60)
	String							borrowdate;

	/**
	 * 计划归还日期
	 */
	@Column(name = "PLANRETURNDATE_", length = 60)
	String							planreturndate;

	/**
	 * 实际归还日期
	 */
	@Column(name = "REALITYRETURNDATE_", length = 60)
	String							realityreturndate;

	/**
	 * 读者姓名
	 */
	@Transient
	private String username;

	/**
	 * 读者身份证
	 */
	@Transient
	private String card;

	/**
	 * 读者身份证（查询读者借阅记录使用）
	 */
	@Transient
	private String usercard;

	/**
	 * 读者性别
	 */
	@Transient
	private String sex;

	/**
	 * 读者年龄
	 */
	@Transient
	private Integer age;

	/**
	 * 读者电话
	 */
	@Transient
	private String phone;

	/**
	 * 书名
	 */
	@Transient
	private String bookname;

	/**
	 * 出版社
	 */
	@Transient
	private String publisher;

	/**
	 * 价格
	 */
	@Transient
	private String price;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getBorrowdate() {
		return borrowdate;
	}

	public void setBorrowdate(String borrowdate) {
		this.borrowdate = borrowdate;
	}

	public String getPlanreturndate() {
		return planreturndate;
	}

	public void setPlanreturndate(String planreturndate) {
		this.planreturndate = planreturndate;
	}

	public String getRealityreturndate() {
		return realityreturndate;
	}

	public void setRealityreturndate(String realityreturndate) {
		this.realityreturndate = realityreturndate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsercard() {
		return usercard;
	}

	public void setUsercard(String usercard) {
		this.usercard = usercard;
	}
}
