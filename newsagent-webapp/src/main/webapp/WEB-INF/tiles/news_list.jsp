<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="news-list-container">

	<c:forEach var="newsItem" items="${newsList}">
		<div class="news-item-container">
			<div class="news-item-date"><fmt:formatDate value="${newsItem.newsDate}" pattern="dd/MM/yyyy"/></div>
			<div class="news-item-title"><c:out value="${newsItem.title}"/></div>
			<div class="news-item-brief"><c:out value="${newsItem.brief}"/></div>
			<div class="news-item-actions">
				<a class="action-item" href="<c:url value="/news/${newsItem.id}"/>"><spring:message code="messages.link.viewNewsItem" /></a> 
				<a class="action-item" href="<c:url value="/news/${newsItem.id}/edit"/>"><spring:message code="messages.link.editNewsItem" /></a> 
				<input form="prompt-form" type="checkbox" class="action-item" name="selectedNewsItems" value="${newsItem.id}" onchange="checkCheckboxes()"/>
			</div>
		</div>
	</c:forEach>
	
	<div id="submit-container">
		<input type="button" id="banch-deletion-button" value="<spring:message code="messages.label.deleteButton" />" class="button" 
			onclick="confirmDeletion('0','<spring:message code="messages.message.confirmBanchOfNewsDeletion" />')" />
	</div>
	
	<div id="prompt-form-container">
    <form id="prompt-form" action="<c:url value="/news" />" method="post">
    <input type="hidden" name="_method" value="delete" />
      <div id="prompt-message"></div>
      <input type="submit" value="<spring:message code="messages.label.okButton" />">
      <input type="button" name="cancel" value="<spring:message code="messages.label.cancelButton" />">
    </form>
	</div>
	
</div>