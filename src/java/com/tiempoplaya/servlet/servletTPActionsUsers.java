/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.servlet;

import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.persistance.TPHelperUsuarios;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author u$3R
 */
public class servletTPActionsUsers extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId, opt = 0;

        opt = Integer.valueOf(request.getParameter("opt"));
        userId = Integer.valueOf(request.getParameter("userid"));

        TPHelperUsuarios tpHelperUsuarios = new TPHelperUsuarios();
        Boolean result = true;

        //save
        String nombre = "";
        String apellidos = "";
        String email = "";
        String usuario = "";
        Integer grupo = 0;
        String contrasenya = "";
        String tk = "";

        TUsuarios u = new TUsuarios();

        switch (opt) {
            case 999:
                //save info, not change pass
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    nombre = request.getParameter("nombre");
                    apellidos = request.getParameter("apellidos");
                    email = request.getParameter("email");
                    usuario = request.getParameter("usuario");
                    contrasenya = request.getParameter("contrasenya");
                    tk = request.getParameter("tk");

                    u = new TUsuarios(userId, nombre, apellidos, usuario, contrasenya, email, tk);

                    result = tpHelperUsuarios.updateUsuario(u);

                    if (result) {
                        request.setAttribute("savefail", "false");
                        request.getRequestDispatcher("/adminusers").forward(request, response);
                    } else {
                        request.setAttribute("savefail", "true");
                        request.getRequestDispatcher("/adminusers").forward(request, response);
                    }
                }

                break;
            case 991:
                //enable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperUsuarios.statusUsuario(userId, 1);
                }
                if (result) {
                    request.setAttribute("enabled", "true");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                } else {
                    request.setAttribute("enabled", "false");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                }
                break;
            case 992:
                //disable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperUsuarios.statusUsuario(userId, 0);
                }

                if (result) {
                    request.setAttribute("disabled", "true");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                } else {
                    request.setAttribute("disabled", "false");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                }
                break;
            case 888:
                //save new
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    nombre = request.getParameter("nombre");
                    apellidos = request.getParameter("apellidos");
                    email = request.getParameter("email");
                    usuario = request.getParameter("usuario");
                    grupo = Integer.parseInt(request.getParameter("grupo"));
                    contrasenya = request.getParameter("contrasenya");

                    result = tpHelperUsuarios.createUsuario(nombre, apellidos, usuario, contrasenya, email, grupo);

                    if (result) {
                        request.setAttribute("savefail", "false");
                        request.getRequestDispatcher("/adminusers").forward(request, response);
                    } else {
                        request.setAttribute("savefail", "true");
                        request.getRequestDispatcher("/adminusers").forward(request, response);
                    }
                }
                break;
            case 666:
                //delete
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperUsuarios.deleteUsuario(userId);
                }

                if (result) {
                    request.setAttribute("deletefail", "false");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                } else {
                    request.setAttribute("deletefail", "true");
                    request.getRequestDispatcher("/adminusers").forward(request, response);
                }

                break;
            case 790:
                //save own info, not change pass
                if ((request.getSession(false).getAttribute("user_reader") == null)) {
                    result = false;
                } else {
                    
                    nombre = request.getParameter("nombre");
                    apellidos = request.getParameter("apellidos");
                    email = request.getParameter("email");
                    
                    u = new TUsuarios(userId, nombre, apellidos, email);

                    result = tpHelperUsuarios.updateMyOwnUserInfo(u);
                    
                    System.err.println("HOLAHOLAHOLAHOLA - - - ");

                    if (result) {
                        request.setAttribute("savefail", "false");
                        request.getRequestDispatcher("/userdata").forward(request, response);
                    } else {
                        request.setAttribute("savefail", "true");
                        request.getRequestDispatcher("/userdata").forward(request, response);
                    }
                }

                break;

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
