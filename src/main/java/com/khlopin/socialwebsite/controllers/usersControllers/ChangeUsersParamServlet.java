package com.khlopin.socialwebsite.controllers.usersControllers;


import com.khlopin.socialwebsite.entity.user.User;
import com.khlopin.socialwebsite.utills.Constants;
import com.khlopin.socialwebsite.utills.DB;
import com.khlopin.socialwebsite.utills.RedirectPaths;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


@WebServlet(name = "ChangeUsersParamServlet", value = "/userChangeProfile")
public class ChangeUsersParamServlet extends HttpServlet {

    //TODO вынести строки в константы

    private static final Logger log = LogManager.getLogger(ChangeUsersParamServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/userChangeProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = (User) request.getSession().getAttribute(Constants.USER);
        String oldLogin = user.getUserName();
        Optional<User> optionalUser = DB.userDataBase.find(oldLogin);
        if (optionalUser.isPresent()) {
            //Смена логина
            changeLogin(parameterMap, oldLogin, optionalUser.get());
            //Смена пароля
            changePassword(parameterMap, optionalUser.get());
        }
        request.getRequestDispatcher(RedirectPaths.TO_PROFILE).forward(request, response);
    }

    //TODO перенести в UserRepository
    private static void changePassword(Map<String, String[]> parameterMap, User userFromDB) {
        if (parameterMap.containsKey(Constants.PASSWORD)) {
            String newPassword = parameterMap.get(Constants.PASSWORD)[0];
            if (!newPassword.isEmpty()) {
                userFromDB.setPassword(newPassword);
                log.info(userFromDB + " изменил свой пароль");
            }
        }
    }

    //TODO перенести в UserRepository
    //TODO Не показывает, что не получилось сменить имя т.к оно уже занято. Сделать возвращение тру или фолс, если тру, то тредирект на одно, если фолс, то на другое
    private static void changeLogin(Map<String, String[]> parameterMap, String oldLogin, User userFromDB) {
        if (parameterMap.containsKey(Constants.LOGIN)) {
            String newLogin = parameterMap.get(Constants.LOGIN)[0];
            if (DB.userDataBase.find(newLogin).isEmpty()) {
                if (!newLogin.isEmpty()) {
                    userFromDB.setUserName(newLogin);
                    log.info(oldLogin + " изменил свой логин на " + newLogin);
                }
            } else {
                log.info(oldLogin + " попытался изменить свой логин на уже существующий " + newLogin);
            }
        }
    }
}
