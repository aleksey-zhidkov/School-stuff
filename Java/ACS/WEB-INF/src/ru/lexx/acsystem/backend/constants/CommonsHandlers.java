package ru.lexx.acsystem.backend.constants;

import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.jdev.utils.db.rowhandlers.StringRowHandler;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 25.02.2006
 * Time: 14:06:29
 */
public interface CommonsHandlers {
    StringRowHandler STRING_ROW_HANDLER_TASK_ID = new StringRowHandler("task_id");

    IntegerRowHandler INTEGER_ROW_HANDLER_COUNT = new IntegerRowHandler("count(*)");
}
