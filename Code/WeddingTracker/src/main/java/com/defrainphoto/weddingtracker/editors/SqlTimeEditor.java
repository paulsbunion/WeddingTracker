package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;
import java.sql.Time;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.EventType;

public class SqlTimeEditor extends PropertyEditorSupport{
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			
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
        	}
        }
        else {
            result = "00:00:00";
        }
        
        return result;
    }
}
