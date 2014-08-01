<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="m4x" uri="http://mirreck.github.io/m4x/tags"%>
<t:bootstrap>
    <t:bootstrap-head>
        <title>Indoor</title>
    </t:bootstrap-head>
    <t:bootstrap-body>

  <t:bootstrap-navbar activelink="indoor" />
	
	<h2><i class="icon-ex-door"></i>Indoor Demo
<span class="fa-stack">
<i class="icon-ex-pj_p sable"></i>
<i class="icon-ex-pj_h argent"></i>
<i class="icon-ex-pj_f sable"></i>
</span>
	</h2>
	<div id="indoorminimap"></div>
	<div id="indoormap" class="indoormap" style="width: 700px; height: 700px;"></div>

	</t:bootstrap-body>
</t:bootstrap>
