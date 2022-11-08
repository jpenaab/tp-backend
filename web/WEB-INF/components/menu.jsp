<%-- 
    Document   : menu
    Created on : 29-may-2019, 18:32:21
    Author     : u$3R
--%>
<nav class="navbar navbar-expand-lg navbar-light bg-dark" id="mainNav">
        <div class="container">
	
            <% if ((request.getSession(false).getAttribute("administrator") != null)) {%>

	    <a class="navbar-brand js-scroll-trigger" href="#"><%= request.getSession(false).getAttribute("administrator")%> ></a>

            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
		<%= request.getSession(false).getAttribute("administrator")%>
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto float-right text-right">
			<li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/tp/maps.jsp">Maps</a>
                    	</li>
			<div class="dropdown-divider"></div>
	                <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Administrador
                        </a>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="/adminusers">Gestión de Usuarios</a>
                                <a class="dropdown-item" href="/adminbeaches">Gestión de Playas</a>
                                <a class="dropdown-item" href="/adminphotos">Gestión de Fotos</a>
                            </div>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/statistics">Estadísticas</a>
                        </li>
			<div class="dropdown-divider"></div>
			<li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userbeaches">Mis Playas</a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userphotos">Mis Fotos</a>
                        </li>         
			<div class="dropdown-divider"></div>
			<li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userdata">Mis Datos</a>
                        </li>
			<div class="dropdown-divider"></div>
			<li class="nav-item">
			<a class="nav-link js-scroll-trigger" href="/utils/logout">Salir</a>
			</li>
		</ul>
	   </div>

	<% } else if ((request.getSession(false).getAttribute("user_writer") != null)) {%>

            <a class="navbar-brand js-scroll-trigger" href="#"><%= request.getSession(false).getAttribute("user_reader")%> ></a>

            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <%= request.getSession(false).getAttribute("user_writer")%>
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto float-right text-right">
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/tp/maps.jsp">Maps</a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/statistics">Estadísticas</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userbeaches">Mis Playas</a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userphotos">Mis Fotos</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userdata">Mis Datos</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/utils/logout">Salir</a>
                        </li>
                </ul>
           </div>

	<% } else if ((request.getSession(false).getAttribute("user_reader") != null)) {%>

            <a class="navbar-brand js-scroll-trigger" href="#"><%= request.getSession(false).getAttribute("user_reader")%> ></a>

            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <%= request.getSession(false).getAttribute("user_reader")%>
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto float-right text-right">
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/tp/maps.jsp">Maps</a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/statistics">Estadísticas</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userbeaches">Mis Playas</a>
                        </li>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userphotos">Mis Fotos</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/userdata">Mis Datos</a>
                        </li>
                        <div class="dropdown-divider"></div>
                        <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/utils/logout">Salir</a>
                        </li>
                </ul>
           </div>

        <% } else { %>

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
	<% } %>
   </div> <!-- div container -->
</nav>
