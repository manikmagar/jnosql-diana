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
package org.jnosql.diana.api.document.query;


import org.jnosql.diana.api.document.DocumentDeleteQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DefaultDocumentnDeleteQueryTest {


    private DocumentDeleteQuery query;


    @Before
    public void setUp() {
        query = DocumentQueryBuilder.delete().from("columnFamily").build();
    }

    @Test(expected =  UnsupportedOperationException.class)
    public void shouldNotEditColumns() {
        List<String> documents = query.getDocuments();
        assertTrue(documents.isEmpty());
        documents.clear();
    }
}
