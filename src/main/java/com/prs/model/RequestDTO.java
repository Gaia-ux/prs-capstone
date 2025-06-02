package com.prs.model;

import java.time.LocalDate;

//@Entity
public class RequestDTO {

//@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int userId;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	
	public RequestDTO() {
		super();
	}

	public RequestDTO(int userId, String description, String justification, LocalDate dateNeeded, String deliveryMode) {
		super();
		this.userId = userId;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@Override
	public String toString() {
		return "RequestDTO [userId=" + userId + ", description=" + description + ", justification=" + justification
				+ ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + "]";
	}
	 
	}

	 

