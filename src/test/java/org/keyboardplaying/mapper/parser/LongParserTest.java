package org.keyboardplaying.mapper.parser;

/**
 * {@link NumberParserTest} implementation for {@link LongParser}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class LongParserTest extends NumberParserTest<Long> {

    /**
     * Creates a new instance.
     */
    public LongParserTest() {
        super(new LongParser());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.NumberParserTest#getValue(int)
     */
    @Override
    protected Long getValue(int number) {
        return (long) number;
    }
}
