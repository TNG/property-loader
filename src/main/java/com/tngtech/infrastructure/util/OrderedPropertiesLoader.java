
// Copyright 2004, 2005 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.tngtech.infrastructure.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.tngtech.infrastructure.exception.SystemException;
import org.apache.log4j.Logger;

/**
 * An object that loads a properties file from the provided input stream or
 * reader. This class reads the property file exactly like java.util.Properties,
 * except that it also allows the files to use an encoding other than ISO-8859-1
 * and all non-ASCII characters are read correctly using the given encoding. In
 * short, non-latin characters no longer need to be quoted using native2ascii.
 *
 * @author mb
 * @since 4.0
 */
public class OrderedPropertiesLoader {
    private static final Logger LOG = Logger.getLogger(OrderedPropertiesLoader.class);

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private static final CharacterMatcher WHITESPACE = new WhitespaceMatcher(false);
    private static final CharacterMatcher LINE_SEPARATOR = new AsciiCharacterMatcher("\n\r");
    private static final CharacterMatcher NOT_LINE_SEPARATOR = new InverseMatcher(LINE_SEPARATOR);
    private static final CharacterMatcher KEY_VALUE_SEPARATOR = new AsciiCharacterMatcher("=:");
    private static final CharacterMatcher SEPARATOR = new AsciiCharacterMatcher("=:\r\n");
    private static final CharacterMatcher COMMENT = new AsciiCharacterMatcher("#!");
    private static final CharacterMatcher WHITESPACE_OR_SEPARATOR = new CompoundMatcher(new CharacterMatcher[]{WHITESPACE, SEPARATOR});

    private ExtendedReader _extendedReader;

    /**
     * Creates a new loader that will load the properties from the given input
     * stream using the provided character encoding.
     *
     * @param ins      the input stream to load the properties from
     * @param encoding the character encoding the be used when reading from the
     *                 stream
     * @throws java.io.UnsupportedEncodingException
     *          if the name of the encoding cannot be recognized
     */
    public OrderedPropertiesLoader(InputStream ins, String encoding)
            throws UnsupportedEncodingException {
        this(new InputStreamReader(ins, encoding));
    }

    /**
     * Creates a new loader that will load the properties from the given reader.
     *
     * @param reader the Reader to load the properties from
     */
    public OrderedPropertiesLoader(Reader reader) {
        _extendedReader = new ExtendedReader(new BufferedReader(reader));
    }

    /**
     * Read the properties from the provided stream and store them into the
     * given map.
     *
     * @param properties the map where the properties will be stored
     * @throws java.io.IOException if an error occurs
     */
    public void load(Map<String, String> properties)
            throws IOException {
        while (!isAtEndOfStream()) {
            // we are at the beginning of a line.
            // check whether it is a comment and if it is, skip it
            int nextChar = _extendedReader.peek();
            if (COMMENT.matches((char) nextChar)) {
                _extendedReader.skipCharacters(NOT_LINE_SEPARATOR);
                continue;
            }

            _extendedReader.skipCharacters(WHITESPACE);
            if (!isAtEndOfLine()) {
                // this line does not consist only of whitespace. the next word
                // is the key
                String key = readQuotedLine(WHITESPACE_OR_SEPARATOR);
                _extendedReader.skipCharacters(WHITESPACE);

                // if the next char is a key-value separator, read it and skip
                // the following spaces
                nextChar = _extendedReader.peek();
                if (nextChar > 0
                        && KEY_VALUE_SEPARATOR.matches((char) nextChar)) {
                    _extendedReader.read();
                    _extendedReader.skipCharacters(WHITESPACE);
                }

                // finally, read the value
                String value = readQuotedLine(LINE_SEPARATOR);

                properties.put(key, value);
            }
            _extendedReader.skipCharacters(LINE_SEPARATOR);
        }
    }

    private boolean isAtEndOfStream()
            throws IOException {
        int nextChar = _extendedReader.peek();
        return nextChar < 0;
    }

    private boolean isAtEndOfLine()
            throws IOException {
        int nextChar = _extendedReader.peek();
        if (nextChar < 0) {
            return true;
        }
        return LINE_SEPARATOR.matches((char) nextChar);
    }

    private String readQuotedLine(CharacterMatcher terminators)
            throws IOException {
        StringBuffer buf = new StringBuffer();

        while (true) {
            // see what the next char is
            int nextChar = _extendedReader.peek();

            // if at end of stream or the char is one of the terminators, stop
            if (nextChar < 0 || terminators.matches((char) nextChar)) {
                break;
            }

            try {
                // read the char (and possibly unquote it)
                char ch = readQuotedChar();
                buf.append(ch);
            } catch (IgnoreCharacterException e) {
                LOG.trace("readQuotedLine() - ignoring IgnoreCharacterException as no character was read");
            }
        }

        return buf.toString();
    }

    /**
     * It is ok when FindBugs complains about this method, as the fallthrough is intended.
     */
    private char readQuotedChar()
            throws IOException {
        int nextChar = _extendedReader.read();
        if (nextChar < 0) {
            throw new IgnoreCharacterException();
        }
        char ch = (char) nextChar;

        // if the char is not the quotation char, simply return it
        if (ch != '\\') {
            return ch;
        }

        // the character is a quotation character. unquote it
        nextChar = _extendedReader.read();

        // if at the end of the stream, stop
        if (nextChar < 0) {
            throw new IgnoreCharacterException();
        }

        ch = (char) nextChar;
        switch (ch) {
            case 'u':
                char res = 0;
                for (int i = 0; i < 4; i++) {
                    nextChar = _extendedReader.read();
                    if (nextChar < 0) {
                        throw new IllegalArgumentException(
                                "Malformed \\uxxxx encoding.");
                    }
                    char digitChar = (char) nextChar;
                    int digit = HEX_DIGITS
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
                nextChar = _extendedReader.peek();
                if (nextChar == '\n') {
                    _extendedReader.read();
                }
                _extendedReader.skipCharacters(WHITESPACE);
                throw new IgnoreCharacterException();
            case '\n':
                _extendedReader.skipCharacters(WHITESPACE);
                throw new IgnoreCharacterException();

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

    private static class IgnoreCharacterException extends SystemException {

        private static final long serialVersionUID = 8366308710256427596L;
    }
}
