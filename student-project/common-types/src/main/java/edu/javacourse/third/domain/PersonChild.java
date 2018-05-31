package edu.javacourse.third.domain;

import edu.javacourse.third.adapter.StudentDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;

/**
 * Created by antonsaburov on 06.03.17.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonChild extends Person implements Serializable
{
    @XmlElement(name="number",required = true)
    private String birthDocument;

    public String getBirthDocument() {
        return birthDocument;
    }

    public void setBirthDocument(String birthDocument) {
        this.birthDocument = birthDocument;
    }
}
