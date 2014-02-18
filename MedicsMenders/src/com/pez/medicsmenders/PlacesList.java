package com.pez.medicsmenders;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;

public class PlacesList implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Key
    public String status;
	
    @Key
    public String next_page_token;
 
    @Key
    public List<Place> results;
	
	

}
