package com.dob.resources;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 01.05.2009
 * Time: 13:24:26
 * To change this template use File | Settings | File Templates.
 */

/**
 * ��������� ��������� ������� IPC. ���������� ������� ����������� ������ ���������,
 * �������������� � ����������� ������� ����������� ��������� IPCHandler
 *
 * @author ������ �������
 */
public interface IPCHandlerListener {

    /**
     * ������� �������� ������ IPC
     */
    void connectorOpened();

    /**
     * ������� ���������� ������� �� ����������� � ������ IPC
     */
    void newLink();

    /**
     * ������� ��������� �������� ����������� ��������
     *
     * @param conumer ������� ����������� ��������
     */
    void delegateRecieved(ResourceConsumer conumer);

    /**
     * ������� ��������� ��������� �� ������������ �������
     */
    void releaseMessageReceived();

    /**
     * ������� ��������� ��������� � ������� ������� � �������
     *
     * @param resource ���������� �������, � �������� ������������� ������
     * @param consumer ������� ����������� ��������, ������������� ������
     */
    void resourceRequested(ResourceDescriptor resource, ResourceConsumer consumer);

    /**
     * ��������� � ��������� ������ � ������� �� ������������ �������
     */
    void releaseRequestDenied();

    /**
     * ��������� � ��������� �������������� ������� �� ������������ �������
     */
    void releaseRequestAccepted();

}
