package ru.lexx.acsystem.interpretator.common.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 01.10.2005
 * Time: 22:28:30
 */
public interface IFunction {
    /**
     * В момент вызова указатель программы находится над именем вызываемой функции.
     * Например:
     * |sqrt|(|4|)|;|
     * ^ - указатель здесь.
     *
     * @param context Контекст исполняемой программы
     * @return Результат выполенеия функции. Если функция не возвращяет ни каких значений
     *         то возвращяет IConstant.VOID_CONSTANT
     */
    IConstant execute(ProgramContext context);

    /**
     * @return Возвращяет тип результата возвращяемого функцией
     */
    DataType getReturnType();
}
