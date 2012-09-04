package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * ������� ������������ �������. ������������ ��� ����������, ��� �
 * ������������ �������.
 *
 * @author ������ �������
 */
public final class ReleaseResource implements Serializable {

    private final ResourceDescriptor descriptor;

    /**
     * ������ ����� ������� �� ������������ �������
     * @param descriptor ���������� �������������� �������
     */
    public ReleaseResource(ResourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * ���������� ���������� �������������� �������
     * @return ���������� �������������� �������
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }
}
