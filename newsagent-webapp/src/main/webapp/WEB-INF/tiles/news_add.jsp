<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="news-add-container">

<form:form action="${pageContext.request.contextPath}/news" modelAttribute="newsItemModel" method="POST">
<form:hidden path="id"/>
<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemTitle" /></div>
		<form:input path="title" cssClass="details-content" size="90"/>
		<form:errors path="title" cssClass="form-error-label" element="div" />
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemDate" /></div>
		<form:input path="newsDate" cssClass="details-content" size="15" id="datepicker"/>
		<form:errors path="newsDate" cssClass="form-error-label" element="div"/>
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemBrief" /></div>
		<form:textarea path="brief" cssClass="details-content" cols="80" rows="6"/>
		<form:errors path="brief" cssClass="form-error-label" element="div" />
	</div>
	
	<div class="details-item">
		<div class="details-title"><spring:message code="label.newsItemContent" /></div>
		<form:textarea path="content" cssClass="details-content" cols="80" rows="10"/>
		<form:errors path="content" cssClass="form-error-label" element="div"/>
	</div>
	
	<div id="submit-container">
		<a href="javascript:history.back()" class="button"><spring:message code="label.cancelButton" /></a>
		<input type="submit" value="<spring:message code="label.saveButton" />" class="button"/>
	</div>
</form:form>	
</div>