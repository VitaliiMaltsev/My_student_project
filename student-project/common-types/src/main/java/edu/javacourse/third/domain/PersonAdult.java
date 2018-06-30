package edu.javacourse.third.domain;

import edu.javacourse.third.adapter.StudentDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by antonsaburov on 06.03.17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonAdult extends Person implements Serializable
{
    @XmlElement(name ="seria",required = true)
    private String passportSeria;
    @XmlElement(name="number",required = true)
    private String passportNumber;
    @XmlElement(name = "dateOfIssue", required = true)
    @XmlJavaTypeAdapter(StudentDateAdapter.class)
    private Date passportDateIssue;
    @XmlElement(name = "dateOfExpire",required = false)
    @XmlJavaTypeAdapter(StudentDateAdapter.class)
    private Date passportDateExpire;

    public PersonAdult() {
    }

    public String getPassportSeria() {
        return passportSeria;
    }

    public void setPassportSeria(String passportSeria) {
        this.passportSeria = passportSeria;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportDateIssue() {
        return passportDateIssue;
    }

    public void setPassportDateIssue(Date passportDateIssue) {
        this.passportDateIssue = passportDateIssue;
    }

    public Date getPassportDateExpire() {
        return passportDateExpire;
    }

    public void setPassportDateExpire(Date passportDateExpire) {
        this.passportDateExpire = passportDateExpire;
    }


}
