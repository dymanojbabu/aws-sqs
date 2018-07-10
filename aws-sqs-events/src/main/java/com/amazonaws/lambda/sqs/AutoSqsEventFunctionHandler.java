package com.amazonaws.lambda.sqs;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AutoSqsEventFunctionHandler implements RequestHandler<Map<String, Object>, String> {

	@Override
	public String handleRequest(Map<String, Object> input, Context context) {
		context.getLogger().log("Input: " + input);
		String message = "";
		if (input != null) {
			SqsRequest request = new SqsRequest(input);
			try {
				message = request.toJSON();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			System.out.println(message);
		}
		return message;
	}
}
