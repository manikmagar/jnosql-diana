/*
 *
 *  Copyright (c) 2017 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 *
 */

package org.jnosql.diana.api.reader;

import org.jnosql.diana.api.ValueReader;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;


public class BigIntegerReaderTest {

    private ValueReader valueReader;

    @Before
    public void init() {
        valueReader = new BigIntegerValueReader();
    }

    @Test
    public void shouldValidateCompatibility() {
        assertTrue(valueReader.isCompatible(BigInteger.class));
        assertFalse(valueReader.isCompatible(AtomicBoolean.class));
        assertFalse(valueReader.isCompatible(Boolean.class));
    }

    @Test
    public void shouldConvert() {
        BigInteger bigInteger = BigInteger.TEN;
        assertEquals(bigInteger, valueReader.read(BigInteger.class, bigInteger));
        assertEquals(bigInteger, valueReader.read(BigInteger.class, 10.00));
        assertEquals(bigInteger, valueReader.read(BigInteger.class, "10"));
    }


}
