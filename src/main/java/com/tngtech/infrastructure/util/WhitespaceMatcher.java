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

/**
 * Matches a whitespace character.
 *
 * @author mb
 * @since 4.0
 */
public class WhitespaceMatcher implements CharacterMatcher {

    private boolean _matchEndLines;

    /**
     * Creates an object that matches all whitespace characters, line
     * terminators included.
     */
    public WhitespaceMatcher() {
        this(true);
    }

    /**
     * Creates an object that matches whitespace characters. Line terminators
     * are included only if this is specified.
     *
     * @param matchEndLines match line terminators as well
     */
    public WhitespaceMatcher(boolean matchEndLines) {
        _matchEndLines = matchEndLines;
    }

    /**
     * Matches whitespace characters. Line terminators may be matched depending
     * on the constructor used to create the object.
     *
     * @see CharacterMatcher#matches(char)
     */
    @Override
    public boolean matches(char ch) {
        if (!_matchEndLines && (ch == '\n' || ch == '\r')) {
            return false;
        }

        return Character.isWhitespace(ch);
    }
}
