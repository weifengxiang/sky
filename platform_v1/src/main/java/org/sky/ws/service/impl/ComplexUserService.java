package org.sky.ws.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.sky.ws.service.IComplexUserService;
import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * <b>function:</b> WebService传递复杂对象，如JavaBean、Array、List、Map等
 * @author 
 * @file ComplexUserService.java
 * @package 
 * @project CXFWebService
 * @version 1.0
 */
@WebService
@SOAPBinding(style = Style.RPC)
public class ComplexUserService implements IComplexUserService {
   
	public ComplexUserService(){
		
	}
	@WebMethod
    public String getUserByName(@WebParam(name = "userid") String userid) {
	
       return "hello:"+userid+"===========";
    }
 
}