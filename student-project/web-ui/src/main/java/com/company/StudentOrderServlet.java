package com.company;

import edu.javacourse.third.ProcessStarter;
import edu.javacourse.third.domain.PersonAdult;
import edu.javacourse.third.domain.StudentOrder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by antonsaburov on 18.05.17.
 */
@WebServlet(name = "StudentOrderServlet", urlPatterns = {"/add"})
public class StudentOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        String dateStr = req.getParameter("dateSO");
        String hSurName = req.getParameter("h_surName");
        String hGivenName = req.getParameter("h_givenName");
        String hPatronymic = req.getParameter("h_patronymic");
        String hDateOfBirth = req.getParameter("h_dateOfBirth");
        String hPassportSeria = req.getParameter("h_PassportSeria");
        String hPassportNumber = req.getParameter("h_PassportNumber");
        String hPassportDateIssue = req.getParameter("h_PassportDateIssue");

        String wSurName = req.getParameter("w_surName");
        String wGivenName = req.getParameter("w_givenName");
        String wPatronymic = req.getParameter("w_patronymic");
        String wDateOfBirth = req.getParameter("w_dateOfBirth");
        String wPassportSeria = req.getParameter("w_PassportSeria");
        String wPassportNumber = req.getParameter("w_PassportNumber");
        String wPassportDateIssue = req.getParameter("w_PassportDateIssue");


        PersonAdult h = new PersonAdult();
        h.setSurName(hSurName);
        h.setGivenName(hGivenName);
        h.setPatronymic(hPatronymic);
        h.setPassportSeria(hPassportSeria);
        h.setPassportNumber(hPassportNumber);
        PersonAdult w = new PersonAdult();
        w.setSurName(wSurName);
        w.setGivenName(wGivenName);
        w.setPatronymic(wPatronymic);
        w.setPassportSeria(wPassportSeria);
        w.setPassportNumber(wPassportNumber);

        StudentOrder so = new StudentOrder();
        try {
            so.setStudentOrderDate(sdf.parse(dateStr));
            h.setDateOfBirth(sdf.parse(hDateOfBirth));
            h.setPassportDateIssue(sdf.parse(hPassportDateIssue));
            w.setDateOfBirth(sdf.parse(wDateOfBirth));
            w.setPassportDateIssue(sdf.parse(wPassportDateIssue));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        so.setHusband(h);
        so.setWife(w);

        ProcessStarter ps = new ProcessStarter();
        ps.addStudentOrder(so);

        System.out.println("URL for StudentOrder is working");

        resp.getWriter().append("OK");
    }
}
