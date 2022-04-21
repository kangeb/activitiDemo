package com.example.activitiBase.domain;

import java.io.Serializable;

public class User implements Serializable{
	private String uuid;
	private String name;
	private String password;
	private String phone;
	private String email;
	private String remark;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", name=" + name + ", password=" + password + ", phone=" + phone + ", email="
				+ email + ", remark=" + remark + "]";
	}
	

}
