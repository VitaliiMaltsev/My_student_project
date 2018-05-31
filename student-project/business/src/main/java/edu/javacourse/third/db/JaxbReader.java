package edu.javacourse.third.db;

import edu.javacourse.third.domain.PersonChild;
import edu.javacourse.third.domain.StudentOrder;
import edu.javacourse.third.domain.StudentOrders;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JaxbReader {

    public List<StudentOrder> readXml() {
        List<StudentOrder> result = new ArrayList<>();
        try (InputStream is = new FileInputStream("student_orders.xml")) {

            JAXBContext js = JAXBContext.newInstance(StudentOrders.class);
            Unmarshaller u = js.createUnmarshaller();
            StudentOrders sors = (StudentOrders) u.unmarshal(is);

            for (StudentOrder so : sors.getStudentOrders()) {
                result.add(so);
//                System.out.println(so.getHusband().getSurName());
//                System.out.println(so.getHusband().getGivenName());
//                System.out.println(so.getHusband().getPatronymic());
//                System.out.println(so.getHusband().getDateOfBirth());
//                System.out.println(so.getHusband().getPassportNumber());
//                System.out.println(so.getHusband().getPassportSeria());
//                System.out.println(so.getHusband().getPassportDateIssue());
//
//                System.out.println(so.getWife().getSurName());
//                System.out.println(so.getWife().getGivenName());
//                System.out.println(so.getWife().getPatronymic());
//                System.out.println(so.getWife().getDateOfBirth());
//                System.out.println(so.getWife().getPassportNumber());
//                System.out.println(so.getWife().getPassportSeria());
//                System.out.println(so.getWife().getPassportDateIssue());
//                for (PersonChild pc : so.getChildren()) {
//
//                    System.out.println(pc.getSurName());
//                    System.out.println(pc.getGivenName());
//                    System.out.println(pc.getPatronymic());
//                    System.out.println(pc.getDateOfBirth());
//                    System.out.println(pc.getBirthDocument());
//                }
//                System.out.println("==============");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}
