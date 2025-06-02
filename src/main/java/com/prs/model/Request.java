package com.prs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

//id,user ID,Description, justification, dateNeeded, deliveryMode,status
	private int Id;
	//@Column(name = "request_number", nullable = false, unique = true)
	   // private String requestNumber; // Ensure this matches database column


	private int userId;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private  String status;
	private  double  total;//double?
	private LocalDateTime submittedDate;
	private String reasonForRejection;
	private String requestNumber;
	
	public Request() {
		super();
	}

	public Request(int id, int userId, String description, String justification, LocalDate dateNeeded,
			String deliveryMode,String requestNumber, String status, double total, LocalDateTime submittedDate, String reasonForRejection) {
		super();
		Id = id;
		this.userId = userId;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		this.requestNumber = requestNumber;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	@Override
	public String toString() {
		return "Request [Id=" + Id + ", userId=" + userId + ", description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status
				+ ", total=" + total + ", submittedDate=" + submittedDate + ", reasonForRejection=" + reasonForRejection
				+ ", requestNumber=" + requestNumber
				+ "]";
	}

	public String getRequestNumber() {
		return this.requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		// TODO Auto-generated method stub
		this.requestNumber = requestNumber;
	}
	
 
 	}
	
