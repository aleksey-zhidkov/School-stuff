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
     echo "<div style=\"color:red\">������ ������ ������!</div>";
     $hasError=1;
   }
   if (strcmp($pass,$pass2)!=0)
   {
     echo "<div style=\"color:red\">�������������� ������� � ������� �������!</div>";
     $hasError=1;
   }
   if (strchr($name,'<') || strchr($login,'<'))
   {
     echo "<div style=\"color:red\">������ ������������ ������� &lt � &gt!</div>";
     $hasError=1;
   }
   if(!(preg_match("/[\w]+(@)[\w]+(\.)[\w]+/","$email")&&strlen($email)))
   {
     echo "<div style=\"color:red\">������������ E-mail!</div>";
     $hasError=1;
   }   
   if ($hasError==0)
   {
     if(mysql_connect("127.0.0.1","root",""))
     {
       mysql_select_db("l1_db");
       if (!(mysql_query("SELECT * FROM users WHERE login='".$uname."' and pass='".md5(trim($upass))."'")))
       {
         echo "<div style=\"color:red\">������������ ��� �/��� ������</div>";
         echo "[ <a href=index.html> ����� </a> | <a href=reg.php> ����������� </a> | 
               <a href=delete.php> �������� </a> | <a href=view.php> �������� </a> ]";
         exit;
       }       
       $db=mysql_select_db("l1_db");
       $s=mysql_query("SELECT * FROM users WHERE login='".$login."'");
       $m=mysql_fetch_array($s);    
       if (count($m)!=1)
       {
         echo "<div style=\"color:red\">��������, �� ������������ � ����� ������� ��� ����������,<br>������� ������ �����<br></div>";
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
           echo "<div style=\"color:red\">�������� ������ �������</div>";
           echo "[ <a href=index.html> ����� </a> | <a href=reg.php> ����������� </a> | 
                 <a href=delete.php> �������� </a> | <a href=view.php> �������� </a> ]";
         }
         else
         {
           echo "<div style=\"color:red\">������! ������ <b>��</b> ��������</div>";

           echo $str;
         }
       }
     } 
     else
     {
       echo "<h3 style=\"color:red\">������ ����������� � ���� ������!<br>����������, ���������� �������.</h3>";
     }
   }
  }
?>
<html>
<body>
  <h2>��������� �������������� �������������</h2>
  ��� ���� �� ����������� ��� ����������.
  <form method=post action=<?= $PHP_SELF ?>>
    <table>
      <tr>
        <td>����� ���: </td>
        <td><input type=text name="name" value="<?= $name ?>"></td>        
      </tr>
      <tr>
        <td>������ E-Mail: </td>
        <td><input type=text name="email" value="<?= $email ?>"></td>        
      </tr>
      <tr>
        <td>����� �����: </td>
        <td><input type=text name="login" value="<?= $login ?>"></td>        
      </tr>
      <tr>
        <td>����� ������: </td>
        <td><input type=password name="pass1"></td>        
      </tr>
      <tr>
        <td>����� ������2: </td>
        <td><input type=password name="pass2"></td>        
      </tr>
      <tr>
        <td>�����: </td>
        <td><input type=text name="uname" value="<?= $uname ?>"></td>        
      </tr>
      <tr>
        <td>������: </td>
        <td><input type=password name="upass"></td>        
      </tr>
      <th colspan=2>
        <input type=submit name=ok value="��������">
      </th>
    </table>
  </form>
 [ <a href=index.html> ����� </a> | <a href=reg.php> ����������� </a> | 
 <a href=delete.php> �������� </a> | <a href=view.php> �������� </a> ] 
</body>
</html>