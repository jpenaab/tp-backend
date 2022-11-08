/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.servlet;

import com.tiempoplaya.persistance.TPHelperDatos;
import com.tiempoplaya.persistance.TPHelperFotografia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author u$3R
 */
public class servletTPActionsPhotos extends HttpServlet {

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

        int photoid, opt = 0;

        opt = Integer.valueOf(request.getParameter("opt"));
        photoid = Integer.valueOf(request.getParameter("value"));

        TPHelperFotografia tpHelperFotografia = new TPHelperFotografia();
        Boolean result = null;

        switch (opt) {
            case 999:
                //delete
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.deleteFotografia(photoid);

                    if (result) {
                        request.setAttribute("deletefail", "false");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    } else {
                        request.setAttribute("deletefail", "true");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    }
                }
                break;
            case 888:
                //enable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.statusFotografia(photoid, 1);

                    if (result) {
                        request.setAttribute("enabled", "true");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    } else {
                        request.setAttribute("enabled", "false");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    }
                }
                break;
            case 777:
                //disable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.statusFotografia(photoid, 0);

                    if (result) {
                        request.setAttribute("disabled", "true");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    } else {
                        request.setAttribute("disabled", "false");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    }
                }
                break;
            case 666:
                //delete
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.deleteFotografia(photoid);

                    if (result) {
                        request.setAttribute("deletefail", "false");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    } else {
                        request.setAttribute("deletefail", "true");
                        request.getRequestDispatcher("/adminphotos").forward(request, response);
                    }
                }
                break;
            case 555:
                //enable
                if ((request.getSession(false).getAttribute("user_reader") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.statusFotografia(photoid, 1);

                    if (result) {
                        request.setAttribute("enabled", "true");
                        request.getRequestDispatcher("/userphotos").forward(request, response);
                    } else {
                        request.setAttribute("enabled", "false");
                        request.getRequestDispatcher("/userphotos").forward(request, response);
                    }
                }
                break;
            case 444:
                //disable
                if ((request.getSession(false).getAttribute("user_reader") == null)) {
                    result = false;
                } else {
                    result = tpHelperFotografia.statusFotografia(photoid, 0);

                    if (result) {
                        request.setAttribute("disabled", "true");
                        request.getRequestDispatcher("/userphotos").forward(request, response);
                    } else {
                        request.setAttribute("disabled", "false");
                        request.getRequestDispatcher("/userphotos").forward(request, response);
                    }
                }
                break;
            default:
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
