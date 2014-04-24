<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:bootstrap>
    <t:bootstrap-navbar activelink="family" />
    <h2>Family Tree.</h2>
	
			<div id="family-tree-1" class="family-tree">
			<canvas id="canvas"></canvas>
			<div id="generation_0" class="generation"></div>
		</div>
	
</t:bootstrap>