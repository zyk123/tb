<%@ include file="../../../toolbar/header.jsp" %>

<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>About</title>
    <link rel="stylesheet" href="<%=basePath %>static/css/base.css">
    <script src="<%=basePath %>static/js/jquery.1.8.3.min.js"></script>
    <script src="<%=basePath%>js/pageEvent.js"></script>
    <style type="text/css">
		html ,body{height:100%; font-family:"Exo 2",sans-serif,monospace;}
    </style>    
</head>
<body class="pt-08r" >

<header class="store tb_header fixed-top">
    <i class="back_one_i" onclick="javascript :history.back(-1)"></i>
    <h2>About</h2>
    <i class="close_i" onclick="history.go(-2)"> </i>
</header>
<div class="store_banner">
    <img src="./static/images/imgs/tbstorebg_xxx.jpg" alt="">
</div>
<div class="page_about">
    <div class="package_detail">
        <p class="title">About Celcom</p>
        <i class="i_con sline"></i>
        <p class="desc_text">
            Celcom is Malaysia's premier and most experienced mobile telecommunications company, with a strong portfolio of digital products and services. It is driving data leadership with close to 3500 4G LTE sites, and provides both prepaid and postpaid mobile services to close to 12 million subscribers in Malaysia.
        </p>
    </div>
    <div class="package_detail">
        <p class="title">About Toolbar</p>
        <i class="i_con sline"></i>
        <p class="desc_text">
            Toolbar is an online service centre for users while visiting mobile internet by browser. It offers convenient services and creative experience with excellent functions, such as data usage enquiry, internet reload, real-time data usage reminder and credit balance top up. You'll get more and more fun here.
        </p>
    </div>
</div>



<script type="text/javascript">
		var baseUrl = "<%=basePath%>";
		var browseUrl = "toolbar/about.jsp";
		recordVist('<%=request.getParameter("suid")%>');

</script>
</body>

</html>
