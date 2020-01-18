package tech.tablesaw.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static tech.tablesaw.TableAssertions.assertTableContents;

import org.junit.jupiter.api.Test;
import tech.tablesaw.api.BooleanColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.FloatColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.DoubleColumnType;

public class TableTransposeTest {

  @Test
  void transposeDoubles() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    DoubleColumn value = DoubleColumn.create("value1", new double[]{ 1.0, 1.1, 1.2 });
    DoubleColumn value2 = DoubleColumn.create("value2", new double[]{ 2.0, 2.1, 2.2});

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"value1", 1.0, 1.1, 1.2},
            {"value2", 2.0, 2.1, 2.2},
        },
        result);
    assertEquals(testTable.name(), result.name());
    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeWithMissingData() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    DoubleColumn value = DoubleColumn.create("value1", new double[] { 1.0, DoubleColumnType.missingValueIndicator(), 1.2 });
    DoubleColumn value2 = DoubleColumn.create("value2", new double[] { 2.0, 2.1, 2.2 });

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"value1", 1.0, Double.NaN, 1.2},
            {"value2", 2.0, 2.1, 2.2},
        },
        result);

    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeFloats() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    FloatColumn value = FloatColumn.create("value1", new float[] { 1.0f, 1.1f, 1.2f });
    FloatColumn value2 = FloatColumn.create("value2", new float[] { 2.0f, 2.1f, 2.2f});

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();
    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeIntegers() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    IntColumn value = IntColumn.create("value1", new int[]{ 1, 2, 3 });
    IntColumn value2 = IntColumn.create("value2", new int[]{ 4, 5, 6 });

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"value1", 1, 2, 3},
            {"value2", 4, 5, 6}
        },
        result);

    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeLongs() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    LongColumn value = LongColumn.create("value1", new long[]{ 1, 2, 3 });
    LongColumn value2 = LongColumn.create("value2", new long[] { 4, 5, 6});

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"value1", 1L, 2L, 3L},
            {"value2", 4L, 5L, 6L}
        },
        result);

    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeBooleans() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    BooleanColumn value = BooleanColumn.create("value1", new boolean[]{ true, true, true });
    BooleanColumn value2 = BooleanColumn.create("value2", new boolean[]{ false, false, false});

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"value1", true, true, true},
            {"value2", false, false, false}
        },
        result);

    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeStrings() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    StringColumn value = StringColumn.create("fruit", new String[]{"apple", "banana","pear"});
    StringColumn value2 = StringColumn.create("colour", new String[]{"red", "yellow", "green"});

    Table testTable = Table.create("Data", label, value, value2);
    Table result = testTable.transpose();

    assertTableContents(
        new Object[][] {
            {"label", "row1", "row2", "row3"},
            {"fruit", "apple", "banana", "pear"},
            {"colour", "red", "yellow", "green"}
        },
        result);

    assertEquals(testTable.print(), result.transpose().print());
  }

  @Test
  void transposeMixedTypes() {
    StringColumn label = StringColumn.create("label", new String[]{ "row1", "row2", "row3"});
    DoubleColumn value = DoubleColumn.create("value1", new double[] { 1.0, 1.1, 1.2 });
    StringColumn value2 = StringColumn.create("colour", new String[]{ "red", "yellow", "green" });

    Table testTable = Table.create("Data", label, value, value2);

    try {
      testTable.transpose();
      fail("Should throw and exception");
    } catch (IllegalArgumentException ex) {
      assertEquals(
          "Transpose currently only supports tables where value columns are of the same type",
          ex.getMessage());
    }
  }
}
