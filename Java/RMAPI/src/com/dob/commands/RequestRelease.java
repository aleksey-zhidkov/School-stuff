package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * ������� ������� ������������ �������. ������������ ���������� �������
 * �����������, ��������� �������� � ������ ������.
 *
 * @author ������ �������
 */
public final class RequestRelease implements Serializable {

    private final ResourceDescriptor descriptor;
    private boolean answer = true;

    /**
     * ������ ����� ������� ������ ������������ �������.
     *
     * @param descriptor ���������� �������, ������� ���������� ����������
     */
    public RequestRelease(ResourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * ���������� ���������� �������, ������� ���������� ����������
     *
     * @return ���������� �������, ������� ���������� ����������
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * ���������� ����� ��������� �������
     *
     * @return <code>true</code>, ���� �������� ����� ���������� � ��������� ������
     * � <code>false</code> � ��������� ������
     */
    public boolean getAnswer() {
        return answer;
    }

    /**
     * ��������� ������� ����������� �������.
     *
     * @param answer ������� ����������� �������. <code>true</code>, ���� ������
     * ���������, ����� - <code>false</code>
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
