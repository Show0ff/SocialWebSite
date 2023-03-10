package com.khlopin.socialwebsite.controllers.questControllers;


import com.khlopin.socialwebsite.services.questServices.QuestStarter;
import com.khlopin.socialwebsite.utills.Constants;
import com.khlopin.socialwebsite.utills.RedirectPaths;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "FirstQuestServlet", value = "/quest")
public class QuestServlet extends HttpServlet {


    private QuestStarter questStarter;
    private int stage = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        questStarter = new QuestStarter();
        questStarter.startQuest(request);

        request.getRequestDispatcher(RedirectPaths.TO_QUEST).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answer = request.getParameter(Constants.ANSWER);
        if (answer != null) { //TODO переработать метод, ерроры в конце
            if (answer.equals(Constants.FALSE)) {
                questStarter.nextStageOfQuest(request, answer, stage);
                request.getRequestDispatcher(RedirectPaths.TO_QUEST).forward(request, response);
            } else {
                request.setAttribute(Constants.RESULT, null);
                if (stage != questStarter.getQuest().getCountOfStages()) {
                    stage++;
                }
                if (stage == questStarter.getQuest().getCountOfStages() && answer.equals(Constants.TRUE)) {
                    stage = 0;
                    questStarter.findTrueAnswer();
                    request.setAttribute(Constants.RESULT, questStarter.getFinishTrueAnswer());
                    request.getRequestDispatcher(RedirectPaths.TO_QUEST).forward(request, response);
                } else {
                    questStarter.nextStageOfQuest(request, answer, stage);
                    request.getRequestDispatcher(RedirectPaths.TO_QUEST).forward(request, response);
                }
            }
        } else {
            request.getRequestDispatcher(RedirectPaths.ERROR).forward(request, response);
        }
    }
}
