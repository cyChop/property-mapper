package org.keyboardplaying.mapper.mock.bean;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.keyboardplaying.mapper.Defaults;
import org.keyboardplaying.mapper.annotation.BooleanValues;
import org.keyboardplaying.mapper.annotation.Metadata;
import org.keyboardplaying.mapper.annotation.Nested;
import org.keyboardplaying.mapper.annotation.Temporal;
import org.keyboardplaying.mapper.annotation.Temporal.TemporalType;
import org.keyboardplaying.mapper.parser.ContactParser;

/**
 * Test bean.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class TestBean {

    @Metadata(value = "hello_world", defaultValue = "Didn't receive hello... :(", defaultMetadata = "Didn't send hello... :(")
    private String hello;

    @Metadata(value = "Do do do", mandatory = true)
    private String mandatory;

    @Metadata("some_bool")
    private boolean someBool;

    @Metadata("some_yesno")
    @BooleanValues(whenTrue = Defaults.BOOLEAN_YES, whenFalse = Defaults.BOOLEAN_NO)
    private boolean someYesNo;

    @Metadata("some_int")
    private int someInt;

    @Metadata("some_long")
    private Long someLong;

    @Metadata("some_bigint")
    private BigInteger someBig;

    @Metadata("some_even_more_important_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Metadata("some_important_date")
    @Temporal(TemporalType.DATETIME)
    private Calendar cal;

    @Nested
    private TestInnerImpl innerImpl;

    @Nested(className = "org.keyboardplaying.mapper.mock.bean.TestInnerImpl")
    private TestInnerBean innerItf;

    @Metadata(elaborate = ContactParser.class)
    private String contact;

    /**
     * Creates a new instance.
     */
    public TestBean() {
        super();
    }

    /**
     * Returns the hello for this instance.
     *
     * @return the hello
     */
    public String getHello() {
        return hello;
    }

    /**
     * Sets the hello for this instance.
     *
     * @param hello the new hello
     */
    public void setHello(String hello) {
        this.hello = hello;
    }

    /**
     * Returns the mandatory String for this instance.
     *
     * @return the mandatory String
     */
    public String getMandatory() {
        return mandatory;
    }

    /**
     * Sets the mandatory String for this instance.
     *
     * @param mandatory the mandatory String
     */
    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * Returns the someBool for this instance.
     *
     * @return the someBool
     */
    public boolean isSomeBool() {
        return someBool;
    }

    /**
     * Sets the someBool for this instance.
     *
     * @param someBool the new someBool
     */
    public void setSomeBool(boolean someBool) {
        this.someBool = someBool;
    }

    /**
     * Returns the yesNo for this instance.
     *
     * @return the yesNo
     */
    public boolean isSomeYesNo() {
        return someYesNo;
    }

    /**
     * Returns the yesNo for this instance.
     *
     * @param someYesNo the new yesNo
     */
    public void setSomeYesNo(boolean someYesNo) {
        this.someYesNo = someYesNo;
    }

    /**
     * Returns the someInt for this instance.
     *
     * @return the someInt
     */
    public int getSomeInt() {
        return someInt;
    }

    /**
     * Sets the someInt for this instance.
     *
     * @param someInt the new someInt
     */
    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    /**
     * Returns the someLong for this instance.
     *
     * @return the someLong
     */
    public Long getSomeLong() {
        return someLong;
    }

    /**
     * Sets the someLong for this instance.
     *
     * @param someLong the new someLong
     */
    public void setSomeLong(Long someLong) {
        this.someLong = someLong;
    }

    /**
     * Returns the someBig for this instance.
     *
     * @return the someBig
     */
    public BigInteger getSomeBig() {
        return someBig;
    }

    /**
     * Sets the someBig for this instance.
     *
     * @param someBig the new someBig
     */
    public void setSomeBig(BigInteger someBig) {
        this.someBig = someBig;
    }

    /**
     * Returns the date for this instance.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date for this instance.
     *
     * @param date the new date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the cal for this instance.
     *
     * @return the cal
     */
    public Calendar getCal() {
        return cal;
    }

    /**
     * Sets the cal for this instance.
     *
     * @param cal the new cal
     */
    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    /**
     * Returns the innerImpl for this instance.
     *
     * @return the innerImpl
     */
    public TestInnerImpl getInnerImpl() {
        return innerImpl;
    }

    /**
     * Sets the innerImpl for this instance.
     *
     * @param innerImpl the new innerImpl
     */
    public void setInnerImpl(TestInnerImpl innerImpl) {
        this.innerImpl = innerImpl;
    }

    /**
     * Returns the innerItf for this instance.
     *
     * @return the innerItf
     */
    public TestInnerBean getInnerItf() {
        return innerItf;
    }

    /**
     * Sets the innerItf for this instance.
     *
     * @param innerItf the new innerItf
     */
    public void setInnerItf(TestInnerBean innerItf) {
        this.innerItf = innerItf;
    }

    /**
     * Returns the contact for this instance.
     *
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact for this instance.
     *
     * @param contact the new contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }
}
