package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

public class EndBlockLexem extends AbstractKeyWordLexem {

    public EndBlockLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        int level = context.decSubBlockLevel();
        if (level == 0 && !context.hasMoreLexems() || !".".equalsIgnoreCase(context.getNextLexem().getValue()))
            throw new UserErrorException("Ожидается 'end.'", getLine());
    }
}
