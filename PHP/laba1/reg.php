<?
  $name="";
  $email="";
  $login="";
  if (@$ok)
  {
   $hasError=0;
   $name=$HTTP_POST_VARS["name"];
   $email=$HTTP_POST_VARS["email"];
   $login=$HTTP_POST_VARS["login"];
   $pass=$HTTP_POST_VARS["pass"];
   $pass2=$HTTP_POST_VARS["pass2"];
   if (strlen($name)==0)
   {
     echo "<div style=\"color:red\">������ ������ ���!</div>";
     $hasError=1;
   }
   if (strlen($email)==0)
   {
     echo "<div style=\"color:red\">������ ������ email!</div>";
     $hasError=1;
   }
   if (strlen($login)==0)
   {
     echo "<div style=\"color:red\">������ ������ �����!</div>";
     $hasError=1;
   }
   if (strlen($pass)==0)
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
   if(!preg_match("/[\w]+(@)[\w]+(\.)[\w]+/","$email"))
   {
     echo "<div style=\"color:red\">������������ E-mail!</div>";
     $hasError=1;
   }   
   if ($hasError==0)
   {
     if(mysql_connect("127.0.0.1","root",""))
     {
       $db=mysql_select_db("l1_db");
       $s=mysql_query("SELECT * FROM users WHERE login='".$login."'");
       $m=mysql_fetch_array($s);    
       if (count($m)!=1)
       {
         echo "<div style=\"color:red\">��������, �� ������������ � ����� ������� ��� ����������,<br>������� ������ �����<br></div>";
       }
       else
       {
         $add="INSERT INTO `users` ( `name` , `email` , `login` , `pass` ) VALUES ('".trim($name)."', '".trim($email).
              "', '".trim($login)."', '".md5(trim($pass))."')";
         $r=mysql_query($add);
         if ($r)
         {
           echo "<h1>�����������! �� ������� ������������������!</h1>";
           echo "<a href=\"index.html\">�����</a>";
           exit();
         }
         else
         {
           echo "<h1 style=\"color:red\">������!</h1>";
           echo "<div style=\"color:red\">����������� �� ����������, ���������� ���.</div>";
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
 <h2 align=center>����� ���������� �� ��������������� �����</h2>
 <hr>
 <center>
 <form method=post action="<?= $PHP_SELF ?>">
  <table>
    <tr>
     <td>���: </td>
     <td><input type=text name=name value="<?= $name ?>"></td>
    </tr>
     <td>E-mail: </td>
     <td><input type=text name=email value="<?= $email ?>"></td>
    </tr>
     <td>�����: </td>
     <td><input type=text name=login value="<?= $login ?>"></td>
    </tr>
     <td>������: </td>
     <td><input type=password name=pass></td>
    </tr>
     <td>��� ���: &nbsp&nbsp&nbsp&nbsp</td>
     <td><input type=password name=pass2></td>
    </tr>
    <th colspan=2><input type=submit name="ok" value="Registration"></th>
  </table>
 </form>
 </center>
 [ <a href=index.html> ����� </a> | <a href=view.php> ������������ </a> | 
   <a href=update.php> �������������� </a> | <a href=delete.php> �������� </a> ]
</body>
</html>