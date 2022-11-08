/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.servlet;

import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.persistance.TPHelperUsuarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jpenaab
 */
public class servletTPLogin extends HttpServlet {

    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    public static final int SECURE_TOKEN_LENGTH = 64;
    private static final char[] symbols = CHARACTERS.toCharArray();
    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("j_username");
        String pass = request.getParameter("j_password");

        TUsuarios u = new TUsuarios();
        u = new TPHelperUsuarios().logonWeb(user, pass);

        if (u != null) {

            if (u.getId_grupo() == 3) {
                //administrators
                HttpSession session = request.getSession(); //Creating a session
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("administrator", u.getUsuario()); //setting session attribute
                session.setAttribute("userid", u.getId()); //setting session attribute
                session.setAttribute("tk", getSecureToken()); //CSRF
                request.setAttribute("username", u.getUsuario());

                request.getRequestDispatcher("../tp/maps.jsp").forward(request, response);

            } else if (u.getId_grupo() == 2) {
                //users_write
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("user_writer", u.getUsuario());
                session.setAttribute("userid", u.getId()); //setting session attribute
                request.setAttribute("username", u.getUsuario());
                
                request.getRequestDispatcher("../tp/maps.jsp").forward(request, response);
                
            } else if (u.getId_grupo() == 1) {
                //users_read
                HttpSession session = request.getSession(); //Creating a session
                session.setMaxInactiveInterval(10 * 60);
                session.setAttribute("user_reader", u.getUsuario()); //setting session attribute
                session.setAttribute("userid", u.getId()); //setting session attribute
                session.setAttribute("tk", getSecureToken()); //CSRF
                request.setAttribute("username", u.getUsuario());

                request.getRequestDispatcher("../tp/maps.jsp").forward(request, response);
            } else {
                request.setAttribute("err_message", u.getUsuario());
                request.getRequestDispatcher("/utils/login.jsp").forward(request, response);
            }

        } else {

            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            request.setAttribute("loginfail", "true");
            rd.include(request, response);

        }
    }

    private String getSecureToken() {

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);

    }

}
