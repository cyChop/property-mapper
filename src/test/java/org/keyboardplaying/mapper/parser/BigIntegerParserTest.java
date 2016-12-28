package org.keyboardplaying.mapper.parser;

import java.math.BigInteger;

/**
 * {@link NumberParserTest} implementation for {@link BigIntegerParser}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class BigIntegerParserTest extends NumberParserTest<BigInteger> {

    /** Creates a new instance. */
    public BigIntegerParserTest() {
        super(new BigIntegerParser());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.NumberParserTest#getValue(int)
     */
    @Override
    protected BigInteger getValue(int number) {
        return BigInteger.valueOf(number);
    }
}
