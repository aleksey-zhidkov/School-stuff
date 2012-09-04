package com.dob.resources;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.05.2009
 * Time: 8:28:15
 * To change this template use File | Settings | File Templates.
 */

/**
 * ��������� ������� ������ � ���������� ����������� IPC.
 * ������ ��� ����������� ������ ��������� ��������� ���������
 * ������������� ������� � �������� IPC.
 *
 * @author ������ �������
 */
public interface IPCHandler {

    /**
     * ����� ��������� ��������� ������� IPC
     * @param listener ��������� ������� IPC
     */
    void setListener(IPCHandlerListener listener);
}
