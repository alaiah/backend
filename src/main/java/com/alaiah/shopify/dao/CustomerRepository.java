package com.alaiah.shopify.dao;

import com.alaiah.shopify.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

   Customer findByEmail(@RequestParam("email") String email);

   @Query("select count(e)>0 from Customer e where e.email = ?1")
   boolean doesCustomerExists(@Param("email") String email);


}
