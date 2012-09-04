package ru.lexx.acsystem.interpretator.pascal;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.CommonParser;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.lexem.*;
import ru.lexx.acsystem.interpretator.pascal.lexem.*;

import java.io.StringReader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 17:15:38
 */
public class PascalParser extends CommonParser {

    private PascalParser(String _delims) {
        super(_delims);
    }

    public PascalParser() {
        this("\r\n,:;=*/+ ().-'<>[]");
    }

    @Override
    protected void parseLexem(String lexem, char delim, List<ILexem> lexems, StringReader reader) throws Exception {
        int lsize = lexems.size();
        if ("var".equalsIgnoreCase(lexem))
            lexems.add(new VarLexem(lexem, line));
        else if ("begin".equalsIgnoreCase(lexem))
            lexems.add(new BeginBlockLexem(lexem, line));
        else if ("end".equalsIgnoreCase(lexem)) {
            lexems.add(new EndBlockLexem(lexem, line));
            if (delim == ';')
                lexems.add(new OperatorLexem(delim + "", line));
        } else if ("real".equalsIgnoreCase(lexem) ||
                   "integer".equalsIgnoreCase(lexem) ||
                   "string".equalsIgnoreCase(lexem) ||
                   "boolean".equalsIgnoreCase(lexem)) {
            lexems.add(new VariableLexem(lexem, line));
            lexems.add(new OperatorLexem(delim + "", line));
        } else if ("then".equalsIgnoreCase(lexem))
            lexems.add(new ThenLexem(lexem, line));
        else if ("for".equalsIgnoreCase(lexem))
            lexems.add(new ForLexem(lexem, line));
        else if ("to".equalsIgnoreCase(lexem))
            lexems.add(new ToLexem(lexem, line));
        else if ("downto".equalsIgnoreCase(lexem))
            lexems.add(new ToLexem(lexem, line));
        else if ("do".equalsIgnoreCase(lexem))
            lexems.add(new DoLexem(lexem, line));
        else if ("repeat".equalsIgnoreCase(lexem))
            lexems.add(new RepeatLexem(lexem, line));
        else if ("until".equalsIgnoreCase(lexem))
            lexems.add(new UntilLexem(lexem, line));
        else if ("array".equalsIgnoreCase(lexem)) {
            lexems.add(new ArrayLexem(lexem, line));
            if (delim == '[')
                lexems.add(new OperatorLexem(delim + "", line));
            return;
        } else if ("of".equalsIgnoreCase(lexem))
            lexems.add(new OfLexem(lexem, line));
            /*String operators*/
        else if ("and".equalsIgnoreCase(lexem) ||
                 "or".equalsIgnoreCase(lexem) ||
                 "xor".equalsIgnoreCase(lexem) ||
                 "not".equalsIgnoreCase(lexem) ||
                 "div".equalsIgnoreCase(lexem) ||
                 "mod".equalsIgnoreCase(lexem)) {
            lexems.add(new OperatorLexem(lexem, line));
            if (delim == '(')
                lexems.add(new OperatorLexem(delim + "", line));
        }
        /*parsing string constants*/
        else if ("true".equalsIgnoreCase(lexem)) {
            lexems.add(ConstantManager.createConstant(lexem, line));
            if (delim == ')' || delim == ';')
                lexems.add(new OperatorLexem(delim + "", line));
        } else if ("false".equalsIgnoreCase(lexem)) {
            lexems.add(ConstantManager.createConstant(lexem, line));
            if (delim == ')' || delim == ';')
                lexems.add(new OperatorLexem(delim + "", line));
        } else if (delim == '\'') {
            StringBuffer buf = new StringBuffer();
            char c;
            while ((c = (char) reader.read()) != '\'') buf.append(c);
            lexems.add(ConstantManager.createConstant(buf.toString(), DataType.TYPE_STRING, line));
        }

        /* ':' may be meeted in 2 cases:
        1)
        var
        somevar : integer;
        2)
        somevar := 12;
        */
        if (delim == ':') {
            reader.mark(2);
            char c2 = (char) reader.read();
            if (delim == '<' && c2 == '>') /* if next char its '=' then we have appropriation*/
                lexems.add(new OperatorLexem(new String(new char[]{delim, c2}), line));
            else if (c2 == '=') {
                if (lexem.trim().length() != 0)
                    lexems.add(new UsersDefinedLexem(lexem, line));
                lexems.add(new OperatorLexem(new String(new char[]{delim, c2}), line));
            } else /* else we must return file pointer to previus character*/
                reader.reset();
        } else if (delim == '<' || delim == '>') {
            super.parseLexem(lexem, delim, lexems, reader);
            reader.mark(2);
            char c2 = (char) reader.read();
            if (c2 == '=' || delim == '<' && c2 == '>') /* if next char its '=' or '>' then we have comparation*/
                lexems.add(new OperatorLexem(new String(new char[]{delim, c2}), line));
            else {/* else we must return file pointer to previus character*/
                reader.reset();
                lexems.add(new OperatorLexem(new String(new char[]{delim}), line));
            }
        } else if (delim == '.') {
            reader.mark(2);
            char c2 = (char) reader.read();
            if (c2 == '.') { /* if next char its '.' then we have sequence operator '..'*/
                lexems.add(ConstantManager.createConstant(lexem, DataType.TYPE_INTEGER, line));
                lexems.add(new OperatorLexem(new String(new char[]{delim, c2}), line));
            } else /* else we must return file pointer to previus character*/
                lexems.add(new OperatorLexem(new String(new char[]{delim}), line));
        }
        if (lsize == lexems.size())
            super.parseLexem(lexem, delim, lexems, reader);
    }
}
