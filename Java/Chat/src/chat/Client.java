// ������-���������� ��������
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

public class Client extends Thread// ��������� ������
{
  public Socket socket;// ����� ���������� � ��������
  public int ID;// �� �������
  public BufferedReader in;// �������� ��������� �� �������
  public PrintWriter out;// �������� ��������� �������
  public String name;// ��� �������
  public Client(Socket socket,int ID)// �����������
  {
    this.socket=socket;// ��������� ������
    this.ID=ID;// ��������� ��
    start();// ������ ������
  }
  public void run()// �����
  {
    try
    {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// ��������� ��������
      name = in.readLine();// ������ ����� �� ������� (������� ���������� ������->������
      chatServer.sent("[--++:"+name+" are connecting at "+getDate()+":++--]",ID);// �������� ��������� ��������
      System.out.println(socket);// ����� �� ������� �������
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);// ��������� �������� �������
      out.println("Yor're sucsessful connected at "+getDate());// ������� ��������� ������� (������� ���������� ������->������)
      System.out.println("Connection accepted: "+ socket);// ����� �� ������� �������
      while (true)// ����������� ����
      {
       String str = "["+name+"]: ";// ��������� ���������� � [���]:
       str += in.readLine();// ��� ��������� �� �������
       if (str.equals("["+name+"]: "+"!exit!")) {break;};// ���� ������ ����� - ������� � ��
       chatServer.sent(str,ID);// �������� ��������� ��������� ��������
       this.currentThread().sleep(100);// �������� �� 0,1 �������
      }
      socket.close();//��������� �����
      chatServer.sent("[--++:"+name+" are leaving at "+getDate()+":++--]",ID);// �������� ��������� �� ����� �������
      chatServer.exit(ID);// �������� ������� � �������
     }
     catch(IOException e){System.out.println("Error: :"+e.toString());}// ������������� ����������
     catch(InterruptedException ie){System.out.println("Error: :"+ie.toString());}// � ������� �� ������� �������
  }
  public String getDate()// ��������� ����
  {
    String date = new String();// ������ � �����
    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);// ��������� ����
    int minute = Calendar.getInstance().get(Calendar.MINUTE);// ��������� ������
    int second = Calendar.getInstance().get(Calendar.SECOND);// ��������� �������
    if (hour<10) {date+="0"+hour+":";}// ���� ��� �� 1 ����� - ��������� 0
    else {date+=hour+":";}// ��������� ���
    if (minute<10) {date+="0"+minute+":";}// ���� ������ �� 1 ����� - ��������� 0
    else {date+=minute+":";}// ��������� ������
    if (second<10) {date+="0"+second;}// ���� ������� �� 1 ����� - ��������� 0
    else {date+=second;};// ��������� �������
    return date;// ���������� ���������
  }
}