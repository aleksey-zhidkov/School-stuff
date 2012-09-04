package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;


/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 01.10.2005
 * Time: 22:31:05
 * To change this template use File | Settings | File Templates.
 */
public interface IConstant extends ILexem {
    IConstant VOID_CONSTANT = new StringConstant("", 0);

    DataType getType();

}
