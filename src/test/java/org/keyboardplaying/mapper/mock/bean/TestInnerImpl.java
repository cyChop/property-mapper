package org.keyboardplaying.mapper.mock.bean;

import org.keyboardplaying.mapper.annotation.Metadata;

// XXX Javadoc
/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class TestInnerImpl implements TestInnerBean {

    @Metadata(value = "hello_world_inner", mandatory = true)
    private String hello;

    @Override
    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
