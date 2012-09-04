package com.dob.resources.manager;

import com.dob.resources.ResourceConsumer;

/**
 * ����� ���������� ��������, ����������� �� ������.
 *
 * @author ������ �������
 */
public interface ReclamationManager extends ResourceManager {

    /**
     * ����������� ������� �������� �����������
     *
     * @param resourceConsumer ����������� ��������
     * @return <code>true</code> ���� ������� ���������� ����������� �������,
     * <code>false</code> �����
     */
    boolean isCleared(ResourceConsumer resourceConsumer);

    /**
     * ������� �������� ���������� �����������
     *
     * @param resourceConsumer ����������� ��������
     */
    void releaseResources(ResourceConsumer resourceConsumer);

}
