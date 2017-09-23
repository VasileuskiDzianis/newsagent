<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div id="news-view-container">
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemTitle" /></div>
		<div class="details-content"><c:out value="${newsItemModel.title}"/></div>
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemDate" /></div>
		<div class="details-content"><fmt:formatDate value="${newsItemModel.newsDate}" pattern="dd/MM/yyyy"/></div>
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemBrief" /></div>
		<div class="details-content"><c:out value="${newsItemModel.brief}"/></div>
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemContent" /></div>
		<div class="details-content"><c:out value="${newsItemModel.content}"/></div>
	</div>
	
	<div id="submit-container">
		<a href="<c:url value="/news/${newsItemModel.id}/edit" />" class="button"><spring:message code="label.editButton" /></a>
		<input type="button" value="<spring:message code="label.deleteButton" />" class="button" 
			onclick="confirmDeletion('${newsItemModel.id}','&quot;${newsItemModel.title}&quot; <spring:message code="message.confirmNewsDeletion" />')" />
	</div>
	
	<div id="prompt-form-container">
    <form id="prompt-form" action="<c:url value="/news" />" method="post">
    <input type="hidden" name="_method" value="delete" />
      <div id="prompt-message"></div>
      <input id="deleting_news" type="hidden" name="selectedNewsItems" value="${newsItemModel.id}"/>
      <input type="submit" value="<spring:message code="label.okButton" />">
      <input type="button" name="cancel" value="<spring:message code="label.cancelButton" />">
    </form>
	</div>
	
</div>