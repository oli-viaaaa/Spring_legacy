package com.javalab.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javalab.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    /*
     * 컨트롤러(즉 RequestMapping이 선언된 메서드 진입) 실행 직전에 동작.
     * 반환 값이 true일 경우 정상적으로 진행이 되고, false일 경우 실행이 멈춥니다.(컨트롤러 진입 안함)
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	log.info("preHandle 메소드");
        HttpSession session = request.getSession();
        UserVo user = (UserVo)session.getAttribute("user");
        
		// 세션에 사용자 정보가 없다는 것은 로그인  안한 사용자!
        if(user == null) {
        	
        	 log.info("세션에 사용자 정보가 없음");
        	 
        	// 인터셉터 오기 전에 가려고 했던 Url 추출
        	String previousUrl = request.getRequestURL().toString();

        	// 세션에 저장했다가 나중에 로그인이 되면 그 때 이동하기 위해서 저장
        	request.setAttribute("previousUrl", previousUrl);
        	log.info("원래 가려고 했던 URL : " + previousUrl);
        	
        	// 로그인 안한 사용자는 로그인 폼으로 이동		
        	String contextPath = request.getContextPath();
        	String url = contextPath + "/login/login.do"; // 로그인폼 띄워주는 메소드(핸들러) 호출
        	response.sendRedirect(url);
        	
        	return false; // 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
        }
        log.info("세션에 사용자 정보가 있어서 요청처리하러 감");

        return true; //컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임. true:가도됨.
    }    
    
    /*
     * 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
     * 컨트롤러 진입 후 view가 랜더링 되기 전에 수행됨.
     * 전달인자의 modelAndView을 통해 화면 단에 들어가는 데이터 등의 조작이 가능함.
     * Controller에서 Exception이 발생 할 경우 posthandle로 요청이 넘어오지 않는다.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
    						Object handler, ModelAndView modelAndView) throws Exception {
    	super.postHandle(request, response, handler, modelAndView);
    	log.info("postHandle 메소드, 딱히 하는 일이 없음, 그냥 거쳐감");

    }

    // [무조건 실행] 컨트롤러의 메소드(핸들러)가 실행되고 난 다음에 무조건 실행되는 로직 구현
    // 컨트롤러에서 Exception이 발생하여도 이 메소드는 실행된다. 즉, 뷰단은 실행된다.
    // view까지 처리하고 난 후에 처리됨
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    	log.info("afterCompletion 메소드, 무조건 실행, 그냥 거쳐감");
    }    
    
}
