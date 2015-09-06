package org.keyboardplaying.mapper.parser;

/**
 * {@link NumberParserTest} implementation for {@link IntegerParser}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IntegerParserTest extends NumberParserTest<Integer> {

    /** Creates a new instance. */
    public IntegerParserTest() {
        super(new IntegerParser());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.NumberParserTest#getValue(int)
     */
    @Override
    protected Integer getValue(int number) {
        return Integer.valueOf(number);
    }
}
