package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.EventType;
import com.defrainphoto.weddingtracker.model.Timeline;
import com.defrainphoto.weddingtracker.model.TimelineManager;

public class TimelineEditor  extends PropertyEditorSupport{
	private TimelineManager timelineManager = new TimelineManager();
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split(" ");
			Timeline timeline = new Timeline(data[0], null, null, null, null);
			timeline = timelineManager.getTimelineByEventId(timeline);
			setValue(timeline);
			System.out.println("Here in it");
			System.out.println(timeline);
//			setValue(new Timeline(data[0], data[1], data[2]));
		}
	}
	
	@Override
    public String getAsText() {
		Timeline timeline = (Timeline) getValue();
        if (timeline != null) {
        	System.out.println("yep");
            return timeline.toString();
        } else {
            return "";
        }
    }
}
