package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * Интерфейс калькулятора сособного вычислять целые, вещественные и булевы
 * выражения представленные массивом <code>ILexem</code>
 *
 * @author Alexey Zhidkov
 *         <p/>
 *         Created by IntelliJ IDEA.
 *         User: jdev
 *         Date: 09.02.2005
 *         Time: 20:46:00
 */
public interface ICalculator {

    /**
     * Вычисление целочисленного выражения представленного массивом <code>ILexem</code>
     *
     * @param lexems Выражение, которое необходимо вычислить. Выражение должно содержать отлько константы и операторы.
     * @return Результат вычисления выражения
     */
    int calculateInt(ILexem[] lexems);

    /**
     * Вычисление вещественного выражения представленного массивом <code>ILexem</code>
     *
     * @param lexems Выражение, которое необходимо вычислить. Выражение должно содержать отлько константы и операторы.
     * @return Результат вычисления выражения
     */
    double calculateDouble(ILexem[] lexems);

    /**
     * Вычисление булевого выражения представленного массивом <code>ILexem</code>
     *
     * @param lexems Выражение, которое необходимо вычислить. Выражение должно содержать отлько константы и операторы.
     * @return Результат вычисления выражения
     */
    boolean calculateBoolean(ILexem[] lexems);

    /**
     * Проверяет выражение на вычислимость.
     *
     * @param lexems Выражение, которое необходимо проверить на корректность.
     * @return <b>true</b> тогда и только тогда, когда вычисление выражения
     *         не вызовет исключений. Иначе <b>false</b> - исключение быдет выброшенно обязательно.
     */
    void validateExpression(ILexem[] lexems);
}
