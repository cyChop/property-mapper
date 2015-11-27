package org.keyboardplaying.mapper.mock.bean;

import org.keyboardplaying.mapper.annotation.Metadata;

/**
 * A subclass to test the interpretation of annotations in an inheritance context..
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class TestSubBean extends TestBean {

    @Metadata(value = "hello_world_sub", mandatory = true)
    private String helloSub;

    /**
     * Returns the sub hello for this instance.
     *
     * @return the sub hello
     */
    public String getHelloSub() {
        return helloSub;
    }

    /**
     * Sets the sub hello for this instance.
     *
     * @param helloSub
     *            the new sub hello
     */
    public void setHelloSub(String helloSub) {
        this.helloSub = helloSub;
    }
}
