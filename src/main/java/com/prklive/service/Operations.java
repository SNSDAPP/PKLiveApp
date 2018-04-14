package com.prklive.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import sun.misc.BASE64Decoder;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

@Path("Operations")
public class Operations {
	static Logger SNSDLogger = Logger.getLogger(Operations.class.getName());

	/**
	 * SayHello Method
	 * 
	 * @return String
	 */
	@Path("/sayHello")
	@GET
	@Produces("application/json")
	public String sayHello(@HeaderParam("authorization") String authString) {

		SNSDLogger.debug("Inside Method:SayHello");

		JSONObject json = new JSONObject();

		if (!isUserAuthenticated(authString)) {
			json.put("status", "Failure");
			json.put("message", "Not authenticated");
			return json.toString();
		} else {
			try {
				json.put("status", "Success");
				json.put("message", "Valid Method ");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				json.put("status", "Failure");
				json.put("message", "Inside Exception");
				e.printStackTrace();
			}
			return json.toString();
		}
	}

	private boolean isUserAuthenticated(String authString) {
		System.out.println("authString"+authString);
		if (null != authString && authString != "") {
			String decodedAuth = "";
			// Header is in the format "Basic 5tyc0uiDat4"
			// We need to extract data before decoding it back to original
			// string
			String[] authParts = authString.split("\\s+");
			String authInfo = authParts[1];
			// Decode the data back to original string
			byte[] bytes = null;
			try {
				bytes = new BASE64Decoder().decodeBuffer(authInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			decodedAuth = new String(bytes);
			System.out.println("decoded string:"+decodedAuth);

			/**
			 * here you include your logic to validate user authentication. it
			 * can be using ldap, or token exchange mechanism or your custom
			 * authentication mechanism.
			 */
			// your validation code goes here....

			return true;
		} else {
			return false;
		}
	}
}