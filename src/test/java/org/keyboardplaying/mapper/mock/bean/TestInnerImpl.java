package org.keyboardplaying.mapper.mock.bean;

import org.keyboardplaying.mapper.annotation.Metadata;

/**
 * An interface implementation for testing correct interpretation of annotations in interface implementations.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class TestInnerImpl implements TestInnerBean {

    @Metadata(value = "hello_world_inner", mandatory = true)
    private String hello;

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.mock.bean.TestInnerBean#getHello()
     */
    @Override
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
}
