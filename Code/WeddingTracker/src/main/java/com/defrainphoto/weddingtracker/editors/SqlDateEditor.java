package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

import org.springframework.util.StringUtils;

public class SqlDateEditor extends PropertyEditorSupport{
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split("/");
			Date newDate = null;
			if (data.length == 3) {
				newDate = Date.valueOf(data[2] + "-" + data[0] + "-" + data[1]);
			}
			
			setValue(newDate);
		}
	}
	
	@Override
    public String getAsText() {
        Date sqlDate = (Date) getValue();
        String result = null;
        String[] data = null;
        int yearIndex = 0, monthIndex = 1, dayIndex = 2;
        
        if (sqlDate != null) {
        	data = sqlDate.toString().split("-");    
        	if (data.length < 3) {
        		data = sqlDate.toString().split("/");
        		yearIndex = 2;
        		monthIndex = 0;
        		dayIndex = 1;
        	}
        }
        
        if (data != null && data.length == 3) {
    		result = data[monthIndex] + "/" + data[dayIndex] + "/" + data[yearIndex];
        }
        else {
            result = "";
        }
        return result;
    }
}