package ru.lexx.acsystem.test.intepretator.pascal;

import junit.framework.Assert;
import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.task.TaskManager;
import ru.lexx.acsystem.interpretator.Interpreter;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.pascal.PascalVariableManager;
import ru.lexx.acsystem.test.envoirment.AbstractTaskTest;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 18:30:52
 * To change this template use File | Settings | File Templates.
 */
public class ConditionTest extends AbstractTaskTest {

    public ConditionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testConditionParsing() {
        Interpreter intr = new Interpreter();
        List<IVariable> input = new ArrayList<IVariable>();
        List<IVariable> output = new ArrayList<IVariable>();
        IVariable[] vars = createVars();

        input.add(vars[0]);
        input.add(vars[1]);
        output.add(vars[1]);
        output.add(vars[2]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION1, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[3]);
        output.add(vars[0]);
        output.add(vars[4]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION1, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[3]);
        output.add(vars[3]);
        output.add(vars[5]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION2, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[1]);
        output.add(vars[6]);
        output.add(vars[7]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION2, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[3]);
        output.add(vars[6]);
        output.add(vars[3]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION3, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[1]);
        output.add(vars[0]);
        output.add(vars[7]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION3, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[3]);
        output.add(vars[6]);
        output.add(vars[9]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION4, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[0]);
        input.add(vars[1]);
        output.add(vars[7]);
        output.add(vars[8]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.CONDITION4, ProgLanguage.PASCAL));
    }

    @Override
    public void runTest() {
        testConditionParsing();
    }

    public IVariable[] createVars() {
        IVariable[] vars = new IVariable[10];
        int i = 0;
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "i", "3");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "j", "3");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "i", "-3");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "j", "4");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "j", "-3");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "i", "4");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "i", "1");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "j", "1");
        vars[i++] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "i", "2");
        vars[i] = new PascalVariableManager().createVariable(DataType.TYPE_INTEGER, "j", "2");
        return vars;
    }
}
