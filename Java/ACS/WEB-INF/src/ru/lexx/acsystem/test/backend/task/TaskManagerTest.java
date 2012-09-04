package ru.lexx.acsystem.test.backend.task;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.task.TaskManager;
import ru.lexx.acsystem.interpretator.common.ProgLangEquipmentBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 13:15:36
 */
public class TaskManagerTest extends TestCase {

    private static final String TASK_20_FORMULATION = "По заданному количеству книг и их цене, а также введенной сумме оплаты, определить сдачу. Входные данные получать в переменные books_count: integer, bool_cost, cash : real. Ответ сохранить в переменную change : real.";

    public TaskManagerTest() {
    }

    public TaskManagerTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testGetTask() {
        Task t = TaskManager.getTask("20", ProgLangEquipmentBuilder.getInstance(ProgLanguage.PASCAL).buildVariableManager());
        assertTrue(t.getDays_to_resolve() == 8);
        assertTrue(TASK_20_FORMULATION.equalsIgnoreCase(t.getFormulation()));
        assertTrue(t.getData().length == 3);
        assertTrue("books_count".equalsIgnoreCase(t.getData()[0].getInput_data().get(0).getName()));
    }

    public void testGetDaysToResolve() {
        int dtr = TaskManager.getDaysToResolve("20");
        assertTrue(dtr == 8);
        assertTrue(TaskManager.getDaysToResolve("-2") == -1);
    }

    @Override
    public void runTest() {
        testGetTask();
        testGetDaysToResolve();
    }
}
