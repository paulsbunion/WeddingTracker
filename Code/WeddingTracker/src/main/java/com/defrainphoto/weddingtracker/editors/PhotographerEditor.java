package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.Photographer;
import com.defrainphoto.weddingtracker.model.PhotographerManager;

public class PhotographerEditor  extends PropertyEditorSupport{
	
	PhotographerManager photographerManager = new PhotographerManager();
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			
			Set<Photographer> photographerSet = new HashSet<Photographer>();
			System.out.println("the data to convert");
			System.out.println(text);
			System.out.println("before split");
			String[] data = text.split(",");
			System.out.println("after split");
			if (data == null) {
				System.out.println("data is null");
			}
			System.out.println("goop");
			System.out.println(data.length);
			
			if (text.length() > 1) {
				System.out.println("Poop");
			}
			// handle when only one photographer exists
			if (data.length <= 1 && text.length() > 1) {
				System.out.println("before here");
				data = new String [1];
				System.out.println("in here");
				data[0] = text.toString().substring(0, text.length());
			}
			
			System.out.println("not here");
			System.out.println(data[0]);
			String[] photographerData;
			if (data != null && data.length > 0) {
				for (int i = 0; i < data.length; i++) {
					photographerData = data[i].split(" ");
					Photographer photographer = new Photographer("", photographerData[0], photographerData[1]);
					photographer = photographerManager.getPhotographerByName(photographer);
					photographerSet.add(photographer);
				}
				System.out.println("added all to the set");
				System.out.println(photographerSet);
			}
			
			setValue(photographerSet);
		}
	}
	
	@Override
    public String getAsText() {
		Set<Photographer> photographers = (Set<Photographer>) getValue();
        if (photographers != null) {
            return photographers.toString();
        } else {
            return "";
        }
    }
}
