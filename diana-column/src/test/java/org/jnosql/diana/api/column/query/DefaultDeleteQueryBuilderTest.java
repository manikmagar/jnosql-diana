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
package org.jnosql.diana.api.column.query;


import org.hamcrest.Matchers;
import org.jnosql.diana.api.Condition;
import org.jnosql.diana.api.TypeReference;
import org.jnosql.diana.api.column.Column;
import org.jnosql.diana.api.column.ColumnCondition;
import org.jnosql.diana.api.column.ColumnDeleteQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.jnosql.diana.api.column.ColumnCondition.eq;
import static org.jnosql.diana.api.column.ColumnCondition.gt;
import static org.jnosql.diana.api.column.query.ColumnQueryBuilder.delete;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DefaultDeleteQueryBuilderTest {

    @Test(expected = NullPointerException.class)
    public void shouldReturnErrorWhenHasNullElementInSelect() {
        delete("column", "column", null);
    }

    @Test
    public void shouldDelete() {
        String columnFamily = "columnFamily";
        ColumnDeleteQuery query = delete().from(columnFamily).build();
        assertTrue(query.getColumns().isEmpty());
        assertFalse(query.getCondition().isPresent());
        assertEquals(columnFamily, query.getColumnFamily());
    }

    @Test
    public void shouldDeleteColumns() {
        String columnFamily = "columnFamily";
        ColumnDeleteQuery query = delete("column", "column2").from(columnFamily).build();
        assertThat(query.getColumns(), containsInAnyOrder("column", "column2"));
        assertFalse(query.getCondition().isPresent());
        assertEquals(columnFamily, query.getColumnFamily());
    }


    @Test(expected = NullPointerException.class)
    public void shouldReturnErrorWhenFromIsNull() {
        delete().from(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnErrorWhenWhereConditionIsNull() {
        String columnFamily = "columnFamily";
        delete().from(columnFamily).where((ColumnCondition) null);
    }

    @Test
    public void shouldSelectWhere() {
        String columnFamily = "columnFamily";
        ColumnCondition condition = eq(Column.of("name", "Ada"));
        ColumnDeleteQuery query = delete().from(columnFamily).where(condition).build();
        assertTrue(query.getCondition().isPresent());
        ColumnCondition conditionWhere = query.getCondition().get();
        assertEquals(condition, conditionWhere);
    }

    @Test
    public void shouldSelectWhereAnd() {
        String columnFamily = "columnFamily";
        ColumnCondition condition = eq(Column.of("name", "Ada"));
        ColumnDeleteQuery query = delete().from(columnFamily).where(condition).and(gt(Column.of("age", 10))).build();
        assertTrue(query.getCondition().isPresent());
        ColumnCondition expected = eq(Column.of("name", "Ada")).and(gt(Column.of("age", 10)));
        assertEquals(expected, query.getCondition().get());
    }

    @Test
    public void shouldSelectWhereOr() {
        String columnFamily = "columnFamily";
        ColumnCondition condition = eq(Column.of("name", "Ada"));
        ColumnDeleteQuery query = delete().from(columnFamily).where(condition).or(gt(Column.of("age", 10))).build();
        assertTrue(query.getCondition().isPresent());
        ColumnCondition expected = eq(Column.of("name", "Ada")).or(gt(Column.of("age", 10)));
        assertEquals(expected, query.getCondition().get());
    }


    @Test(expected = NullPointerException.class)
    public void shouldReturnErrorWhenSelectWhereAndConditionIsNull() {
        String columnFamily = "columnFamily";
        ColumnCondition condition = eq(Column.of("name", "Ada"));
        delete().from(columnFamily).where(condition).and((ColumnCondition) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnErrorWhenSelectWhereOrConditionIsNull() {
        String columnFamily = "columnFamily";
        ColumnCondition condition = eq(Column.of("name", "Ada"));
        delete().from(columnFamily).where(condition).or((ColumnCondition) null);
    }


    @Test
    public void shouldSelectWhereNameEq() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").eq(name).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.EQUALS, condition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(name, column.get());

    }

    @Test
    public void shouldSelectWhereNameLike() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").like(name).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.LIKE, condition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(name, column.get());
    }

    @Test
    public void shouldSelectWhereNameGt() {
        String columnFamily = "columnFamily";
        Number value = 10;
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").gt(value).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.GREATER_THAN, condition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(value, column.get());
    }

    @Test
    public void shouldSelectWhereNameGte() {
        String columnFamily = "columnFamily";
        Number value = 10;
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").gte(value).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.GREATER_EQUALS_THAN, condition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(value, column.get());
    }

    @Test
    public void shouldSelectWhereNameLt() {
        String columnFamily = "columnFamily";
        Number value = 10;
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").lt(value).build();
        ColumnCondition columnCondition = query.getCondition().get();

        Column column = columnCondition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.LESSER_THAN, columnCondition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(value, column.get());
    }

    @Test
    public void shouldSelectWhereNameLte() {
        String columnFamily = "columnFamily";
        Number value = 10;
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").lte(value).build();
        ColumnCondition columnCondition = query.getCondition().get();

        Column column = columnCondition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.LESSER_EQUALS_THAN, columnCondition.getCondition());
        assertEquals("name", column.getName());
        assertEquals(value, column.get());
    }

    @Test
    public void shouldSelectWhereNameBetween() {
        String columnFamily = "columnFamily";
        Number valueA = 10;
        Number valueB = 20;
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").between(valueA, valueB).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();

        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.BETWEEN, condition.getCondition());
        assertEquals("name", column.getName());
        Assert.assertThat(column.get(new TypeReference<List<Number>>() {
        }), Matchers.contains(10, 20));
    }

    @Test
    public void shouldSelectWhereNameNot() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").not().eq(name).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        ColumnCondition negate = column.get(ColumnCondition.class);
        assertTrue(query.getColumns().isEmpty());
        assertEquals(columnFamily, query.getColumnFamily());
        assertEquals(Condition.NOT, condition.getCondition());
        assertEquals(Condition.EQUALS, negate.getCondition());
        assertEquals("name", negate.getColumn().getName());
        assertEquals(name, negate.getColumn().get());
    }


    @Test
    public void shouldSelectWhereNameAnd() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").eq(name).and("age").gt(10).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.AND, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }

    @Test
    public void shouldSelectWhereNameOr() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").eq(name).or("age").gt(10).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.OR, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }


    @Test
    public void shouldSelectWhereNameAnd2() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";

        ColumnDeleteQuery query = delete().from(columnFamily).where("name").eq(name)
                .and(ColumnCondition.gt(Column.of("age", 10))).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.AND, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }

    @Test
    public void shouldSelectWhereNameOr2() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where("name").eq(name)
                .or(ColumnCondition.gt(Column.of("age", 10))).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.OR, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }


    @Test
    public void shouldSelectWhereNameAnd3() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";

        ColumnDeleteQuery query = delete().from(columnFamily).where(ColumnCondition.eq(Column.of("name", name))
        ).and("age").gt(10).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.AND, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }

    @Test
    public void shouldSelectWhereNameOr3() {
        String columnFamily = "columnFamily";
        String name = "Ada Lovelace";
        ColumnDeleteQuery query = delete().from(columnFamily).where(ColumnCondition.eq(Column.of("name", name)))
                .or("age").gt(10).build();
        ColumnCondition condition = query.getCondition().get();

        Column column = condition.getColumn();
        List<ColumnCondition> conditions = column.get(new TypeReference<List<ColumnCondition>>() {
        });
        assertEquals(Condition.OR, condition.getCondition());
        assertThat(conditions, Matchers.containsInAnyOrder(ColumnCondition.eq(Column.of("name", name)),
                ColumnCondition.gt(Column.of("age", 10))));
    }

}
