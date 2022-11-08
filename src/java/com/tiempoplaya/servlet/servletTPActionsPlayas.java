/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.servlet;

import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.persistance.TPHelperPlayas;
import com.tiempoplaya.utils.CoordenadasUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.me.jstott.jcoord.LatLng;

/**
 *
 * @author u$3R
 */
public class servletTPActionsPlayas extends HttpServlet {

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

        int playaid, opt = 0;
        
        String nombre = "";
        String municipio = "";
        String pais = "";
        String UTMx = "";
        String UTMy = "";
        String UTMz = "";
        Character zone;
        
        LatLng obj;
        
        TPlayas p = new TPlayas();
        
        opt = Integer.valueOf(request.getParameter("opt"));
        playaid = Integer.valueOf(request.getParameter("value"));
        

        TPHelperPlayas tpHelperPlayas = new TPHelperPlayas();
        Boolean result = null;

        switch (opt) {
            case 999:
                //enable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {

                    result = tpHelperPlayas.statusPlayas(playaid, 1);

                    if (result) {
                        request.setAttribute("enabled", "true");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    } else {
                        request.setAttribute("enabled", "false");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    }
                }

                break;
            case 888:
                //disable
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperPlayas.statusPlayas(playaid, 0);

                    if (result) {
                        request.setAttribute("disabled", "true");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    } else {
                        request.setAttribute("disabled", "false");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    }
                }
                break;
            case 777:
                //save info, not change pass
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    
                    nombre = request.getParameter("nombre");
                    municipio = request.getParameter("municipio");
                    pais = request.getParameter("pais");
                    UTMx = request.getParameter("utmx");
                    UTMy = request.getParameter("utmy");
                    UTMz = request.getParameter("utmz").substring(0, 2);
                    zone = request.getParameter("utmz").charAt(2);
                    
                    p = new TPlayas(playaid, nombre, UTMx, UTMy, UTMz, zone, municipio, pais);

                    result = tpHelperPlayas.updatePlaya(p);

                    if (result) {
                        request.setAttribute("savefail", "false");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    } else {
                        request.setAttribute("savefail", "true");
                        request.getRequestDispatcher("/adminbeaches").forward(request, response);
                    }
                }

                break;
            case 111:
                //delete
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    result = tpHelperPlayas.deletePlaya(playaid);
                }

                if (result) {
                    request.setAttribute("deletefail", "false");
                    request.getRequestDispatcher("/adminbeaches").forward(request, response);
                } else {
                    request.setAttribute("deletefail", "true");
                    request.getRequestDispatcher("/adminbeaches").forward(request, response);
                }

                break;
            case 666:
                //geolocate
                //tenerife
                obj = new LatLng(28.224576,-16.603286);
                 
                if ((request.getSession(false).getAttribute("administrator") == null)) {
                    result = false;
                } else {
                    
                    UTMx = request.getParameter("utmx");
                    UTMy = request.getParameter("utmy");
                    UTMz = request.getParameter("utmz").substring(0, 2);
                    zone = request.getParameter("utmz").charAt(2);
                    
                    obj = CoordenadasUtils.getLatLngFromUTM(UTMx, UTMy, UTMz, zone);
                                        
                }

                request.setAttribute("latitud", obj.getLat());
                request.setAttribute("longitud", obj.getLng());
                request.setAttribute("zoom", 12);
                
                request.getRequestDispatcher("/tp/maps.jsp").forward(request, response);
                
                break;
                
            case 665:
                //geolocate
                //tenerife
                obj = new LatLng(28.224576,-16.603286);
                 
                if ((request.getSession(false).getAttribute("user_reader") == null)) {
                    result = false;
                } else {
                    
                    UTMx = request.getParameter("utmx");
                    UTMy = request.getParameter("utmy");
                    UTMz = request.getParameter("utmz").substring(0, 2);
                    zone = request.getParameter("utmz").charAt(2);
                    
                    obj = CoordenadasUtils.getLatLngFromUTM(UTMx, UTMy, UTMz, zone);
                                        
                }

                request.setAttribute("latitud", obj.getLat());
                request.setAttribute("longitud", obj.getLng());
                request.setAttribute("zoom", 12);
                
                request.getRequestDispatcher("/tp/maps.jsp").forward(request, response);
                
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
