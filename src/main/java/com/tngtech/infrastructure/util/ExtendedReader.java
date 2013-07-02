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

import java.io.IOException;
import java.io.Reader;

/**
 * A Reader that provides some additional functionality, such as peek().
 *
 * @author mb
 * @since 4.0
 */
public class ExtendedReader extends Reader {
    private Reader _reader;
    private boolean _hasBufferedChar = false;
    private char _bufferedChar;

    /**
     * Creates a new extended reader that reads from the provided object.
     *
     * @param in the Reader to get data from
     */
    public ExtendedReader(Reader in) {
        _reader = in;
    }

    /**
     * Returns the next character in the stream without actually comitting the read.
     * Multiple consequtive invocations of this method should return the same value.
     *
     * @return the next character waiting in the stream or -1 if the end of the stream is reached
     * @throws java.io.IOException if an error occurs
     */
    public synchronized int peek() throws IOException {
        if (!_hasBufferedChar) {
            int bufferedChar = read();
            if (bufferedChar < 0) {
                return bufferedChar;
            }
            _bufferedChar = (char) bufferedChar;
            _hasBufferedChar = true;
        }
        return _bufferedChar;
    }

    /**
     * Determines whether the end of the stream is reached.
     *
     * @return true if at the end of stream
     * @throws java.io.IOException if an error occurs
     */
    public synchronized boolean isEndOfStream() throws IOException {
        return peek() < 0;
    }

    /**
     * Skips the next characters until a character that does not match the provided rule is reached.
     *
     * @param matcher the object determining whether a character should be skipped
     * @throws java.io.IOException if an error occurs
     */
    public synchronized void skipCharacters(CharacterMatcher matcher) throws IOException {
        while (true) {
            if (isEndOfStream()) {
                break;
            }
            char ch = (char) peek();
            if (!matcher.matches(ch)) {
                break;
            }
            read();
        }
    }

    /**
     * Reads the next characters until a character that does not match the provided rule is reached.
     *
     * @param matcher the object determining whether a character should be read
     * @return the string of characters read
     * @throws java.io.IOException if an error occurs
     */
    public synchronized String readCharacters(CharacterMatcher matcher) throws IOException {
        StringBuffer buf = new StringBuffer();
        while (true) {
            if (isEndOfStream()) {
                break;
            }
            char ch = (char) peek();
            if (!matcher.matches(ch)) {
                break;
            }
            buf.append(read());
        }
        return buf.toString();
    }

    /**
     * @see java.io.FilterReader#read(char[],int,int)
     */
    @Override
    public synchronized int read(char[] cbuf, int off, int len) throws IOException {
        int offset = off;
        if (len <= 0) {
            return 0;
        }
        int readLength = len;

        boolean extraChar = _hasBufferedChar;
        if (_hasBufferedChar) {
            _hasBufferedChar = false;
            cbuf[offset++] = _bufferedChar;
            readLength--;
        }

        int read = _reader.read(cbuf, offset, readLength);
        if (extraChar) {
            read++;
        }
        return read;
    }

    /**
     * @see java.io.FilterReader#ready()
     */
    @Override
    public synchronized boolean ready() throws IOException {
        if (_hasBufferedChar) {
            return true;
        }
        return _reader.ready();
    }

    /**
     * @see java.io.FilterReader#markSupported()
     */
    @Override
    public synchronized boolean markSupported() {
        return false;
    }

    /**
     * @see java.io.FilterReader#reset()
     */
    @Override
    public synchronized void reset() throws IOException {
        _hasBufferedChar = false;
        _reader.reset();
    }

    /**
     * @see java.io.FilterReader#skip(long)
     */
    @Override
    public synchronized long skip(long n) throws IOException {
        long skipChars = n;
        if (_hasBufferedChar && skipChars > 0) {
            _hasBufferedChar = false;
            skipChars--;
        }
        return _reader.skip(skipChars);
    }

    /**
     * @see java.io.Reader#close()
     */
    @Override
    public synchronized void close() throws IOException {
        _hasBufferedChar = false;
        _reader.close();
    }

}
