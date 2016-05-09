/**
 * 
 */
package com.aaronheath.register.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author aheath
 *
 */
public class JSONUtils {
	
	private static Logger logger = Logger.getLogger(JSONUtils.class);

	public static String toJson(Object object) {
		String jsonStr = null;
		if (object != null) {
			try {
				jsonStr = JSONSerializer.toJSON(object).toString();
						
			} catch (Exception ex) {
				logger.error("Error converting object to json: ", ex);
				return null;
			}
		}
		logger.debug(jsonStr);
		return jsonStr;
	}
	
	public static JSONArray toJSONArray(String jsonStr) {
		logger.debug("toJSONArray ");
		JSONArray jsonArray = null;
		if (StringUtils.isNotEmpty(jsonStr)) {
			try {
				jsonArray = JSONArray.fromObject(jsonStr);
			}
			catch (Exception ex) {
				logger.error("Error converting to JSONObject: ", ex);
			}
		}
		
		return jsonArray;
	}


}
