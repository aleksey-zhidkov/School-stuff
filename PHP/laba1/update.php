<?
  $name="";
  $email="";
  $login="";
  $uname="";
  if (@$ok)
  {
   $hasError=0;
   $name=$HTTP_POST_VARS["name"];
   $email=$HTTP_POST_VARS["email"];
   $login=$HTTP_POST_VARS["login"];
   $pass=$HTTP_POST_VARS["pass1"];
   $pass2=$HTTP_POST_VARS["pass2"];
   $uname=$HTTP_POST_VARS["uname"];
   $upass==$HTTP_POST_VARS["upass"];
   if (strlen($upass)==0)
   {
     echo "<div style=\"color:red\">Забыли ввести пароль!</div>";
     $hasError=1;
   }
   if (strcmp($pass,$pass2)!=0)
   {
     echo "<div style=\"color:red\">Несоответствие первого и второго паролей!</div>";
     $hasError=1;
   }
   if (strchr($name,'<') || strchr($login,'<'))
   {
     echo "<div style=\"color:red\">Нельзя использовать симвылы &lt и &gt!</div>";
     $hasError=1;
   }
   if(!(preg_match("/[\w]+(@)[\w]+(\.)[\w]+/","$email")&&strlen($email)))
   {
     echo "<div style=\"color:red\">Некорректный E-mail!</div>";
     $hasError=1;
   }   
   if ($hasError==0)
   {
     if(mysql_connect("127.0.0.1","root",""))
     {
       mysql_select_db("l1_db");
       if (!(mysql_query("SELECT * FROM users WHERE login='".$uname."' and pass='".md5(trim($upass))."'")))
       {
         echo "<div style=\"color:red\">Некорректные имя и/или пароль</div>";
         echo "[ <a href=index.html> Назад </a> | <a href=reg.php> Регистрация </a> | 
               <a href=delete.php> Удаление </a> | <a href=view.php> Просмотр </a> ]";
         exit;
       }       
       $db=mysql_select_db("l1_db");
       $s=mysql_query("SELECT * FROM users WHERE login='".$login."'");
       $m=mysql_fetch_array($s);    
       if (count($m)!=1)
       {
         echo "<div style=\"color:red\">Извините, но пользователь с таким логином уже существует,<br>Введите другой логин<br></div>";
       }
       else
       {
         $str="UPDATE users SET ";
         if(strlen($name)!=0)
         {
           $str=$str."name='".trim($name)."'"; 
         }
         if(strlen($email)!=0)
         {
           $str=$str." ,email='".trim($email)."'"; 
         }
         if(strlen($login)!=0)
         {
           $str=$str." ,login='".trim($login)."'"; 
         }
         if(strlen($pass)!=0)
         {
           $str=$str." ,pass='".md5(trim($pass))."'"; 
         } 
         $str=$str." WHERE login='".$uname."'";
         $s=mysql_query($str);
         if ($s)
         {
           echo "<div style=\"color:red\">Операция прошла успешно</div>";
           echo "[ <a href=index.html> Назад </a> | <a href=reg.php> Регистрация </a> | 
                 <a href=delete.php> Удаление </a> | <a href=view.php> Просмотр </a> ]";
         }
         else
         {
           echo "<div style=\"color:red\">Ошибка! Данные <b>НЕ</b> изменены</div>";

           echo $str;
         }
       }
     } 
     else
     {
       echo "<h3 style=\"color:red\">Ошибка подключения к Базе Данных!<br>Пожалуйста, попробуйте позднее.</h3>";
     }
   }
  }
?>
<html>
<body>
  <h2>Страничка редоктирования пользователей</h2>
  Все поля не обязательны для заполнения.
  <form method=post action=<?= $PHP_SELF ?>>
    <table>
      <tr>
        <td>Новое имя: </td>
        <td><input type=text name="name" value="<?= $name ?>"></td>        
      </tr>
      <tr>
        <td>Новоый E-Mail: </td>
        <td><input type=text name="email" value="<?= $email ?>"></td>        
      </tr>
      <tr>
        <td>Новый Логин: </td>
        <td><input type=text name="login" value="<?= $login ?>"></td>        
      </tr>
      <tr>
        <td>Новый пароль: </td>
        <td><input type=password name="pass1"></td>        
      </tr>
      <tr>
        <td>Новый пароль2: </td>
        <td><input type=password name="pass2"></td>        
      </tr>
      <tr>
        <td>Логин: </td>
        <td><input type=text name="uname" value="<?= $uname ?>"></td>        
      </tr>
      <tr>
        <td>Пароль: </td>
        <td><input type=password name="upass"></td>        
      </tr>
      <th colspan=2>
        <input type=submit name=ok value="Обновить">
      </th>
    </table>
  </form>
 [ <a href=index.html> Назад </a> | <a href=reg.php> Регистрация </a> | 
 <a href=delete.php> Удалениу </a> | <a href=view.php> Просмотр </a> ] 
</body>
</html>