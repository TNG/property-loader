package com.tngtech.infrastructure.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.tngtech.infrastructure.exception.Reject;
import com.tngtech.infrastructure.exception.SystemException;
import org.apache.log4j.Logger;

/**
 * Utility functions if you want to work with serialization :-)
 */
public abstract class SerializationUtil {
    private static final Logger LOG = Logger.getLogger(SerializationUtil.class);

    public static byte[] serialize(Object toSerialize) {
        Reject.ifNull("can't serialize null", toSerialize);
        if (toSerialize instanceof Serializable) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(toSerialize);
                objectOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (IOException ex) {
                throw wrapException("fatal error serializing object", ex);
            }
        }
        String error = "fatal error serializing object: class " + toSerialize + " does not implement java.io.Serializable!";
        LOG.fatal("serialize() - " + error);
        throw new SystemException(error);
    }

    public static Serializable deserialize(byte[] toDeserialize) {
        Reject.ifNull("can't deserialize null", toDeserialize);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(toDeserialize);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Serializable) ois.readObject();
        } catch (IOException ex) {
            throw wrapException("unable to de-serialize byte array", ex);
        } catch (ClassNotFoundException ex) {
            throw wrapException("unable to de-serialize byte array", ex);
        }
    }

    private static SystemException wrapException(String error, Exception e) {
        LOG.error("wrapException() " + error, e);
        return new SystemException(error, e);
    }
}
