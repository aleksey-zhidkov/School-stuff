/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.resources;

import org.davic.resources.ResourceClient;

public interface ResourceConsumer extends ResourceClient {

    /**
     * ���������� ������������� ���������� ����������� ��������
     *
     * @return ������������� ���������� ����������� ��������
     */
    int getAppId();

    /**
     * ���������� ��������� ���������� ����������� ��������
     *
     * @return ��������� ���������� ����������� ��������
     */
    int getPriority();

}
