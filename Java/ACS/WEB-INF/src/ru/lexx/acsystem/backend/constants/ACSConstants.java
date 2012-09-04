package ru.lexx.acsystem.backend.constants;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 17.01.2006
 * Time: 22:54:13
 * To change this template use File | Settings | File Templates.
 */
public interface ACSConstants {
    // им€ атрибута сессии под которым хранитс€ аккаунт пользовател€
    String ACCAUNT_ATTRIBUTE_NAME = "accaunt";

    // им€ атрибута запроса под которым хранитс€ контекст
    String CONTEXT_ATTRIBUTE_NAME = "context";

    // им€ атрибута контекста под которым хранитс€ форма
    String FORM_ATTRIBUTE_NAME = "ACS_RC_ATTR_FORM";

    // 1 инстанс пустого массива на всЄ приложение - экономи€ пам€ти и времени
    Object[] ZERO_ARRAY = new Object[0];

    // им€ атрибута, под которым хранитс€ класс эдтора
    String ACSUIEDITOR_ATTRIBUTE_NAME = "ACS_RC_ATTR_ACSUIEDITOR";

    /* им€ атрибута контекства, под которымх хранитс€ сущЄность
       изменнЄна€ модулем администрировани€  */
    String ACTIONED_EDITABLE_ATTRIBUTE_NAME = "ACS_RC_ARRT_ACTIONED_EDITABLE";
}
