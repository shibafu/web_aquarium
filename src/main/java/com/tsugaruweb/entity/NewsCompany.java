//package com.tsugaruweb.entity;
//
//import java.io.Serializable;
//import javax.persistence.*;
//
//
///**
// * The persistent class for the news_company database table.
// * 
// */
//@Entity
//@Table(name="news_company")
//@NamedQuery(name="NewsCompany.findAll", query="SELECT n FROM NewsCompany n")
//public class NewsCompany implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@Column(name="company_id")
//	private Integer companyId;
//
//	@Column(name="company_name")
//	private String companyName;
//
//	public NewsCompany() {
//	}
//
//	public Integer getCompanyId() {
//		return this.companyId;
//	}
//
//	public void setCompanyId(Integer companyId) {
//		this.companyId = companyId;
//	}
//
//	public String getCompanyName() {
//		return this.companyName;
//	}
//
//	public void setCompanyName(String companyName) {
//		this.companyName = companyName;
//	}
//
//}