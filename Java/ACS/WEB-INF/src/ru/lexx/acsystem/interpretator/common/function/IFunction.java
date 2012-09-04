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
     * � ������ ������ ��������� ��������� ��������� ��� ������ ���������� �������.
     * ��������:
     * |sqrt|(|4|)|;|
     * ^ - ��������� �����.
     *
     * @param context �������� ����������� ���������
     * @return ��������� ���������� �������. ���� ������� �� ���������� �� ����� ��������
     *         �� ���������� IConstant.VOID_CONSTANT
     */
    IConstant execute(ProgramContext context);

    /**
     * @return ���������� ��� ���������� ������������� ��������
     */
    DataType getReturnType();
}
