<%-- 
    Document   : adminphotos
    Created on : 29-may-2019, 18:30:52
    Author     : u$3R
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.tiempoplaya.model.TFotografias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

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

        <script type="text/javascript">
            function onClick(element, pid) {
                document.getElementById("image-gallery-image").src = element.src;
                document.getElementById("image-gallery-footer").innerHTML = document.getElementById("figcaptionimage" + pid).innerHTML;
                document.getElementById("image-gallery").style.display = "block";
            }
        </script>

    </head>

    <body style="background: none">

        <!-- Navigation -->
        <jsp:include page="/WEB-INF/components/menu.jsp"></jsp:include>
            <br/>

        <% if (request.getAttribute("disabled") == "true") { %>
        <div class="col-md-6 offset-md-3">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Deshabilitada!</strong> La fotografía ha sido deshabilitada correctamente.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
        <% } else if (request.getAttribute("enabled") == "true") {%>
        <div class="col-md-6 offset-md-3">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>Habilitada!</strong> La fotografía ha sido habilitada correctamente.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
        <% }%>

        <header class="container-fluid">

            <c:set var="totalCount" scope="session" value="${list_photos.size()}"/>
            <c:set var="perPage" scope="session" value="12"/>
            <c:set var="pageStart" scope="session" value="${param.start}"/>

            <c:if test="${empty pageStart or pageStart < 0}">
                <c:set var="pageStart" value="0"/>
            </c:if>
            <c:if test="${totalCount < pageStart}">
                <c:set var="pageStart" value="${pageStart - perPage}"/>
            </c:if>


            <div class="row">

                <c:forEach items = "${list_photos}" var = "photo" varStatus="status" begin = "${pageStart}" end = "${pageStart + perPage -1}">

                    <div class="col-lg-3 col-md-4 col-xs-6 thumb">
                        <figure class="figure">
                            <a class="thumbnail" href="#" data-image-id="" data-toggle="modal" data-title=""
                               data-image="/images/beaches/<fmt:formatNumber pattern="0000" value="${photo.getTPlayas().getId()}" />/${photo.getFilename()}" data-target="#image-gallery">
                                <img id="imgphoto" onclick="onClick(this, '${photo.getId()}')" class="rounded img-fluid" src="/images/beaches/<fmt:formatNumber pattern="0000" value="${photo.getTPlayas().getId()}" />/${photo.getFilename()}" alt="${photo.getTPlayas().getNombre()}">
                            </a>
                            <!--figure caption-->
                            <figcaption id="figcaptionimage${photo.getId()}" class="figure-caption text-center">
                                ${photo.getTPlayas().getNombre()}
                                &nbsp;
                                <fmt:formatDate pattern="YYYY-MM-dd" value="${photo.getTimestamp()}"/>                   
                                &nbsp;
                                &nbsp;
                                <c:if test = "${photo.getEnabled() == 1}">

                                    <a href="/actionsphotos?opt=444&value=${photo.getId()}" title="deshabilitar" class="text-success"><i class="fas fa-check-circle"></i></a>

                                </c:if>
                                <c:if test = "${photo.getEnabled() == 0}">

                                    <a href="/actionsphotos?opt=555&value=${photo.getId()}" title="habilitar" class="text-danger"><i class="fas fa-times-circle"></i></a>

                                </c:if>

                            </figcaption>
                        </figure>
                    </div>

                </c:forEach>

            </div>

            <nav aria-label="page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link text-primary" href="?start=${pageStart - perPage}" aria-label="Previous">
                            <span aria-hidden="true" style="color: #07b587"><b>&laquo;</b></span>
                            <span class="sr-only" style="color: #07b587">Prev</span>
                        </a>
                    </li>
                    <c:forEach items = "${list_photos}" var = "photo" varStatus="status" begin = "0" end = "${totalCount/perPage}">                    
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

            <!-- Creates the bootstrap modal where the image will appear -->
            <div class="modal fade" id="image-gallery" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">   
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <img id="image-gallery-image" class="img-responsive col-md-12" src="">
                        </div>
                        <div class="modal-footer">
                            <h4 class="text-center text-secondary" id="image-gallery-footer"></h4>
                        </div>
                    </div>
                </div>
            </div>

            <br/>
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
