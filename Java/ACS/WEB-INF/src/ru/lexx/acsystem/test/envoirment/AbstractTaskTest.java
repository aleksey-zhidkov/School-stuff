package ru.lexx.acsystem.test.envoirment;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.task.Task;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 17:30:49
 */
public class AbstractTaskTest extends TestCase implements Taskable {

    protected Task task;

    public AbstractTaskTest(String name) {
        super(name);
    }

    public void setTask(Task _task) {
        task = _task;
    }
}
