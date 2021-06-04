package ua.training.myWeb.commands;

import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteEditionCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = "redirect:editions";
        if (request.getParameter("editionId") != null) {

            long userId = Long.parseLong(request.getParameter("editionId"));
            try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
                editionDao.delete(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            forward = "redirect:editions";
        }

        return forward;
    }
}
