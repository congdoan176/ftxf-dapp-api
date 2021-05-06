package net.ftxf.dapps.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
	private long id;

	private String email;
	private String password;
	private String name;
	private String mobile;
	private String resetToken;
	private String title;
	private String level;
	private String group = "User";
	private String codeVerify;
	private String role;
	@CreatedDate
	private LocalDateTime created;
	@LastModifiedDate
	private LocalDateTime updated;
	private String address;
	private long sponsor = -1;

	private ArrayList<Long> purchasedCourse = new ArrayList<Long>();

	private String authSecret;

	private long balance = 0;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getCodeVerify() {
		return codeVerify;
	}

	public void setCodeVerify(String codeVerify) {
		this.codeVerify = codeVerify;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public long getSponsor() {
		return sponsor;
	}

	public ArrayList<Long> getPurchasedCourse() {
		return purchasedCourse;
	}

	public void setPurchasedCourse(ArrayList<Long> purchasedCourse) {
		this.purchasedCourse = purchasedCourse;
	}

	public void setSponsor(long sponsor) {
		this.sponsor = sponsor;
	}

	public long getBalance() {
		return balance;
	}

	public String getAuthSecret() {
		return authSecret;
	}

	public void setAuthSecret(String authSecret) {
		this.authSecret = authSecret;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
