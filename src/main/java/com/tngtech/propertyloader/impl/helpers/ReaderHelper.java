package com.tngtech.propertyloader.impl.helpers;

import com.tngtech.infrastructure.io.ExtendedReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReaderHelper
{
    public ExtendedReader getExtendedReaderFromInputStream(InputStream stream, String encoding) throws UnsupportedEncodingException
    {
        return new ExtendedReader(new BufferedReader(new InputStreamReader(stream, encoding)));
    }
}
