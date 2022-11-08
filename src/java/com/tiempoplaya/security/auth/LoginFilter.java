/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.security.auth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author u$3R
 */
public class LoginFilter implements Filter {

    protected FilterConfig filterConfig;
    private List revokeList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;

        // read revoked user list
        revokeList = new java.util.ArrayList();
        readConfig();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

      // pre login action
        // get username 
        String username = req.getParameter("j_username");

        // if user is in revoked list send error
        if (revokeList.contains(username)) {
            res.sendError(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

      // call next filter in the chain : let j_security_check authenticate 
        // user
        chain.doFilter(request, response);

        // post login action
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
        revokeList = null;
    }

    private void readConfig() {
        if (filterConfig != null) {

            // get the revoked user list file and open it.
            BufferedReader in;
            try {
                String filename = filterConfig.getInitParameter("RevokedUsers");
                in = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException fnfe) {
                return;
            }

            // read all the revoked users and add to revokeList. 
            String userName;
            try {
                while ((userName = in.readLine()) != null) {
                    revokeList.add(userName);
                }
            } catch (IOException ioe) {
            }

        }
    }

}
