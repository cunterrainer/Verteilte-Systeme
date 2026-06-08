package de.othr.vs.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExampleEntity {

    private String attrib1;
    private int attrib2;

    public ExampleEntity() {}


    public String getAttrib1() {
        return attrib1;
    }

    public void setAttrib1(String attrib1) {
        this.attrib1 = attrib1;
    }

    public int getAttrib2() {
        return attrib2;
    }

    public void setAttrib2(int attrib2) {
        this.attrib2 = attrib2;
    }
}