<%-- 
    Document   : adminphotos
    Created on : 29-may-2019, 18:30:52
    Author     : u$3R
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tiempoplaya.model.TUsuarios"%>
<%@page import="java.util.List"%>
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

    </style>
</head>

<%
    if ((request.getSession(false).getAttribute("administrator") == null)) {
%>
<jsp:forward page="/utils/login.jsp"></jsp:forward>
<%
    }
%>

<body style="background: none">
    <!-- Navigation -->
    <jsp:include page="/WEB-INF/components/menu.jsp"></jsp:include>
    <br/>
    <% if (request.getAttribute("savefail") == "true") { %>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> Ha habido algún error y el usuario no se ha modificado.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% } else if (request.getAttribute("savefail") == "false") {%>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Guardado!</strong> El usuario ha sido modificado correctamente.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% }%>
    <% if (request.getAttribute("deletefail") == "true") { %>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> Ha habido algún error y el usuario no se ha eliminado.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% } else if (request.getAttribute("deletefail") == "false") {%>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Eliminado!</strong> El usuario ha sido eliminado correctamente.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% }%>
    <% if (request.getAttribute("disabled") == "true") { %>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Deshabilitada!</strong> La cuenta de usuario ha sido deshabilitada correctamente.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% } else if (request.getAttribute("enabled") == "true") {%>
    <div class="col-md-6 offset-md-3">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Habilitada!</strong> La cuenta de usuario ha sido habilitada correctamente.
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
    <% }%>
    <header class="container-fluid">
        <div class="table-responsive">
            <table class="table">
                <thead style="background: #07b587">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Apellidos</th>
                        <th scope="col">Email</th>
                        <th scope="col">Grupo</th>
                        <th scope="col">Usuario</th>
                        <th scope="col">Estado</th>
                        <th scope="col">Acciones</th>
                    </tr>
                </thead>
                <tbody>

                    <c:set var="totalCount" scope="session" value="${list_users.size()}"/>
                    <c:set var="perPage" scope="session" value="10"/>
                    <c:set var="pageStart" scope="session" value="${param.start}"/>

                    <c:if test="${empty pageStart or pageStart < 0}">
                        <c:set var="pageStart" value="0"/>
                    </c:if>
                    <c:if test="${totalCount < pageStart}">
                        <c:set var="pageStart" value="${pageStart - perPage}"/>
                    </c:if>

                    <c:forEach items = "${list_users}" var = "usuario" varStatus="status" begin = "${pageStart}" end = "${pageStart + perPage -1}">
                    <form action="/actionsusers" method="POST" class="form-inline">
                        <tr class="text-dark">
                            <th class="align-middle"  scope="row">
                                <c:out value = "${usuario.getId()}"/>
                            </th>
                        <input name="userid" type="hidden" value="${usuario.getId()}"/>
                        <td class="align-middle" >
                            <input name="nombre" type="text" size="5"  class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="<c:out value = "${usuario.getNombre()}"/>"/>
                        </td>
                        <td class="align-middle" >
                            <input name="apellidos" type="text" size="10" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="<c:out value = "${usuario.getApellidos()}"/>"/>
                        </td>
                        <td class="align-middle" >
                            <input name="email" type="text" size="12"  class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="<c:out value = "${usuario.getEmail()}"/>"/>
                        </td>
                        <c:set var = "role" scope = "session" value = "${usuario.getId_grupo()}"/>
                        <c:if test = "${role == 1}">
                        <td class="align-middle" >
                            <select class="form-control mb-2 mr-sm-2" name="grupo">
                                <option value="1" selected>Readers</option>
                                <option value="2" disabled>Writers</option>
                                <option value="3" disabled>Administrators</option>
                            </select>
                        </td>
                        </c:if>
                        <c:if test = "${role == 2}">
                        <td class="align-middle" >
                            <select class="form-control mb-2 mr-sm-2" name="grupo">
                                <option value="1" disabled>Readers</option>
                                <option value="2" selected>Writers</option>
                                <option value="3" disabled>Administrators</option>
                            </select>
                        </td>
                        </c:if>
                        <c:if test = "${role == 3}">
                        <td class="align-middle" >
                            <select class="form-control mb-2 mr-sm-2" name="grupo">
                                <option value="1" disabled>Readers</option>
                                <option value="2" disabled>Writers</option>
                                <option value="3" selected>Administrators</option>
                            </select>
                        </td>
                        </c:if>
                        <td class="align-middle" >
                            <input name="usuario" type="text" size="5" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="<c:out value = "${usuario.getUsuario()}"/>"/>
                        </td>
                        <c:set var = "enabled" scope = "session" value = "${usuario.getEnabled()}"/>
                        <c:if test = "${enabled == 1}">
                            <td class="align-middle" >
                                <i class="text-success fas fa-check-circle"></i>
                            </td>
                        </c:if>
                        <c:if test = "${enabled == 0}">
                            <td class="align-middle" >
                                <i class="text-danger fas fa-times-circle"></i>
                            </td>
                        </c:if>
                        <td class="align-middle" >
                            <c:if test = "${enabled == 0}">

                                <button name="opt" value="991" title="habilitar" type="submit" class="btn btn-outline-success btn-sm"><i class="fas fa-check-circle"></i></button>

                            </c:if>
                            <c:if test = "${enabled == 1}">

                                <button name="opt" value="992" title="deshabilitar" type="submit" class="btn btn-outline-danger btn-sm"><i class="fas fa-times-circle"></i></button>

                            </c:if>

                            <button name="opt" value="999" title="guardar" type="submit" class="btn btn-outline-primary btn-sm"><i class="fas fa-save"></i></button>
                            <button name="opt" value="777" title="contraseña" type="button" class="btn btn-outline-warning btn-sm"><i class="fas fa-key"></i></button>
                            <button name="opt" value="666" title="eliminar" type="submit" class="btn btn-outline-secondary btn-sm"><i class="fas fa-trash-alt"></i></button>

                        </td>

                        </tr>
                    </form>
                </c:forEach>

                </tbody>
            </table>
        </div>

       <nav aria-label="page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link text-primary" href="?start=${pageStart - perPage}" aria-label="Previous">
                            <span aria-hidden="true" style="color: #07b587"><b>&laquo;</b></span>
                            <span class="sr-only" style="color: #07b587">Prev</span>
                        </a>
                    </li>
                    <c:forEach items = "${list_users}" var = "usuario" varStatus="status" begin = "0" end = "${totalCount/perPage}">
                        <li class="page-item"><a style="background: #07b587; color: #EEE" class="page-link" href="?start=${(status.count -1) * perPage}"><b>${status.count}</b></a></li> 
                    </c:forEach>
                    <li class="page-item">
                        <a class="page-link text-primary"  href="?start=${pageStart + perPage}" aria-label="Next">
                            <span aria-hidden="true" style="color: #07b587"><b>&raquo;</b></span>
                            <span class="sr-only" style="color: #07b587">Next</span>
                        </a>
                    </li>
                </ul>
            </nav>

        <div class="table-responsive">
            <table class="table">
                <thead style="background: #07b587">
                    <tr>
                        <th colspan=8 class="bg-warning text-dark">Nuevo Usuario</th>
                    </tr>
                </thead>
                <tbody>
                <form action="/actionsusers" method="POST" class="form-inline">
                    <tr class="alert-secondary text-dark align-middle">
                        <th class="align-middle" scope="row">
                            #
                        </th>
                        <td class="align-middle" >
                            <input name="nombre" type="text" class="form-control alert-secondary mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="nombre"
                        </td>
                        <td class="align-middle" >
                            <input name="apellidos" type="text" class="form-control alert-secondary mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="apellidos">
                        </td>
                        <td class="align-middle" >
                            <input name="email" type="text" class="form-control alert-secondary mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="email">
                        </td>
                        <td class="align-middle" >
                            <select class="form-control alert-secondary mb-2 mr-sm-2" name="grupo">
                                <option value="1">Readers</option>
                                <option value="2">Writers</option>
                                <option value="3">Administrators</option>
                            </select>
                        </td>
                        <td class="align-middle" >
                            <input name="usuario" type="text" class="form-control alert-secondary mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="usuario">
                        </td>
                        <td class="align-middle" >
                            <input name="contrasenya" type="text" class="form-control alert-secondary mb-2 mr-sm-2" id="inlineFormInputName2" placeholder="contraseña">
                        </td>
                        <td class="align-middle" >
                            <input name="userid" type="hidden" value="-5">
                            <input name="opt" type="hidden" value="888">
                            <button title="nuevo usuario" type="submit" class="btn btn-outline-primary btn-sm"><i class="fas fa-save"></i></button>
                        </td>
                    </tr>
                </form>

                </tbody>
            </table>

        </div>
                            
    </header>
    <br/>
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
