package org.sky.sys.security.tag;

import java.util.List;

import javax.servlet.jsp.tagext.BodyTagSupport;  

import org.sky.sys.model.SysOperation;
import org.sky.sys.service.SysFunctionService;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 
* @Title: MyAuthorizeTag.java 
* @Package org.es.sys.security.tag 
* @Description: TODO(页面按钮权限控制标签) 
* @author 位峰祥   
* @date 2016-7-16 下午9:04:05 
* @version V1.0
 */
public class MyAuthorizeTag extends BodyTagSupport {  
	private SysFunctionService sysfunservice=BspUtils.getBean(SysFunctionService.class);
    private static final long serialVersionUID = 1L;  
  
    private String securl;  
  
    
    public String getSecurl() {
		return securl;
	}


	public void setSecurl(String securl) {
		this.securl = securl;
	}


	@Override  
    public int doStartTag() {  
        //securl为空或者有权限时进行显示 ,开发模式
		if(!BspUtils.isSecurityFilter()){
			return EVAL_BODY_INCLUDE;
		}
		if(StringUtils.isNull(securl)){
			return EVAL_BODY_INCLUDE;
		}
		String userCode = BspUtils.getLoginUser().getCode();
		List<SysOperation> list=sysfunservice.getSysOperationByUserCode(userCode);
		for(SysOperation op:list){
			if(securl.equals(op.getUrl())){
				return EVAL_BODY_INCLUDE;
			}
		}
        return this.SKIP_BODY;  
    }  
}  