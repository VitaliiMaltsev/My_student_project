package edu.javacourse.third;

import edu.javacourse.third.domain.PersonAdult;
import edu.javacourse.third.domain.PersonChild;
import edu.javacourse.third.domain.StudentOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentOrderUtil {
    public static StudentOrder createStudentOrder() {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderDate(new Date());
        PersonAdult h = adult("H");
        PersonAdult w = adult("W");

        List<PersonChild> children = new ArrayList<>();
        for(int i=0; i<3; i++) {
            children.add(child(i));
        }
        so.setChildren(children);

        so.setHusband(h);
        so.setWife(w);
        return so;
    }

    private static PersonAdult adult(String prefix) {
        PersonAdult p = new PersonAdult();
        p.setSurName(prefix + "_SurName");
        p.setGivenName(prefix + "_GivenName");
        p.setPatronymic(prefix + "_Patronymic");
        p.setDateOfBirth(new Date());
        p.setPassportSeria(prefix + "_SERIA");
        p.setPassportNumber("123456");
        p.setPassportDateIssue(new Date());
        return p;
    }

    private static PersonChild child(int counter) {
        PersonChild c = new PersonChild();
        c.setSurName("Child_SurName" + counter);
        c.setGivenName("Child_GivenName" + counter);
        c.setPatronymic("Child_Patronymic" + counter);
        c.setDateOfBirth(new Date());
        c.setBirthDocument("Child_BirthDocument_" + counter);
        return c;
    }
}
