<?
  $name="";
  if (@$ok)
  {
    $name=$HTTP_POST_VARS["login"];
    $pass=$HTTP_POST_VARS["pass"];
    if (strlen($name)==0)
    {
     echo "<div style=\"color:red\">������ ������ ��� ������������!</div>";
    }
    if (strcmp($pass,"admin")==0)
    {
      mysql_connect("127.0.0.1","root","");
      $bd=mysql_select_db("l1_db");  
      if(mysql_query("SELECT * FROM users WHERE name='admin' and pass='".md5(trim($pass))."'"))
      {
       $s=mysql_query("SELECT * FROM users WHERE login='".$login."'");
       $m=mysql_fetch_array($s);    
       if (count($m)==1)
       {
         echo "<div style=\"color:red\">��������, �� ������ ������������ �� ��������������������,<br>������� ������ �����<br></div>";
       }
       else
       {
        $r=mysql_query("DELETE FROM users WHERE login='".$name."'");      
        if ($r)
        {
          echo "<div style=\"color:red\">������������ ".$name." ��� ������� �����.</div>";
          echo "[ <a href=index.html> ����� </a> | <a href=reg.php> ����������� </a> | 
                <a href=update.php> �������������� </a> | <a href=view.php> �������� </a> ]";
          exit;
        }  
       }
      }
    }
    else
    {
     echo "<div style=\"color:red\">�������� ������!</div>";
    }
  }  
?>
<html>
<body>
  <h2>�������� �������������</h2>
  <form method=post action=<?= $PHP_SELF ?>>
    <table>
      <tr>
         <td>������ ������: </td>
         <td><input type=password name=pass></td>
      </tr>
      <tr>
         <td>����� ������������: </td>
         <td><input type=text name=login value="<?= $name ?>"></td>
      </tr>
      <th colspan=2><input name=ok type=submit value="�������"></th>
    </table>
  </form>
 [ <a href=index.html> ����� </a> | <a href=reg.php> ����������� </a> | 
   <a href=update.php> �������������� </a> | <a href=view.php> �������� </a> ]  
</body>
</html>