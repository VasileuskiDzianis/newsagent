<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="menu-container">
	<div id="menu-subcontainer">
		<div id="menu-header"><spring:message code="label.menuHeader"/></div>
		<div id="menu-content">
			<div><a href="<c:url value="/news/list"/>"><spring:message code="link.newsList"/></a></div>
			<div><a href="<c:url value="/news/add"/>"><spring:message code="link.newsAdd"/></a></div>
			<div><a href="<c:url value="/about"/>"><spring:message code="link.about"/></a></div>
		</div>

	</div>
</div>