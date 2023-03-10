package com.khlopin.socialwebsite.controllers;


import com.khlopin.socialwebsite.entity.user.Role;
import com.khlopin.socialwebsite.utills.DB;
import com.khlopin.socialwebsite.utills.RedirectPaths;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

@WebServlet(name = "HomeServlet", value = "")
public class HomeServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute("roles", Role.values());

        DB.initWallsForMap();

        super.init(config);
    }

    private static final Logger log = LogManager.getLogger(HomeServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.trace(request.getSession().getAttribute("user") + "посетил главную страницу");
        request.getRequestDispatcher(RedirectPaths.TO_HOME).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
