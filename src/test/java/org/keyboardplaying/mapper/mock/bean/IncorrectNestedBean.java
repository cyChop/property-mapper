package org.keyboardplaying.mapper.mock.bean;

import org.keyboardplaying.mapper.annotation.Nested;

/**
 * Test bean.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IncorrectNestedBean {

    @Nested
    private TestInnerBean inner;

    /**
     * Returns the inner for this instance.
     *
     * @return the inner
     */
    public TestInnerBean getInner() {
        return inner;
    }

    /**
     * Sets the inner for this instance.
     *
     * @param inner
     *            the new inner
     */
    public void setInner(TestInnerBean inner) {
        this.inner = inner;
    }
}
