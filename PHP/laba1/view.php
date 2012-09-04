<html>
<body>
  <h2 align=center>Страница просмотра пользователей</h2>
  <table border=0 align=center width=75%>
<?
   mysql_connect("127.0.0.1","root","");
   $bd=mysql_select_db("l1_db");      
   $r=mysql_query("SELECT * FROM `users` ");
   while ($row = mysql_fetch_array($r)) 
        echo " <tr><td style=\"border: 1px black solid; background-color: silver;\">".$row["id"].
             "</td><td style=\"border: 1px black solid;\">".$row["name"].
             "</td><td style=\"border: 1px black solid; background-color: silver;\">".$row["login"].
             "</td><td style=\"border: 1px black solid;\">".$row["email"].            
             "</td></tr>";
?>
  </table>  
 [ <a href=index.html> Назад </a> | <a href=reg.php> Регистрация </a> | 
   <a href=update.php> Редоктирование </a> | <a href=delete.php> Удаление </a> ]
</body>
</html>