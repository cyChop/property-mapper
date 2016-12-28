package org.keyboardplaying.mapper.mock.bean;

import org.keyboardplaying.mapper.annotation.Metadata;

/**
 * A bean designed for testing the behavior of the mapping and unmapping engines when default values are concerned.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
public class TestDefaultedBean {

    @Metadata(value = "the_answer", defaultValue = "42")
    private Integer theAnswer;

    @Metadata(value = "not_null_string", blankDefaultValue = true, mandatory = true)
    private String notNullString;

    @Metadata(value = "the_doctor", defaultMetadata = "Doctor Who?", mandatory = true)
    private String theDoctor;

    @Metadata(value = "the_companion", blankDefaultMetadata = true)
    private String theCompanion;

    public Integer getTheAnswer() {
        return theAnswer;
    }

    public void setTheAnswer(Integer theAnswer) {
        this.theAnswer = theAnswer;
    }

    public String getNotNullString() {
        return notNullString;
    }

    public void setNotNullString(String notNullString) {
        this.notNullString = notNullString;
    }

    public String getTheDoctor() {
        return theDoctor;
    }

    public void setTheDoctor(String theDoctor) {
        this.theDoctor = theDoctor;
    }

    public String getTheCompanion() {
        return theCompanion;
    }

    public void setTheCompanion(String theCompanion) {
        this.theCompanion = theCompanion;
    }
}
