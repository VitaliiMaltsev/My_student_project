package edu.javacourse.third.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by antonsaburov on 27.02.17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentOrder implements Serializable
{
    //private static final long serialVersionUID = 7556377404604699342L;
    private Long studentOrderId;
    private Date studentOrderDate;

    @XmlElement(name = "husband",required = true)
    private PersonAdult husband;
    @XmlElement(name = "wife", required = true)
    private PersonAdult wife;


    @XmlElementWrapper(name = "children")
    @XmlElement(name = "child",required = true)
    private List<PersonChild> children;

    public StudentOrder(PersonAdult husband, PersonAdult wife, List<PersonChild> children) {
        this.husband = husband;
        this.wife = wife;
        this.children = children;
    }
    public Long getStudentOrderId() {
        return studentOrderId;
    }

    public Date getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(Date studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
    }

    public StudentOrder() {
    }

    public PersonAdult getHusband() {
        return husband;
    }

    public PersonAdult getWife() {
        return wife;
    }

    public void setHusband(PersonAdult husband) {
        this.husband = husband;
    }

    public void setWife(PersonAdult wife) {
        this.wife = wife;
    }

    public void setChildren(List<PersonChild> children) {
        this.children = children;
    }

    public List<PersonChild> getChildren() {
        return children;
    }

    public void setStudentOrderId(Long studentOrderId) {
        this.studentOrderId = studentOrderId;
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "studentOrderId=" + studentOrderId +
                ", studentOrderDate=" + studentOrderDate +
                ", husband=" + husband +
                ", wife=" + wife +
                ", children=" + children +
                '}';
    }
}
