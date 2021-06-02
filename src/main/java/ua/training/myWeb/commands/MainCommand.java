package ua.training.myWeb.commands;

import com.google.common.collect.Lists;
import ua.training.myWeb.Path;
import ua.training.myWeb.model.dao.EditionDao;
import ua.training.myWeb.model.dao.impl.JDBCDaoFactory;
import ua.training.myWeb.model.entity.Edition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MainCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try (EditionDao editionDao = JDBCDaoFactory.getInstance().createEditionDao()) {
            long page = 1;
            long recordsPerPage = 6;
            if(request.getParameter("page") != null)
                page = Integer.parseInt(request.getParameter("page"));

            List<List<Edition>> editionList = Lists.partition(editionDao.findAllWithOffsetLimit((page-1)*recordsPerPage, recordsPerPage), 2);

            request.setAttribute("editionsList", editionList);

            long noOfRecords = editionDao.count();
            long noOfPages = (long) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

            System.out.println("noOfPages "+ noOfPages);
            System.out.println("noOfRecords "+ noOfRecords);
            System.out.println("currentPage "+ noOfPages);
            System.out.println(editionList);
        } catch (Exception e) {
            e.printStackTrace();
            return Path.ERROR_PAGE;
        }


        return Path.MAIN_PAGE;
    }
}
