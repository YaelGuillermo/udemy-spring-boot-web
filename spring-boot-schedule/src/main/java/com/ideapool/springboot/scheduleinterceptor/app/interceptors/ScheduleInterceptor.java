package com.ideapool.springboot.scheduleinterceptor.app.interceptors;

import java.util.Calendar;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("scheduleInterceptor")
public class ScheduleInterceptor implements HandlerInterceptor {

	@Value("${config.schedule.open}")
	private Integer open;
	
	@Value("${config.schedule.close}")
	private Integer close;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hour >= open && hour < close) {
			StringBuilder message = new StringBuilder("Welcome to customer service hours");
			message.append(", we serve from ");
			message.append(open);
			message.append(" to ");
			message.append(close);
			message.append(".");
			request.setAttribute("message", message.toString());
			return true;
		}
		
		response.sendRedirect(request.getContextPath().concat("/close"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		String message = (String) request.getAttribute("message");
		
		if (modelAndView != null && handler instanceof HandlerMethod) {
			modelAndView.addObject("schedule", message);
		}
	}

}
