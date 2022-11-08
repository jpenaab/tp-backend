<%-- 
    Document   : adminphotos
    Created on : 29-may-2019, 18:30:52
    Author     : u$3R
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.tiempoplaya.model.TPlayas"%>
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
    if ((request.getSession(false).getAttribute("administrator") != null)
            || (request.getSession(false).getAttribute("user_reader") != null)
            || (request.getSession(false).getAttribute("user_writer") != null)) {

    } else { %>
<jsp:forward page="/utils/login.jsp"></jsp:forward>
<%
    }
%>

<body style="background: none">
    <!-- Navigation -->
    <jsp:include page="/WEB-INF/components/menu.jsp"></jsp:include>
        <br/>
        <header class="container-fluid">

            <div class="table-responsive">
                <table class="table">
                    <thead style="background: #07b587">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Nombre</th>
                            <th scope="col">Municipio</th>
                            <th scope="col">País</th>
                            <th scope="col">UTM X</th>
                            <th scope="col">UTM Y</th>
                            <th scope="col">UTM Z</th>
                            <th scope="col">Estado</th>
                            <th scope="col">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>

                    <c:set var="totalCount" scope="session" value="${list_playas.size()}"/>
                    <c:set var="perPage" scope="session" value="10"/>
                    <c:set var="pageStart" scope="session" value="${param.start}"/>

                    <c:if test="${empty pageStart or pageStart < 0}">
                        <c:set var="pageStart" value="0"/>
                    </c:if>
                    <c:if test="${totalCount < pageStart}">
                        <c:set var="pageStart" value="${pageStart - perPage}"/>
                    </c:if>

                    <c:forEach items = "${list_playas}" var = "playa" varStatus="status" begin = "${pageStart}" end = "${pageStart + perPage -1}">
                    <form action="/actionsplayas" method="POST" class="form-inline">
                        <tr class="text-dark">
                            <th class="align-middle"  scope="row">
                                <c:out value = "${playa.getId()}"/>
                            </th>
                            <input name="value" type="hidden" value="${playa.getId()}"/>
                            <td class="align-middle" >
                                <input name="nombre" type="text" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getNombre()}"/>
                            </td>
                            <td class="align-middle" >
                                <input name="municipio" type="text" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getMunicipio()}"/>
                            </td>
                            <td class="align-middle" >
                                <input name="pais" type="text" maxlength="2" size="2"  class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getPais()}"/>
                            </td>
                            <td class="align-middle" >
                                <input name="utmx" type="text" size="5" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getCoordUTMx()}"/>
                            </td>
                            <td class="align-middle" >
                                <input name="utmy" type="text" size="5" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getCoordUTMy()}"/>
                            </td>
                            <td class="align-middle" >
                                <input name="utmz" type="text" maxlength="3" size="3" class="form-control mb-2 mr-sm-2" id="inlineFormInputName2" value="${playa.getCoordUTMz()}${playa.getUtmzone()}"/>
                            </td>   
                            <c:set var = "enabled" scope = "session" value = "${playa.getEnabled()}"/>
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
                                <button name="opt" value="665" title="geolocalizar" type="submit" class="btn btn-outline-warning btn-sm"><i class="fas fa-map-marker-alt"></i></button>
                                    <c:if test="${playa.getWebcam() != null}">
                                    <a href="${playa.getWebcam()}" target="_blank" title="webcam" role="button" class="btn btn-outline-info btn-sm"><i class="fas fa-video"></i></a>
                                    </c:if>
                                    <c:if test="${playa.getWebcam() == null}">
                                    <a href="#" target="_blank" title="webcam" role="button" class="btn btn-outline-info btn-sm disabled"><i class="fas fa-video-slash"></i></a>
                                    </c:if>

                            </td>

                        </tr>
                    </form>
                </c:forEach>

                </tbody>
            </table>

            <nav aria-label="page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link text-primary" href="?start=${pageStart - perPage}" aria-label="Previous">
                            <span aria-hidden="true" style="color: #07b587"><b>&laquo;</b></span>
                            <span class="sr-only" style="color: #07b587">Prev</span>
                        </a>
                    </li>
                    <c:forEach items = "${list_playas}" var = "playa" varStatus="status" begin = "0" end = "${totalCount/perPage}">                    
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
