// ������ ����
package chat;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class chatServer// ����� �������
{
 public static ArrayList clients = new ArrayList();// ������ ��������
 public static ServerSocket s;// ��������������
 public static int PORT = 8582;// ����, � �������� ����������� 1 ������
 public static void main(String args[])throws IOException
 {
   System.out.println("Server started: ");// ����� ��������� �� �������
   try
   {
     do// ����������� ����
     {
       s = new ServerSocket(PORT);// ������ ����� ��������������...
       Socket socket = s.accept();// ��� ��������...
       clients.add(new Client(socket,clients.size()));// ������ ����������� �������
       PORT++;// ����������� ����� �����
     }
     while(true);// ����������� ����
   }
   finally// ������...
   {
    s.close();// ��������� �����
   }
 }
 public static void exit(int id)
 {
   for (int i=id+1;i<clients.size();i++)// ��� ���� �������� �� �� ������ ���������...
   {
     Client c = (Client) clients.get(i);// �������� �������
     c.ID--;// ��������� ��
     c=null;// ���������� ���. ������
   }
   clients.remove(id);// ������� �������
   if (clients.size()<1)// ���� �������� �� ��������...
   {
     System.out.println("Server stopped");// ������� ���������
     System.exit(0);// � ������� �� ���������
   };
 }

 public static void sent(String s,int ID)// �������� ���������
 {
   System.out.println(s);// ����� �� ������� �������
   for (int i=0;i<clients.size();i++)// ��� ���� ��������...
   {
     Client c = (Client) clients.get(i);// �������� �������
     if (i!=ID) {c.out.println(s);};// ���� �� �� �������, �� �������� ������� ���������
   }
 }
}

