// клиент чата
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

public class chatClient// стартовый класс
{
  public static PrintWriter out;// писатель серверу
  public static BufferedReader in;// читатель сервера
  public static clientWriter cw;// класс-писатель
  public static clientReader cr;// класс-читатель
  public static String name = new String();// имя клиента
  public static int PORT=8582;// порт с которого начнём стучаться к серверу
  public static Socket socket;// сокет соеденения с сервером
  public static void main(String[] args) throws IOException
   {
    InetAddress addr = InetAddress.getByName(null);// получение адреса (считается, что сервер на той же машине,
                                                   // что и клиент
    name = new String(args[0]);// имя клиента
    boolean hasConnect;// успешно ли соеденение
    do
    {
      if (PORT>8632) {PORT=8582;};// если первые 50 портов заняты - начинаем сначала, в поиске свободного
      hasConnect=true;// считаем что соеденение успешно...
      socket = new Socket(addr, PORT);//получаем сокет
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);// получаем писателя
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// получаем читателя
      socket.setSoTimeout(3000);// устанавливаем задержку на ожидание ответа от сервера в 3 секунды
      try
      {
        System.out.println("Try connect to "+addr.getCanonicalHostName()+":"+PORT+"...");// выводим сообщение клиенту
        out.println(name);// отпровляем серверу имя
        System.out.println(in.readLine());// ждём ответа и выводим на экран (если есть)...
      }
      catch(SocketTimeoutException e)// если ответа не дождались
      {
       PORT++;// увеличиваем номер порта
       hasConnect=false;// считаем соеденение неудачным
       System.out.println("Faild...");// выводим сообщение клиенту
      };
    }while(!hasConnect);// пока  не установим успешное соеденение...
    socket.setSoTimeout(0);// снимаем ограничение на время ожидания ответа сервера
    cw = new clientWriter();// создаём читателя
    cr = new clientReader(); // создаём писателя
   }
}

class clientWriter extends Thread// писатель - потомок Потока
{
 public clientWriter() //конструктор
 {
   start();// запуск потока
 }
 public void run()// поток
 {
   try
   {
    String s;// сообщение серверу
    do
    {
     byte[] input= new byte[256];// массив байт считываемых с стандартного ввода
     System.in.read(input);// считывание сообщения введённого пользователем
     s = new String(input).trim();// удаление пробелов
     chatClient.out.println(s);// отправление сообщения серверу
     try
     {
       this.currentThread().sleep(100);// засыпаем на 0,1
     }
     catch (InterruptedException e) {System.out.println("Error: "+e.toString());}; //перехват ошыбок потока
    }
    while(!s.equals("!exit!"));// до тех пор, пока пользователь не введёт зарезервированное слово
   }
   catch(IOException e)// перехват ошибок ввода
   {
    System.out.println("Error: "+e.toString());// вывод сообщения об ошибке
   }
   finally// всегда...
   {
    System.out.print("closing...");// выводим сообщение клиенту
    try{chatClient.socket.close();}catch(IOException e){};// закрываем сокет
    System.exit(0);// выходим из программы
   }
 }
}

class clientReader extends Thread// читатель - потомок Потока
{
 public clientReader()// конструктор
 {
   start();// запуск потока
 }
 public void run()// поток
 {
   try
   {
    String s = new String();// сообщение от сервера
    do// бесконечный цикл
    {
     s = chatClient.in.readLine();// получение сообщения от сервера
     if (s!=null)// если сообщение не пусто....
     {
      System.out.println(s);// выводим на консоль
     };
     try
     {
       this.currentThread().sleep(100);// засыпаем на 0,1 секунды
     }
     catch (InterruptedException e)// перехват исключения
     {
      System.out.println("Error: "+e.toString());// вывод сообщения об ошибке
     };
    }
    while(true);// бесконечный цикл
   }
   catch(IOException e)// перехват исключения
   {
    System.out.println("Error: "+e.toString());// вывод сообщения об ошибке
   }
 }
}

