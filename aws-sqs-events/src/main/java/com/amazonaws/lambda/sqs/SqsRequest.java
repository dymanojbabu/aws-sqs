/**
 * 
 */
package com.amazonaws.lambda.sqs;

import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SqsRequest {
	private String body;
	private String type;
	private String receiptHandle;

	public SqsRequest(Map<String, Object> input) {
		JSONObject requestJSON = new JSONObject(input);
		JsonObject jsonObject = new Gson().fromJson(requestJSON.toString(), JsonObject.class);
		this.body = getBody(jsonObject);
		this.type = getType(jsonObject);
		this.receiptHandle = getReceiptHandle(jsonObject);
	}

	private String getReceiptHandle(JsonObject jsonObject) {
		return jsonObject.getAsJsonArray("Records").get(0).getAsJsonObject().get("receiptHandle").toString()
				.replaceAll("^\"|\"$", "");
	}

	private String getType(JsonObject jsonObject) {
		JsonObject meesagAttJsonObject = jsonObject.getAsJsonArray("Records").get(0).getAsJsonObject()
				.get("messageAttributes").getAsJsonObject();

		return meesagAttJsonObject != null && meesagAttJsonObject.has(type) ? meesagAttJsonObject.get("type")
				.getAsJsonObject().get("stringValue").toString().replaceAll("^\"|\"$", "") : "";
	}

	private String getBody(JsonObject jsonObject) {
		return jsonObject.getAsJsonArray("Records").get(0).getAsJsonObject().get("body").toString()
				.replaceAll("^\"|\"$", "");
	}

	public SqsRequest(String body, String type) {
		this.body = body;
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiptHandle() {
		return receiptHandle;
	}

	public void setReceiptHandle(String receiptHandle) {
		this.receiptHandle = receiptHandle;
	}
	
	
	public String toJSON() throws JsonProcessingException {
		String json = "{}";
			json = new ObjectMapper().writeValueAsString(this);
		return json;
	}

}
