package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.interpretator.common.function.IFunctionManager;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;
import ru.lexx.acsystem.interpretator.pascal.PascalCalculator;
import ru.lexx.acsystem.interpretator.pascal.PascalParser;
import ru.lexx.acsystem.interpretator.pascal.PascalVariableManager;
import ru.lexx.acsystem.interpretator.pascal.function.PascalFunctionManager;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 15:44:40
 */
public class PascalEquipmentBuilder extends ProgLangEquipmentBuilder {

    public IVariableManager buildVariableManager() {
        return new PascalVariableManager();
    }

    public CommonCalculator buildCalculator() {
        return new PascalCalculator("not");
    }

    public CommonParser buildParser() {
        return new PascalParser();
    }

    public IFunctionManager buildFunctionManager() {
        return new PascalFunctionManager(ProgLanguage.PASCAL);
    }
}
