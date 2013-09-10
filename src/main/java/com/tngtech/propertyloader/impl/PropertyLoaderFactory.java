package com.tngtech.propertyloader.impl;

import com.tngtech.infrastructure.io.CharacterMatcher;
import com.tngtech.infrastructure.io.charactermatchers.AsciiCharacterMatcher;
import com.tngtech.infrastructure.io.charactermatchers.CompoundMatcher;
import com.tngtech.infrastructure.io.charactermatchers.InverseMatcher;
import com.tngtech.infrastructure.io.charactermatchers.WhitespaceMatcher;
import com.tngtech.propertyloader.impl.helpers.ReaderHelper;

public class PropertyLoaderFactory {
    public OrderedProperties getOrderedProperties(){
        return new OrderedProperties();
    }
    public OrderedPropertiesLoader getOrderedPropertiesLoader(){
        return new OrderedPropertiesLoader(this);
    }
    public ReaderHelper getReaderHelper(){
        return new ReaderHelper();
    }
    public CharacterMatcher getWhitespaceMatcher(boolean matchEndLines){
        return new WhitespaceMatcher(matchEndLines);
    }
    public CharacterMatcher getAsciiCharacterMatcher(String character){
        return new AsciiCharacterMatcher(character);
    }
    public CharacterMatcher getInverseMatcher(CharacterMatcher characterMatcher){
        return new InverseMatcher(characterMatcher);
    }
    public CharacterMatcher getCompoundCharacterMatcher(CharacterMatcher[] characterMatchers){
        return new CompoundMatcher(characterMatchers);
    }
}
