package edu.javacourse.third.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "student-orders")
    public class StudentOrders implements Serializable

    {
        //private static final long serialVersionUID = 7556377404604699342L;

        @XmlElement(name = "student-order", required = true)
        private List<StudentOrder> studentOrders;

        public List<StudentOrder> getStudentOrders() {
            return studentOrders;
        }

        public void setStudentOrders(List<StudentOrder> studentOrders) {
            this.studentOrders = studentOrders;
        }
}
