<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session ="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/newsagent.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/confirmation_window.css"/>" />
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  	<script>
  		$( function() {
    	$( "#datepicker" ).datepicker({dateFormat: "dd/mm/yy"});
  		} );
  		
  </script>
	<script src="<c:url value="/resources/confirm_deletion.js"/>"></script>
	<script src="<c:url value="/resources/checkboxes_checker.js"/>"></script>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="menu" />
	<div id="body-container">
		<tiles:insertAttribute name="location" />
		<tiles:insertAttribute name="body" />
	</div>
	<tiles:insertAttribute name="footer" />
</body>
</html>