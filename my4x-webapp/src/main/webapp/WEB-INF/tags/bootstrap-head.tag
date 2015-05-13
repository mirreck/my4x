<%@tag description="Bootstrap head tag" pageEncoding="UTF-8"%>
<%@taglib prefix="m4x" uri="http://mirreck.github.io/m4x/tags"%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>HTML5 DEMO</title>
    <link href="resources/lib/3rdparty-styles.min.css" rel="stylesheet" />
    <link href="resources/css/app-styles.min.css" rel="stylesheet" />
    <!--[if lt IE 9]>
    <script src="resources/lib/polyfills.js"></script>
    <![endif]-->
<m4x:include-all-js basePath="/resources/lib/" pattern=".js" excludes="polyfills,.min.,require" />

<m4x:include-all-js basePath="/resources/lib/polyfills" pattern=".js" prefix="<!--[if lt IE 9]>" suffix=" <![endif]-->"/>

<m4x:include-all-css basePath="/resources/lib/" pattern=".css" excludes=".min." />

    <jsp:doBody/>
</head>