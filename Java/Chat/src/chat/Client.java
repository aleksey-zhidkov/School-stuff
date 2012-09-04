// объект-обработчик клиентов
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

public class Client extends Thread// наследник Потока
{
  public Socket socket;// сокет соеденения с клиентом
  public int ID;// ид клиента
  public BufferedReader in;// читатель сообщений от клиента
  public PrintWriter out;// писатель сообщений клиенту
  public String name;// имя клиента
  public Client(Socket socket,int ID)// конструктор
  {
    this.socket=socket;// получение сокета
    this.ID=ID;// получение ид
    start();// запуск потока
  }
  public void run()// поток
  {
    try
    {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// получение читателя
      name = in.readLine();// чтение имени от клиента (удачное соеденение клиент->сервер
      chatServer.sent("[--++:"+name+" are connecting at "+getDate()+":++--]",ID);// рассылка остальным клиентам
      System.out.println(socket);// вывод на консоль сервера
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);// получение писателя клиенту
      out.println("Yor're sucsessful connected at "+getDate());// посылка сообщения клиенту (удачное соеденение сервер->клиент)
      System.out.println("Connection accepted: "+ socket);// вывод на консоль сервера
      while (true)// бесконечный цикл
      {
       String str = "["+name+"]: ";// сообщение начинается с [имя]:
       str += in.readLine();// ждём сообщения от клиента
       if (str.equals("["+name+"]: "+"!exit!")) {break;};// если клиент вышел - выходим и мы
       chatServer.sent(str,ID);// рассылка сообщения остальным клиентам
       this.currentThread().sleep(100);// засыпаем на 0,1 секунды
      }
      socket.close();//закрываем сокет
      chatServer.sent("[--++:"+name+" are leaving at "+getDate()+":++--]",ID);// рассылка сообщения об уходе клиента
      chatServer.exit(ID);// удаления клиента с сервера
     }
     catch(IOException e){System.out.println("Error: :"+e.toString());}// перехватываем исключения
     catch(InterruptedException ie){System.out.println("Error: :"+ie.toString());}// и выводим на консоль сервера
  }
  public String getDate()// получение даты
  {
    String date = new String();// строка с датой
    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);// получение часа
    int minute = Calendar.getInstance().get(Calendar.MINUTE);// получение минуты
    int second = Calendar.getInstance().get(Calendar.SECOND);// получение секунды
    if (hour<10) {date+="0"+hour+":";}// если час из 1 цифры - добовляем 0
    else {date+=hour+":";}// добовляем час
    if (minute<10) {date+="0"+minute+":";}// если минута из 1 цифры - добовляем 0
    else {date+=minute+":";}// добовляем минуту
    if (second<10) {date+="0"+second;}// если секунда из 1 цифры - добовляем 0
    else {date+=second;};// добовляем секунды
    return date;// возвращяем результат
  }
}