package by.htp.newsagent.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLoggingAspect {
	
	@Before("execution(* by.htp.newsagent.controller.*.*(org.springframework.ui.Model, ..))")
	public void logHttpRequest(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for (Object argument : args) {
			if (argument instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) argument;
				System.out.println(new Date()+ " | url: " + request.getRequestURI() + " | method: " + request.getMethod());
			}
		}
	}
}
