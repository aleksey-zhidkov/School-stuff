package ru.lexx.acsystem.test.envoirment;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 30.10.2005
 * Time: 15:00:24
 */
public interface PascalPrograms {
    String CONDITION1 =
            "var " +
            "i,j : integer;" +
            "begin" +
            "  read(i);" +
            "  read(j);" +
            "  if ( i = j ) then" +
            "    begin" +
            "      writeln('i=j');" +
            "      i:= j * -1;" +
            "    end" +
            "  else" +
            "    j := i * -1;" +
            "end.";

    String CONDITION2 =
            "var " +
            "  i,j:integer;" +
            "begin" +
            "  read(i);" +
            "  read(j);" +
            "  if ( i < j) then " +
            "    i := j" +
            "  else " +
            "    begin" +
            "      i := 1;" +
            "      j := 1;" +
            "    end;" +
            "end.";

    String CONDITION3 =
            "var " +
            " i,j:integer;" +
            "begin" +
            "  read(i);" +
            "  read(j);" +
            "  if ( i < j) then " +
            "    i := 1" +
            "  else " +
            "    j := 1;" +
            "end.";

    String CONDITION4 =
            "var " +
            " i,j:integer;" +
            "begin" +
            "  read(i);" +
            "  read(j);" +
            "  if ( i < j) then" +
            "    begin " +
            "      i := 1;" +
            "      j := 2;" +
            "    end" +
            "  else " +
            "    begin" +
            "      j := 1;" +
            "      i := 2;" +
            "    end;" +
            "end.";

    String FOR1 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 1 to 10 do" +
            "    inc(j);" +
            "end.";

    String FOR2 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 5 to 10 do" +
            "    if (i = 7) then" +
            "      j := i + 3;" +
            "end.";

    String FOR3 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 10 downto 5 do" +
            "    if (i = 7) then" +
            "      j := i + 3;" +
            "end.";

    String FOR4 =
            "var " +
            "  i,j,k : integer;" +
            "begin" +
            "  j := 0;" +
            "  i := 10;" +
            "  for k := i to j do" +
            "    j := i + 3;" +
            "end.";

    String WHILE1 =
            "var " +
            "  i,j: integer;" +
            "begin" +
            "  while (i < j) do" +
            "    i := i + 1;" +
            "end.";

    String REPEAT1 =
            "var " +
            "  i,j: integer;" +
            "begin" +
            "  repeat" +
            "    i := i + 1;" +
            "  until (i >= j) " +
            "end.";

    String ARRAY1 =
            "var " +
            "  arr1,arr2 : array[ 1..5, 3..6, 2..6] of integer;" +
            "begin" +
            "  writeln(arr[arr[2 + 1][i][i+1]][i][arr[1][2][3]+i]);" +
            "end.";
}
