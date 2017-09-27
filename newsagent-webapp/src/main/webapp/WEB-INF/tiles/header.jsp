<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="header-container">
	<div id="header-content"><h2><spring:message code="messages.label.header"/></h2></div>
	<div id="header-locale">
		<a href="<c:url value="?lang=en"/>"><spring:message code="messages.link.localeEn"/></a> 
		<a href="<c:url value="?lang=ru"/>"><spring:message code="messages.link.localeRu"/></a>
	</div>
</div>