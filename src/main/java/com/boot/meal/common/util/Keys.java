package com.boot.meal.common.util;

import com.boot.meal.security.exception.WeakKeyException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/***
 * Copyright (C) 2014 jsonwebtoken.io
 * io.jsonwebtoken.security.Keys custom class
 */
public class Keys {

    //purposefully ordered higher to lower:
    private static final List<Integer> PREFERRED_HMAC_ALGS_MIN_KEY_LENGTH = Collections.unmodifiableList(Arrays.asList(
            512, 284, 256));

    //prevent instantiation
    private Keys() {
    }

    public static SecretKey hmacShaKeyFor(byte[] bytes) throws InvalidKeyException {

        if (bytes == null) {
            throw new InvalidKeyException("SecretKey byte array cannot be null.");
        }

        int bitLength = bytes.length * 8;

        for (int alg : PREFERRED_HMAC_ALGS_MIN_KEY_LENGTH) {
            if (bitLength >= alg) {
                return new SecretKeySpec(bytes, "HmacSHA" + alg);
            }
        }

        String msg = "The specified key byte array is " + bitLength + " bits which " +
                "is not secure enough for any JWT HMAC-SHA algorithm.  The JWT " +
                "JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a " +
                "size >= 256 bits (the key size must be greater than or equal to the hash " +
                "output size).  Consider using the " + Keys.class.getName() + "#secretKeyFor(SignatureAlgorithm) method " +
                "to create a key guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See " +
                "https://tools.ietf.org/html/rfc7518#section-3.2 for more information.";
        throw new WeakKeyException(msg);
    }
}
