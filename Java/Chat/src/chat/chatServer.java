// сервер чата
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

public class chatServer// класс сервера
{
 public static ArrayList clients = new ArrayList();// список клиентов
 public static ServerSocket s;// СерверныйСокет
 public static int PORT = 8582;// Порт, к которому коннектится 1 клиент
 public static void main(String args[])throws IOException
 {
   System.out.println("Server started: ");// вывод сообщения на консоль
   try
   {
     do// бесконечный цикл
     {
       s = new ServerSocket(PORT);// создаём новый СерверныйСокет...
       Socket socket = s.accept();// ждём коннекта...
       clients.add(new Client(socket,clients.size()));// создаём обработчика клиента
       PORT++;// увиличиваем номер порта
     }
     while(true);// бесконечный цыкл
   }
   finally// всегда...
   {
    s.close();// закрываем сокет
   }
 }
 public static void exit(int id)
 {
   for (int i=id+1;i<clients.size();i++)// для всех клиентов чеё ид больше уходящего...
   {
     Client c = (Client) clients.get(i);// получаем клиента
     c.ID--;// уменьшаем ид
     c=null;// уничтожаем доп. ссылку
   }
   clients.remove(id);// удаляем клиента
   if (clients.size()<1)// если клиентов не осталось...
   {
     System.out.println("Server stopped");// выводим сообщение
     System.exit(0);// и выходим из программы
   };
 }

 public static void sent(String s,int ID)// рассылка сообщений
 {
   System.out.println(s);// вывод на консоль сервера
   for (int i=0;i<clients.size();i++)// для всех клиентов...
   {
     Client c = (Client) clients.get(i);// получаем клиента
     if (i!=ID) {c.out.println(s);};// если не он прислал, то отсылаем клиенту сообщение
   }
 }
}

