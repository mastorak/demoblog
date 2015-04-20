<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 3rd party libs -->
<script	src="js/jquery-1.11.2.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>

<!-- app javascript code -->
<script src="js/eddemoblog.js"></script>

</head>

<body>
	<div align="center">
		<h1>MongoDB+SPRING+JQuery Demo app</h1>

		<table border="1">
			<tr>
				<td width="700">
					<div id="postlistdiv"></div>

					<div id="postdiv" ></div>

					<div id="editordiv">
						<jsp:include page="WEB-INF/pages/editor.jsp" />
					</div>
				</td>

				<td valign="top">
					<div id="sidebar">
						<jsp:include page="WEB-INF/pages/sidebar.jsp" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>