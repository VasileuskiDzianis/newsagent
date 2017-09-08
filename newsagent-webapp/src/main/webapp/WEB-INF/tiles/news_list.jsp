<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="news-list-container">

	<form action="<c:url value="/news"/>" method="post">
	<input type="hidden" name="_method" value="delete" />
	<c:forEach var="newsItem" items="${newsList}">
		<div class="news-item-container">
			<div class="news-item-date"><fmt:formatDate value="${newsItem.newsDate}" pattern="dd/MM/yyyy"/></div>
			<div class="news-item-title">${newsItem.title}</div>
			<div class="news-item-brief">${newsItem.brief}</div>
			<div class="news-item-actions">
				<a class="action-item" href="<c:url value="/news/${newsItem.id}"/>"><spring:message code="link.viewNewsItem" /></a> 
				<a class="action-item" href="<c:url value="/news/${newsItem.id}/edit"/>"><spring:message code="link.editNewsItem" /></a> 
				<input type="checkbox" class="action-item" name="selectedNewsItems" value="${newsItem.id}" />
			</div>
		</div>
	</c:forEach>
	<div id="submit-container">
		<input type="submit" value="<spring:message code="label.deleteButton" />" class="button" />
	</div>
	</form>
</div>