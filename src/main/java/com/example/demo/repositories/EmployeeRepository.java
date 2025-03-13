package com.example.demo.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Tourist;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> 
{
	
	@Modifying
	@Query("update Employee e set e.e_photo =:file where e.employee_id =:id")
	public int uploadPhoto(int id,byte [] file);
	
	
	@Query("SELECT e FROM Employee e where e.loginid.login_id = :id ")
	public Employee getEmployee(int id);
	
	
	@Query("SELECT t FROM Employee t where t.loginid.uid = :uid ")
	public Employee getEmployeeforforgetpwd(String uid);

//	@Modifying
//	@Query("update Login l set l.pwd=:newpwd where uid = :uid")
//	public int UpdatePasswordiInLogin(String newpwd,String uid);
}