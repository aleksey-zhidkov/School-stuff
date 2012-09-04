package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * ��������� ������������ ��������� ��������� �����, ������������ � ������
 * ��������� �������������� �������� <code>ILexem</code>
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
     * ���������� �������������� ��������� ��������������� �������� <code>ILexem</code>
     *
     * @param lexems ���������, ������� ���������� ���������. ��������� ������ ��������� ������ ��������� � ���������.
     * @return ��������� ���������� ���������
     */
    int calculateInt(ILexem[] lexems);

    /**
     * ���������� ������������� ��������� ��������������� �������� <code>ILexem</code>
     *
     * @param lexems ���������, ������� ���������� ���������. ��������� ������ ��������� ������ ��������� � ���������.
     * @return ��������� ���������� ���������
     */
    double calculateDouble(ILexem[] lexems);

    /**
     * ���������� �������� ��������� ��������������� �������� <code>ILexem</code>
     *
     * @param lexems ���������, ������� ���������� ���������. ��������� ������ ��������� ������ ��������� � ���������.
     * @return ��������� ���������� ���������
     */
    boolean calculateBoolean(ILexem[] lexems);

    /**
     * ��������� ��������� �� ������������.
     *
     * @param lexems ���������, ������� ���������� ��������� �� ������������.
     * @return <b>true</b> ����� � ������ �����, ����� ���������� ���������
     *         �� ������� ����������. ����� <b>false</b> - ���������� ����� ���������� �����������.
     */
    void validateExpression(ILexem[] lexems);
}
