<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div id="news-list-container">

	<form action="<c:url value="/news/list"/>" method="post">
	<c:forEach var="newsItem" items="${newsList}">
		<div class="news-item-container">
			<div class="news-item-date">${newsItem.newsDate}</div>
			<div class="news-item-title">${newsItem.title}</div>
			<div class="news-item-brief">${newsItem.brief}</div>
			<div class="news-item-actions">
				<a class="action-item" href="<c:url value="/news/list/view/id${newsItem.id}"/>"><spring:message code="link.viewNewsItem" /></a> 
				<a class="action-item" href="<c:url value="/news/list/edit/id${newsItem.id}"/>"><spring:message code="link.editNewsItem" /></a> 
				<input type="checkbox" class="action-item" name="selectedNewsItems" value="${newsItem.id}" />
			</div>
		</div>
	</c:forEach>
	<div id="submit-container">
		<input type="submit" value="<spring:message code="label.deleteButton" />" class="button" />
	</div>
	</form>
</div>