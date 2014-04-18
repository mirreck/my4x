<%@tag description="Bootstrap Navbar Tag" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="activelink" required="true"%>

<!-- Fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Mirreck Demos</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li <c:if test="${activelink == 'home'}">class="active"</c:if>><a href="home"><i class="icon-home"></i></a></li>
        <li <c:if test="${activelink == 'map'}">class="active"</c:if>><a href="map"><i class="icon-compass"></i> Map</a></li>
        <li <c:if test="${activelink == 'indoor'}">class="active"</c:if>><a href="indoor"><i class="icon-ex-door"></i> Indoor</a></li>
        <li <c:if test="${activelink == 'family'}">class="active"</c:if>><a href="family"><i class="icon-sitemap"></i> Family</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>