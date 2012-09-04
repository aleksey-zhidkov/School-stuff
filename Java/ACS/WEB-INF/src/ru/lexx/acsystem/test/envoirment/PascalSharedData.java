package ru.lexx.acsystem.test.envoirment;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 30.10.2005
 * Time: 15:00:24
 * To change this template use File | Settings | File Templates.
 */
public class PascalSharedData {
    public static final String CONDITION1 =
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

    public static final String CONDITION2 =
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

    public static final String CONDITION3 =
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

    public static final String CONDITION4 =
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

    public static final String FOR1 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 1 to 10 do" +
            "    inc(j);" +
            "end.";

    public static final String FOR2 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 5 to 10 do" +
            "    if (i = 7) then" +
            "      j := i + 3;" +
            "end.";

    public static final String FOR3 =
            "var " +
            "  i,j : integer;" +
            "begin" +
            "  j := 0;" +
            "  for i := 10 downto 5 do" +
            "    if (i = 7) then" +
            "      j := i + 3;" +
            "end.";

    public static final String FOR4 =
            "var " +
            "  i,j,k : integer;" +
            "begin" +
            "  j := 0;" +
            "  i := 10;" +
            "  for k := i to j do" +
            "    j := i + 3;" +
            "end.";

    public static final String WHILE1 =
            "var " +
            "  i,j: integer;" +
            "begin" +
            "  while (i < j) do" +
            "    i := i + 1;" +
            "end.";

    public static final String REPEAT1 =
            "var " +
            "  i,j: integer;" +
            "begin" +
            "  repeat" +
            "    i := i + 1;" +
            "  until (i >= j) " +
            "end.";
}
