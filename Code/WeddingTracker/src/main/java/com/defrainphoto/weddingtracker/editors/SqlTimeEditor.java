package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;
import java.sql.Time;

import org.springframework.util.StringUtils;

public class SqlTimeEditor extends PropertyEditorSupport{
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split(" ");
			String[] hrMin = data[0].split(":");
			
			if (data[1].equalsIgnoreCase("PM")) {
				if (!hrMin[0].equals("12")) {
					hrMin[0] = "" + (Integer.parseInt(hrMin[0]) + 12);
				}
			}
			
			else {
				if (hrMin[0].equals("12")) {
					hrMin[0] = "00";
				}
			}
			text = hrMin[0] + ":" + hrMin[1] + ":00";
			Time t = Time.valueOf(text);
			setValue(Time.valueOf(text));
		}
	}
	
	@Override
    public String getAsText() {
        Time sqlTime = (Time) getValue();
        String result = null;
        String[] data = null;
        
        if (sqlTime != null) {
        	data = sqlTime.toString().split(":");    
        	if (data.length < 3) {
        		data = sqlTime.toString().split(" ");
        	}
        }
        
        if (data != null) {
        	if (data.length == 1) {
        		result = data[0] + ":00:00";
        	}
        	
        	else if (data.length == 2) {
        		result = data[0] + data[1] + ":00";
        	}
        	else {
        		result = sqlTime.toString();
        		data = sqlTime.toString().split(":");
        		data[2] = "AM";
        		if (Integer.parseInt(data[0]) >= 12) {
        			data[2] = "PM";
        			data[0] = "" + (Integer.parseInt(data[0]) - 12);
        		}
        		else if (data[0].equals("00")) {
        			data[0] = "12";
        		}
        		result = data[0] + ":" + data[1] + " " + data[2];
        	}
        }
        else {
            result = "00:00 AM";
        }
        return result;
    }
}
