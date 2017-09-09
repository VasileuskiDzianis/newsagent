<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="header-container">
	<div id="header-content"><h2><spring:message code="label.header"/></h2></div>
	<div id="header-locale">
		<a href="<c:url value="?lang=en"/>"><spring:message code="link.localeEn"/></a> 
		<a href="<c:url value="?lang=ru"/>"><spring:message code="link.localeRu"/></a>
	</div>
</div>