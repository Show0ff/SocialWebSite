package com.khlopin.socialwebsite.controllers;


import com.khlopin.socialwebsite.utills.RedirectPaths;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "SupportServlet", value = "/support")
public class SupportServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(SupportServlet.class);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace(request.getSession().getAttribute("user") + "посетил страницу с поддержкой");
        request.getRequestDispatcher(RedirectPaths.TO_SUPPORT).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
