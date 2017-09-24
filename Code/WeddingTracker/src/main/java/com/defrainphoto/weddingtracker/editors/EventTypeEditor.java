package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.EventTypeManager;

public class EventTypeEditor extends PropertyEditorSupport{
	@Autowired
	EventTypeManager eventTypeManager;
	
	public EventTypeEditor(){}
	public EventTypeEditor(EventTypeManager eventTypeManager){
		setEventTypeManager(eventTypeManager);
	}
	public void setEventTypeManager(EventTypeManager eventTypeManager) {
		this.eventTypeManager = eventTypeManager;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("In the eventType string to object editor");
		System.out.println(text);
		if (StringUtils.hasText(text)) {
			System.out.println("start conversion");
			String[] data = text.split(": ");
			System.out.println(data[0]);
			System.out.println(data[1]);
			EventType temp = new EventType("", data[0], data[1]);
			if (eventTypeManager == null) {
				System.out.println("did not autowire");
			}
			temp = eventTypeManager.getEventTypeByName(temp);
			System.out.println("the found value");
			System.out.println(temp);
//			eventTypeManager.getEventTypeByName(eventType)
			setValue(temp);
		}
	}
	
	@Override
    public String getAsText() {
        EventType eventType = (EventType) getValue();
        if (eventType != null) {
        	System.out.println("In the editor");
        	System.out.println(eventType.toString());
        	
            return eventType.toStringNoId();
        } else {
            return "";
        }
    }
}
