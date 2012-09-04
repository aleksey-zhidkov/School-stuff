package com.dob.commands;

import org.davic.resources.ResourceProxy;

import java.io.Serializable;

/**
 * ������� �������������� ������� � ������� � �������.
 * ������������ ��������� ������� �����������, ������������ ������ � �������.
 *
 * @author ������ �������
 */
public final class RequestAccepted implements Serializable {

    private final ResourceProxy proxy;

    /**
     * ������ ����� ������� �� �������������� ������� � �������.
     *
     * @param proxy ������ �������, � �������� ������������ ������
     */
    public RequestAccepted(ResourceProxy proxy) {
        this.proxy = proxy;
    }

    /**
     * ���������� ������ �������, � �������� ������������ ������
     * @return ������ �������, � �������� ������������ ������
     */
    public ResourceProxy getProxy() {
        return proxy;
    }
}
