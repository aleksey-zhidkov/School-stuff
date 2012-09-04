// ������ ����
package chat;
import java.net.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class chatClient// ��������� �����
{
  public static PrintWriter out;// �������� �������
  public static BufferedReader in;// �������� �������
  public static clientWriter cw;// �����-��������
  public static clientReader cr;// �����-��������
  public static String name = new String();// ��� �������
  public static int PORT=8582;// ���� � �������� ����� ��������� � �������
  public static Socket socket;// ����� ���������� � ��������
  public static void main(String[] args) throws IOException
   {
    InetAddress addr = InetAddress.getByName(null);// ��������� ������ (���������, ��� ������ �� ��� �� ������,
                                                   // ��� � ������
    name = new String(args[0]);// ��� �������
    boolean hasConnect;// ������� �� ����������
    do
    {
      if (PORT>8632) {PORT=8582;};// ���� ������ 50 ������ ������ - �������� �������, � ������ ����������
      hasConnect=true;// ������� ��� ���������� �������...
      socket = new Socket(addr, PORT);//�������� �����
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);// �������� ��������
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// �������� ��������
      socket.setSoTimeout(3000);// ������������� �������� �� �������� ������ �� ������� � 3 �������
      try
      {
        System.out.println("Try connect to "+addr.getCanonicalHostName()+":"+PORT+"...");// ������� ��������� �������
        out.println(name);// ���������� ������� ���
        System.out.println(in.readLine());// ��� ������ � ������� �� ����� (���� ����)...
      }
      catch(SocketTimeoutException e)// ���� ������ �� ���������
      {
       PORT++;// ����������� ����� �����
       hasConnect=false;// ������� ���������� ���������
       System.out.println("Faild...");// ������� ��������� �������
      };
    }while(!hasConnect);// ����  �� ��������� �������� ����������...
    socket.setSoTimeout(0);// ������� ����������� �� ����� �������� ������ �������
    cw = new clientWriter();// ������ ��������
    cr = new clientReader(); // ������ ��������
   }
}

class clientWriter extends Thread// �������� - ������� ������
{
 public clientWriter() //�����������
 {
   start();// ������ ������
 }
 public void run()// �����
 {
   try
   {
    String s;// ��������� �������
    do
    {
     byte[] input= new byte[256];// ������ ���� ����������� � ������������ �����
     System.in.read(input);// ���������� ��������� ��������� �������������
     s = new String(input).trim();// �������� ��������
     chatClient.out.println(s);// ����������� ��������� �������
     try
     {
       this.currentThread().sleep(100);// �������� �� 0,1
     }
     catch (InterruptedException e) {System.out.println("Error: "+e.toString());}; //�������� ������ ������
    }
    while(!s.equals("!exit!"));// �� ��� ���, ���� ������������ �� ����� ����������������� �����
   }
   catch(IOException e)// �������� ������ �����
   {
    System.out.println("Error: "+e.toString());// ����� ��������� �� ������
   }
   finally// ������...
   {
    System.out.print("closing...");// ������� ��������� �������
    try{chatClient.socket.close();}catch(IOException e){};// ��������� �����
    System.exit(0);// ������� �� ���������
   }
 }
}

class clientReader extends Thread// �������� - ������� ������
{
 public clientReader()// �����������
 {
   start();// ������ ������
 }
 public void run()// �����
 {
   try
   {
    String s = new String();// ��������� �� �������
    do// ����������� ����
    {
     s = chatClient.in.readLine();// ��������� ��������� �� �������
     if (s!=null)// ���� ��������� �� �����....
     {
      System.out.println(s);// ������� �� �������
     };
     try
     {
       this.currentThread().sleep(100);// �������� �� 0,1 �������
     }
     catch (InterruptedException e)// �������� ����������
     {
      System.out.println("Error: "+e.toString());// ����� ��������� �� ������
     };
    }
    while(true);// ����������� ����
   }
   catch(IOException e)// �������� ����������
   {
    System.out.println("Error: "+e.toString());// ����� ��������� �� ������
   }
 }
}

