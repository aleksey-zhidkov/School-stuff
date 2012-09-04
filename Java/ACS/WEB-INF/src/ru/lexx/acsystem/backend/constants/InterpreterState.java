package ru.lexx.acsystem.backend.constants;

public class InterpreterState extends AbstractConstant {

    public static final InterpreterState STATE_START = new InterpreterState("STATE_START", "null_state");
    public static final InterpreterState STATE_VAR_DEFINE = new InterpreterState("STATE_VAR_DEFINE", "var_define");
    public static final InterpreterState STATE_MAIN_PROG = new InterpreterState("STATE_MAIN_PROG", "main_prog");
    public static final InterpreterState STATE_SUBBLOCK = new InterpreterState("STATE_SUBBLOCK", "subblock");
    public static final InterpreterState STATE_END = new InterpreterState("STATE_END", "null_state");


    private InterpreterState(String _name, String _code) {
        super(_name, _code);
    }

    public static InterpreterState getByName(String _name) {
        return (InterpreterState) getConstantByName(_name, InterpreterState.class);
    }

    public static InterpreterState getByCode(String _code) {
        return (InterpreterState) getConstantByName(_code, InterpreterState.class);
    }

    public static InterpreterState[] getAll() {
        AbstractConstant ac[] = getAll(InterpreterState.class);
        InterpreterState res[] = new InterpreterState[ac.length];
        for (int i = 0; i < ac.length; i++)
            res[i] = (InterpreterState) ac[i];

        return res;
    }

}
