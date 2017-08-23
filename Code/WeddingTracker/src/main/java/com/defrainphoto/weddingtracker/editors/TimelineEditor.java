package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.EventType;

public class TimelineEditor  extends PropertyEditorSupport{
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split(" ");
			
//			setValue(new Timeline(data[0], data[1], data[2]));
		}
	}
	
	@Override
    public String getAsText() {
        EventType eventType = (EventType) getValue();
        if (eventType != null) {
            return eventType.toString();
        } else {
            return "";
        }
    }
}
