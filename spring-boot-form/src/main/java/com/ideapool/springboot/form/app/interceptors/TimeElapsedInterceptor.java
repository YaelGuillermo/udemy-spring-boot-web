package com.ideapool.springboot.form.app.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("timeElapsedInterceptor")
public class TimeElapsedInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeElapsedInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			logger.info("It is a method of the controller: " + method.getMethod().getName());		
		}
		
		logger.info("TimeElapsedInterceptor: preHandle() initializing...");
		logger.info("Intercepting: " + handler);

		long initialTime = System.currentTimeMillis();
		request.setAttribute("initialTime", initialTime);
		
		Random random = new Random();
		Integer delay = random.nextInt(100);
		Thread.sleep(delay);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (!request.getMethod().equalsIgnoreCase("post")) {
			logger.info("TimeElapsedInterceptor: postHandle() initializing...");
			long finalTime = System.currentTimeMillis();
			long initialTime = (Long) request.getAttribute("initialTime");
			
			long elapsedTime = finalTime - initialTime;
			if (handler instanceof HandlerMethod && modelAndView != null) {
				modelAndView.addObject("elapsedTime", elapsedTime);
			}
			logger.info("Elapsed Time: " + elapsedTime + " millis");
			logger.info("TimeElapsedInterceptor: postHandle() finishing...");
		}
	}
	
}
