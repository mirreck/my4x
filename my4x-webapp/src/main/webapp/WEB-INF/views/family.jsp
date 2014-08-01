<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="m4x" uri="http://mirreck.github.io/m4x/tags"%>
<t:bootstrap>
    <t:bootstrap-head>
        <title>Family</title>
    </t:bootstrap-head>
    <t:bootstrap-body>
         <t:bootstrap-navbar activelink="family" />
         <h2>Family Tree.</h2>
         <div id="family-tree-1" class="family-tree">
            <div id="generation_0" class="generation"></div>
         </div>
    </t:bootstrap-body>
</t:bootstrap>