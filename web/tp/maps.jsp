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

<body>
    <!-- Navigation -->
    <jsp:include page="/WEB-INF/components/menu.jsp"></jsp:include>

    <body class="masthead">

        <div class="embed-responsive embed-responsive-16by9">
            <div id="map" class="embed-responsive-item"></div>
        </div>

    <%
        if (request.getAttribute("latitud") != null && request.getAttribute("longitud") != null) {
    %>

    <script>
        // Initialize and add the map
        function initMap() {
            // The location of beach
            var tenerife = {lat: ${latitud}, lng: ${longitud}};
            // The map, centered at Uluru
            var map = new google.maps.Map(
                    document.getElementById('map'), {zoom: ${zoom}, center: tenerife});
            // The marker, positioned at Uluru
            var marker = new google.maps.Marker({position: tenerife, map: map});
        }
    </script>

    <% } else { %>

    <script>
        // Initialize and add the map
        function initMap() {
            // The location of Tenerife
            var tenerife = {lat: 28.224576, lng: -16.603286};
            // The map, centered at Uluru
            var map = new google.maps.Map(
                    document.getElementById('map'), {zoom: 7, center: tenerife});
            // The marker, positioned at Uluru
            var marker = new google.maps.Marker({position: tenerife, map: map});
        }
    </script>

    <% }%>
    
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDFzWkEmvP8kVgQXKV5b3rchKTiEQPQdNc&callback=initMap">
    </script>

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

