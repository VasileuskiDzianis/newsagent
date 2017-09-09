<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="location-container">
	<a href="javascript:history.back()"><spring:message
			code="${locationModel.previousLocation.label}" /></a> &gt;&gt;
	<spring:message code="${locationModel.currentLocation.label}" />
</div>