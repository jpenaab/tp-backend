<%-- 
    Document   : login
    Created on : 11-abr-2019, 22:09:45
    Author     : u$3R
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Tiempo y Playa es una aplicacición colaborativa que permite conocer las condióciones meteorolóígicas antes de un día de playa, gracias de su gran comunidad de usuarios. Puedes encontrar ionformación sobre playas de Canarias, de Tenerife, como la Playa de Las Vistas, playa de las Teresitas o la Playa de Las Arenas en Buenavista">
        <meta name="author" content="jpa">

        <title>Tiempo y Playa. Descubre las condiciones meteorológicas antes de un día de playa. Tenerife, Canarias, Playa de las Vistas, Playa de La Arena, Playa Jardín</title>

        <!-- Bootstrap core CSS -->
        <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="../vendor/simple-line-icons/css/simple-line-icons.css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet">

        <!-- Plugin CSS -->
        <link rel="stylesheet" href="../device-mockups/device-mockups.min.css">

        <!-- Custom styles for this template -->
        <link href="../css/new-age.min.css" rel="stylesheet">
        <link href="../css/tiempoplaya.css" rel="stylesheet">

        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-131010813-1"></script>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag() {
                dataLayer.push(arguments);
            }
            gtag('js', new Date());
            gtag('config', 'UA-131010813-1');
        </script>

        <!-- Custom styles for this template -->
        <link href="../css/new-age.min.css" rel="stylesheet">
        <!--Custom styles-->
        <link rel="stylesheet" type="text/css" href="../css/login2_style.css">
    </head>

    <%
        if ((request.getSession(false).getAttribute("administrator") != null) ||
            (request.getSession(false).getAttribute("user_reader") != null) ||
            (request.getSession(false).getAttribute("user_writer") != null)) {
    %>
        <jsp:forward page="/tp/maps.jsp"></jsp:forward>
    <%
        }
    %>
    <body>

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand js-scroll-trigger" href="../index.html#page-top">Inicio</a>
                <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto float-right text-right">
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="../index.html#download">Descargas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="../index.html#features">Caracter&iacute;sticas</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="../index.html#contact">Contacto</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="../utils/login.jsp">Iniciar sesi&oacute;n</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <header class="masthead">
            <div class="container h-100">
                <div class="row h-100">
                    <div class="col-lg-12 my-auto">
                        <div class="header-content mx-auto opacity">
                            <form action="login" method="POST" name="login" class="form-signin opacity rounded">       
                                <h3 class="form-signin-heading">Bienvenido</h3>
                                <hr class="colorgraph"><br>
                                <% if (request.getAttribute("loginfail") == "true") { %>
                                <div class="alert alert-danger text-center" role="alert">
                                    Fallo en los credenciales introducidos
                                </div>
                                <% }%>
                                <input type="text" class="form-control" id="username" name="j_username" placeholder="usuario" required="" autofocus="" />
                                <input type="password" class="form-control" id="password" name="j_password" placeholder="contrase&ntilde;a" required=""/>     		  

                                <button class="btn btn-lg btn-block btn-custom-color"  id="submit" name="login" value="login" type="Submit">comenzar</button>  			
                            </form>	
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <footer>
            <jsp:include page="/WEB-INF/components/foot.jsp"></jsp:include>
        </footer>

        <!-- Bootstrap core JavaScript -->
        <script src="../vendor/jquery/jquery.min.js"></script>
        <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Plugin JavaScript -->
        <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for this template -->
        <script src="../js/new-age.min.js"></script>

    </body>
</html>

