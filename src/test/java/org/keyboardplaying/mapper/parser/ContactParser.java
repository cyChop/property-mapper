package org.keyboardplaying.mapper.parser;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.keyboardplaying.mapper.exception.ParsingException;

/**
 * Test {@link ElaborateParser}.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ContactParser implements ElaborateParser<String> {

    private static final String NAME_FIELD = "somebody_s_name";
    private static final String PHONE_FIELD = "somebody_s_phone";

    private static final String PARSING_PATTERN = "(.*)(?:\\s+\\((.*)\\))?";

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.ElaborateParser#fromMap(java.util.Map)
     */
    @Override
    public String fromMap(Map<String, String> map) throws ParsingException {
        return String.format("%s (%s)", map.get(NAME_FIELD), map.get(PHONE_FIELD));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.mapper.parser.ElaborateParser#toMap(java.lang.Object, java.util.Map)
     */
    @Override
    public void toMap(String value, Map<String, String> map) throws ParsingException {
        String name = null;
        String phone = null;

        if (value != null) {
            Matcher matcher = Pattern.compile(PARSING_PATTERN).matcher(value);
            if (!matcher.matches()) {
                throw new ParsingException("The contact has an incorrect format.");
            }
            name = matcher.group(1);
            phone = matcher.group(2);
        }

        map.put(NAME_FIELD, name);
        map.put(PHONE_FIELD, phone);
    }
}
