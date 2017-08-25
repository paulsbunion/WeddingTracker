package com.defrainphoto.weddingtracker.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.defrainphoto.weddingtracker.model.Client;
import com.defrainphoto.weddingtracker.model.ClientManager;

public class ClientEditor extends PropertyEditorSupport{
	
	ClientManager clientManager = new ClientManager();
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String[] data = text.split(" ");
			Client client = new Client("", data[0], data[1], null, null, data[2], null);
			client = clientManager.getClientByEmail(client);
			setValue(client);
		}
	}
	
	@Override
    public String getAsText() {
        Client client = (Client) getValue();
        if (client != null) {
            return client.toString();
        } else {
            return "";
        }
    }
}
