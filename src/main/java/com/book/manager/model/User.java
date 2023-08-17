
package com.book.manager.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "USERS")
public class User extends BaseEntity implements UserDetails {

	/**
	 * 登录名
	 */
	@Column(name = "USERNAME_", length = 60, unique = true)
	String							username;

	/**
	 * 用来显示的用户名
	 */
	@Column(name = "REALNAME_", length = 60)
	String							realname;

	/**
	 * 角色
	 */
	@Column(name = "ROLE_", length = 60)
	String							role;

	/**
	 * 身份证
	 */
	@Column(name = "CARD_", length = 60)
	String							card;

	/**
	 * 性别
	 */
	@Column(name = "SEX_", length = 10)
	String							sex;

	/**
	 * 年龄
	 */
	@Column(name = "AGE_", length = 10)
	Integer							age;

	/**
	 * 手机号
	 */
	@Column(name = "PHONE_", length = 60)
	String							phone;

	/**
	 * 密码
	 */
	@Column(name = "PASSWORD_", length = 60)
	String							password;
	
	
	/**
	 * 用户状态
	 */
	@Column(name = "STATUS_", length = 1)
	String							status;

	/**
	 * 注册时间
	 */
	@Column(name = "REGISTER_TIME_", length = 100)
	String							registerTime;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	// 封装了权限信息
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	// 账户是否未过期
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账户是否未锁定
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 帐户密码是否未过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
