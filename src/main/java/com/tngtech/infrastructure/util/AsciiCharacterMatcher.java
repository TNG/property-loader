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
 * An object for matching a set of provided ASCII characters.
 *
 * @author mb
 * @since 4.0
 */
public class AsciiCharacterMatcher implements CharacterMatcher {
    private boolean[] _charMap;

    /**
     * Create a new ASCII character matcher for identifying the set of provided ASCII characters.
     *
     * @param chars the character that this matcher should identify
     */
    public AsciiCharacterMatcher(String chars) {
        _charMap = new boolean[128];
        for (int i = 0; i < chars.length(); i++) {
            char ch = chars.charAt(i);
            if (ch > 127) {
                continue;
            }
            _charMap[ch] = true;
        }
    }

    /**
     * Match the characters provided in the constructor.
     *
     * @see CharacterMatcher#matches(char)
     */
    @Override
    public boolean matches(char ch) {
        if (ch > 127) {
            return false;
        }
        return _charMap[ch];
    }
}
