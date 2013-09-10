package com.tngtech.propertyloader.impl;

import com.tngtech.infrastructure.io.CharacterMatcher;
import com.tngtech.infrastructure.io.ExtendedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderedPropertiesLoader {

    private PropertyLoaderFactory propertyLoaderFactory;
    private ExtendedReader extendedReader;
    private CharacterMatcher WHITESPACE;
    private CharacterMatcher LINE_SEPARATOR;
    private CharacterMatcher NOT_LINE_SEPARATOR;
    private CharacterMatcher KEY_VALUE_SEPARATOR;
    private CharacterMatcher SEPARATOR;
    private CharacterMatcher COMMENT;
    private CharacterMatcher WHITESPACE_OR_SEPARATOR;

    public OrderedPropertiesLoader(PropertyLoaderFactory propertyLoaderFactory){
        this.propertyLoaderFactory = propertyLoaderFactory;
        WHITESPACE = propertyLoaderFactory.getWhitespaceMatcher(false);
        LINE_SEPARATOR = propertyLoaderFactory.getAsciiCharacterMatcher("\n\r");
        NOT_LINE_SEPARATOR = propertyLoaderFactory.getInverseMatcher(LINE_SEPARATOR);
        KEY_VALUE_SEPARATOR = propertyLoaderFactory.getAsciiCharacterMatcher("=:");
        SEPARATOR = propertyLoaderFactory.getAsciiCharacterMatcher("=:\r\n");
        COMMENT = propertyLoaderFactory.getAsciiCharacterMatcher("#!");
        CharacterMatcher[] whitespaceOrSeperator = {WHITESPACE, SEPARATOR};
        WHITESPACE_OR_SEPARATOR = propertyLoaderFactory.getCompoundCharacterMatcher(whitespaceOrSeperator);
    }

    public OrderedProperties loadOrderedPropertiesFromStream(InputStream stream, String encoding) {
        try{
            extendedReader = propertyLoaderFactory.getReaderHelper().getExtendedReaderFromInputStream(stream, encoding);
            Map<String, String> properties = new LinkedHashMap<String, String>();

            while (!isAtEndOfStream()) {
                // we are at the beginning of a line.
                // check whether it is a comment and if it is, skip it
                int nextChar = extendedReader.peek();
                if (COMMENT.matches((char) nextChar)) {
                    skipCharacters(NOT_LINE_SEPARATOR);
                    continue;
                }

                skipCharacters(WHITESPACE);
                if (!isAtEndOfLine()) {
                    // this line does not consist only of whitespace. the next word
                    // is the key
                    String key = readQuotedLine(WHITESPACE_OR_SEPARATOR);
                    skipCharacters(WHITESPACE);

                    // if the next char is a key-value separator, read it and skip
                    // the following spaces
                    nextChar = extendedReader.peek();
                    if (nextChar > 0
                            && KEY_VALUE_SEPARATOR.matches((char) nextChar)) {
                        extendedReader.read();
                        skipCharacters(WHITESPACE);
                    }

                    // finally, read the value
                    String value = readQuotedLine(LINE_SEPARATOR);

                    properties.put(key, value);
                }
                skipCharacters(LINE_SEPARATOR);
            }
            return new OrderedProperties(properties);
        }
        catch(UnsupportedEncodingException e){

        }
        catch (IOException e){

        }

        return new OrderedProperties();
    }

    private boolean isAtEndOfStream()
            throws IOException {
        int nextChar = extendedReader.peek();
        return nextChar < 0;
    }

    private boolean isAtEndOfLine()
            throws IOException {
        int nextChar = extendedReader.peek();
        if (nextChar < 0) {
            return true;
        }
        return LINE_SEPARATOR.matches((char) nextChar);
    }

    public synchronized boolean isEndOfStream() throws IOException {
        return extendedReader.peek() < 0;
    }

    public synchronized void skipCharacters(CharacterMatcher matcher) throws IOException {
        while (true) {
            if (isEndOfStream()) {
                break;
            }
            char ch = (char) extendedReader.peek();
            if (!matcher.matches(ch)) {
                break;
            }
            extendedReader.read();
        }
    }

    private String readQuotedLine(CharacterMatcher terminators)
            throws IOException {
        StringBuffer buf = new StringBuffer();

        while (true) {
            // see what the next char is
            int nextChar = extendedReader.peek();

            // if at end of stream or the char is one of the terminators, stop
            if (nextChar < 0 || terminators.matches((char) nextChar)) {
                break;
            }

            //try {
                // read the char (and possibly unquote it)
                char ch = readQuotedChar();
                buf.append(ch);
            //} catch (IgnoreCharacterException e) {
            //    LOG.trace("readQuotedLine() - ignoring IgnoreCharacterException as no character was read");
            //}
        }

        return buf.toString();
    }

    /**
     * It is ok when FindBugs complains about this method, as the fallthrough is intended.
     */
    private char readQuotedChar()
            throws IOException {
        int nextChar = extendedReader.read();
        if (nextChar < 0) {
            //throw new IgnoreCharacterException();
        }
        char ch = (char) nextChar;

        // if the char is not the quotation char, simply return it
        if (ch != '\\') {
            return ch;
        }

        // the character is a quotation character. unquote it
        nextChar = extendedReader.read();

        // if at the end of the stream, stop
        if (nextChar < 0) {
            //throw new IgnoreCharacterException();
        }

        ch = (char) nextChar;
        switch (ch) {
            case 'u':
                char res = 0;
                for (int i = 0; i < 4; i++) {
                    nextChar = extendedReader.read();
                    if (nextChar < 0) {
                        throw new IllegalArgumentException(
                                "Malformed \\uxxxx encoding.");
                    }
                    char digitChar = (char) nextChar;
                    int digit = "0123456789ABCDEF"
                            .indexOf(Character.toUpperCase(digitChar));
                    if (digit < 0) {
                        throw new IllegalArgumentException(
                                "Malformed \\uxxxx encoding.");
                    }
                    res = (char) (res * 16 + digit);
                }
                return res;

            case '\r':
                // if the next char is \n, read it and fall through
                nextChar = extendedReader.peek();
                if (nextChar == '\n') {
                    extendedReader.read();
                }
                skipCharacters(WHITESPACE);
                //throw new IgnoreCharacterException();
            case '\n':
                skipCharacters(WHITESPACE);
                //throw new IgnoreCharacterException();

            case 't':
                return '\t';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            default:
                return ch;
        }
    }

}
