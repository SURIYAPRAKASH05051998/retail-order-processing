package com.retail.order.utils;

public enum HttpResponse {

	CREATED(201, "Data Created Successfully"), SUCCESS(200, "Success"), NO_DATA(204, "No Data Found"),
	INVALID(500, "Something went wrong"), INSUFFICIENT_STOCK(203, "Insufficient Stock"),
	INVALID_PRODUCT(205, "Invalid Product"), INPUT_INVALID(202, "Invalid Input");

	private HttpResponse(int statusCode, String statusMesssage) {
		this.statusCode = statusCode;
		this.statusMesssage = statusMesssage;
	}

	public int statusCode;
	public String statusMesssage;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMesssage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMesssage = statusMessage;
	}

}
