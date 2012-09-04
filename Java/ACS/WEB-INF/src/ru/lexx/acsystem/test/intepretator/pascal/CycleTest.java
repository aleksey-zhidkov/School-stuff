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
 * User: Alexey
 * Date: 04.11.2005
 * Time: 17:24:38
 */
public class CycleTest extends AbstractTaskTest {

    public CycleTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testFor() {
        try {
            Interpreter intr = new Interpreter();
            IVariable[] vars = getVars();
            List<IVariable> output = new ArrayList<IVariable>();
            List<IVariable> input = new ArrayList<IVariable>();

            output.add(vars[2]);
            output.add(vars[3]);
            TaskManager.putDataToTestCase(this, new ArrayList<IVariable>(), output);
            Assert.assertTrue(intr.interpretate(task, PascalPrograms.FOR1, ProgLanguage.PASCAL));

            input.clear();
            output.clear();
            output.add(vars[2]);
            output.add(vars[3]);
            TaskManager.putDataToTestCase(this, new ArrayList<IVariable>(), output);
            Assert.assertTrue(intr.interpretate(task, PascalPrograms.FOR1, ProgLanguage.PASCAL));

            input.clear();
            output.clear();
            output.add(vars[5]);
            output.add(vars[6]);
            TaskManager.putDataToTestCase(this, new ArrayList<IVariable>(), output);
            Assert.assertTrue(intr.interpretate(task, PascalPrograms.FOR3, ProgLanguage.PASCAL));

            input.clear();
            output.clear();
            output.add(vars[2]);
            output.add(vars[6]);
            TaskManager.putDataToTestCase(this, new ArrayList<IVariable>(), output);
            Assert.assertTrue(intr.interpretate(task, PascalPrograms.FOR4, ProgLanguage.PASCAL));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            fail(e.toString());
        }
    }

    public void testWhile() {
        Interpreter intr = new Interpreter();
        IVariable[] vars = getVars();
        List<IVariable> output = new ArrayList<IVariable>();
        List<IVariable> input = new ArrayList<IVariable>();

        input.add(vars[3]);
        input.add(vars[5]);
        output.add(vars[2]);
        output.add(vars[3]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.WHILE1, ProgLanguage.PASCAL));

        input.clear();
        output.clear();
        input.add(vars[2]);
        input.add(vars[6]);
        output.add(vars[2]);
        output.add(vars[6]);
        TaskManager.putDataToTestCase(this, input, output);
        Assert.assertTrue(intr.interpretate(task, PascalPrograms.WHILE1, ProgLanguage.PASCAL));
    }

    public void runTest() {
        testFor();
        testWhile();
    }

    private static IVariable[] getVars() {
        PascalVariableManager man = new PascalVariableManager();
        IVariable[] vars = new IVariable[7];
        int i = 0;
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "i");
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "j");
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "i", "10");
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "j", "10");
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "i", "1");
        vars[i++] = man.createVariable(DataType.TYPE_INTEGER, "i", "5");
        vars[i] = man.createVariable(DataType.TYPE_INTEGER, "j", "0");
        return vars;
    }
}
