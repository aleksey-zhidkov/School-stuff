package com.dob.resources;

/**
 * »нтерфейс классов описывающих ресурсы.
 *
 * @author ∆идков јлексей
 */
public interface ResourceAttributes {

    /**
     * ¬озвращ€ет название ресурса
     *
     * @return название ресурса
     */
    String getName();

    /**
     * ¬озвращает общее количество ресурса
     *
     * @return общее количество ресурса
     */
    long getTotalQuantity();

    /**
     * ¬озвращает раздел€емость ресурса
     *
     * @return <code>true</code>, если ресурс раздел€ем, в противном случае -
     *         <code>false</code>
     */
    boolean isDisposable();

    /**
     * ¬озвращает резервируемость ресурса
     *
     * @return <code>true</code>, если ресурс может быть зарезервирован, в противном случае -
     *         <code>false</code>
     */
    boolean isReservable();

    /**
     * ¬озвращает переиспользуемость ресурса
     *
     * @return <code>true</code>, если ресурс может быть переиспользован, в противном случае -
     *         <code>false</code>
     */
    boolean isReusble();
}
