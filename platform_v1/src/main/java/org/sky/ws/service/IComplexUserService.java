package org.sky.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
/**
 * <b>function:</b>定制客户端请求WebService所需要的接口
 * @author 
 * @file ComplexUserService.java
 * @package 
 * @project CXFWebService
 * @version 1.0
 */
/*
 * ws 接口 
 */
@WebService
@SOAPBinding(style = Style.RPC)  
public interface IComplexUserService {
	@WebMethod
    public String getUserByName(@WebParam(name = "name") String name);
}