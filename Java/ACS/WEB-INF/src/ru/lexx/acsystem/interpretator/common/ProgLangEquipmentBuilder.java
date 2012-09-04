package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.interpretator.common.function.IFunctionManager;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 21.05.2006
 * Time: 21:25:14
 */
public abstract class ProgLangEquipmentBuilder {

    private static Map<ProgLanguage, ProgLangEquipmentBuilder> builders = new HashMap<ProgLanguage, ProgLangEquipmentBuilder>();

    static {
        builders.put(ProgLanguage.PASCAL, new PascalEquipmentBuilder());
    }


    public static ProgLangEquipmentBuilder getInstance(ProgLanguage lang) {
        return builders.get(lang);
    }

    public abstract IVariableManager buildVariableManager();

    public abstract CommonCalculator buildCalculator();

    public abstract CommonParser buildParser();

    public abstract IFunctionManager buildFunctionManager();
}
