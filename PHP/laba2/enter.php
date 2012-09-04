<?
  if (isset($HTTP_COOKIE_VARS["login"]))
  {
    $login=$HTTP_COOKIE_VARS["login"];
    $pass=$HTTP_COOKIE_VARS["pass"];
    if(mysql_connect("127.0.0.1","root",""))
    {
      $db=mysql_select_db("l2_db");
      $s=mysql_query("SELECT * FROM users WHERE login='".$login."' AND pass='".$pass."'");
      $r=mysql_fetch_array($s);      
      if ($r["status"]=="user")
      {
        $s = mysql_query("SELECT max(time) FROM users WHERE status='user'");
        $r = mysql_fetch_array($s);
        $s = mysql_query("SELECT * FROM users WHERE time='".$r["max(time)"]."'");
        $r2 = mysql_fetch_array($s);
        if( ($r["max(time)"]-time())>60*5 || $r2["login"]!=$login)
        {
          echo "Извените, но в данный момент ресурс занят";
          exit;
        }
        echo "Actions for user";
        $s=mysql_query("UPDATE users SET time='".time()."' WHERE login='".$login."'");
      }
      else if ($r["status"]=="admin")
      {
        $s = mysql_query("SELECT max(time) FROM users WHERE status='admin'");
        $r = mysql_fetch_array($s);
        $s = mysql_query("SELECT * FROM users WHERE time='".$r["max(time)"]."'");
        $r2 = mysql_fetch_array($s);
        if( ($r["max(time)"]-time())>60*5 || $r2["login"]!=$login)
        {
          echo "Извените, но в данный момент ресурс занят";
          exit;
        }
        echo "Actions for admin";
        $s=mysql_query("UPDATE users SET time='".time()."' WHERE login='".$login."'");
      }
      else
      {
        echo "registaration";
      }
    }
  }
  else
  {
   if (!@$ok)
   {
     $code= rand(0,999999);    
     echo "  <form method=post action=\"".$PHP_SELF."\">";
     echo "    <table>";
     echo "      <tr>";
     echo "       <td>Логин: </td>";
     echo "       <td><input type=text name=login value=\"\"></td>";
     echo "      </tr>";
     echo "       <td>Пароль: </td>";
     echo "       <td><input type=password name=pass value=\"\"></td>";
     echo "      </tr>";
     echo "       <td>Код: </td>";
     echo "       <td><input type=text name=ecode value=\"\"></td>";
     echo "      </tr>";
     echo "      <tr><th colspan=2><img src=\"code.php?code=".$code."\"></th></tr>";
     echo "      <th colspan=2><input type=submit name=ok value=\" Войти \"></th>    ";
     echo "    </table>";
     echo "    <input type=hidden name=code value=$code>";
     echo "  </form>";
     echo "  <div>Если не можете разобрать секретный код, нажмите \"Обновить\"/\"Refresh\"</div>";
   }
   else
   {
     $login=$HTTP_POST_VARS["login"];    
     $pass=$HTTP_POST_VARS["pass"];
     $code=$HTTP_POST_VARS["code"];
     $ecode=$HTTP_POST_VARS["ecode"];
     $hasError=0;
     if (!$login)
     {
       echo "Забыли ввести логин";
       $hasError=1;
     }
     if (!$pass)
     {
       echo "Забыли ввести пароль";
       $hasError=1;
     }
     if (!$ecode)
     {
       echo "Забыли ввести код";
       $hasError=1;
     }
     if ($code!=$ecode)
     {
       echo "Неверный код";
       $hasError=1;
     }
     if (!$hasError)
     {
      if(mysql_connect("127.0.0.1","root",""))
      {
         $db=mysql_select_db("l2_db");    
         $s=mysql_query("SELECT * FROM users WHERE login='".$login."' and pass='".md5(trim($pass))."'");
         $m=mysql_fetch_array($s);    
         if (count($m)!=1)
         {
           setcookie("login",$login,time()+3600*24*7);
           setcookie("pass",md5(trim($pass)),time()+3600*24*7);
           Header("Location: http://127.0.0.1/laba2/enter.php");
           exit;
         }  
         else
         {
           echo "Неверные логин или пароль";
           exit; 
         }
      }
     }
   }
 }
?>