package com.example.demo.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Tourist;
import com.example.demo.repositories.TouristRegRepository;

@Service
public class TouristRegService 
{
	@Autowired
	TouristRegRepository regrepo;

	public Tourist saveTourist(Tourist d)
	{
		return regrepo.save(d);
	}
	
	
	//get tourist by its primary key tourist if id for booking tour
	public Tourist getTouristByIdForBooking(int id)
	{

		Optional<Tourist> opt = regrepo.findById(id);
		Tourist t = null;
		try
		{
			t = opt.get();
		}
		catch(NoSuchElementException e)
		{
			t = null;
		}
		return t;
		
	}
	
	//get tourist by login id
	public Tourist getTourist(int id)
   {
		return regrepo.getTourist(id);
   }
	
	
	// method called for forget password
	public Tourist getTouristforforgetpwd(String uid) 
	{
		// TODO Auto-generated method stub
		return regrepo.getTouristforforgetpwd(uid);
	}
	
	
}