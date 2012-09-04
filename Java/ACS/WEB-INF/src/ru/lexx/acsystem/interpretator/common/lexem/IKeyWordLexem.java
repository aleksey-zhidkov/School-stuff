// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IKeyWordLexem.java

package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;

public interface IKeyWordLexem
        extends ILexem {

    void changeState(ProgramContext context);
}
