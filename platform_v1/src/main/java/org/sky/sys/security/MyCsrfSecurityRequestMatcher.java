package org.sky.sys.security;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class MyCsrfSecurityRequestMatcher implements RequestMatcher {
	private Logger log=LoggerFactory.getLogger(MyCsrfSecurityRequestMatcher.class);
    private Pattern allowedMethods = Pattern
            .compile("^(GET|HEAD|TRACE|OPTIONS)$");

    public boolean matches(HttpServletRequest request) {

        if (execludeUrls != null && execludeUrls.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : execludeUrls) {
                if (servletPath.startsWith(url)) {
                	log.debug("请求路径"+servletPath+"以"+url+"结尾 过滤掉CSRF验证");
                    return false;
                }
            }
        }
        log.debug("请求路径"+request.getServletPath()+"进行CSRF验证");
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    /**
     * 需要排除的url列表
     */
    private List<String> execludeUrls;

    public List<String> getExecludeUrls() {
        return execludeUrls;
    }

    public void setExecludeUrls(List<String> execludeUrls) {
        this.execludeUrls = execludeUrls;
    }
}