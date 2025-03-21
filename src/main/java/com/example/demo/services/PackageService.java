package com.example.demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.AddPackage;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PackageRepository;

@Service
public class PackageService {
	
	
	  @Autowired
	  PackageRepository packageprepo;
	  
	  
	  public AddPackage getPackageInfo(int id)
	  {
		  System.out.println("packageservice-----"+id);
		  Optional<AddPackage> packagedata =packageprepo.findById(id);
		  System.out.println("packageid----"+packagedata);
		  AddPackage pkg = null;
			try
			{
				pkg  = packagedata.get();
			}
			catch(NoSuchElementException e)
			{
				pkg = null;
			}
			
			
			return pkg;	  
	  }
	
	public AddPackage save(AddPackage p)
	{
		return packageprepo.save(p);
	}
	
	public void deletePackage(int pid) {
		
		packageprepo.deleteById(pid);
	}


	public List<AddPackage> geAllPackages() {
		
		
		return packageprepo.findAll();
	}


}