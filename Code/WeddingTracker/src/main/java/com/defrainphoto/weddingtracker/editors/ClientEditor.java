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
			System.out.println("editing a Client");
			System.out.println(text);
			String[] data = text.split(" ");
			data[2] = data[2].substring(1, data[2].length() - 1);
			System.out.println(data[2]);
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
