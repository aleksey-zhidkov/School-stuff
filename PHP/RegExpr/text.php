<?
  $arr=array();
  $N=150000;
  for ($i=0;$i<$N;$i++)
  {
    $str="";
    $bs=rand(1,10);
    $as=rand(1,3);
    for($j=0;$j<$bs;$j++)
    {
      $str.=chr(rand(1,24)+64);
    }
    $str.=".";
    for($j=0;$j<$as;$j++)
    {
      $str.=chr(rand(1,24)+64);
    } 
    $arr[]=$str;
  }
  $arr1=array();
  $estart=time();
  for ($i=0;$i<$N;$i++)
  {
    $j=0;
    $arr1[$i]=$arr[$i];
    for (;;) if ($arr[$i][$j++]==".") break;
    $arr1[$i][$j++]="O"; 
    $arr1[$i][$j++]="U"; 
    $arr1[$i][$j++]="T"; 
    $arr1[$i][$j++]="\r"; 
  }
  $efinish=time();
  echo "Елементарный алгоритм: ";
  echo $efinish;
  echo '-';
  echo $estart;
  echo '=';
  echo $efinish-$estart;
  echo "<br>";
  $arr2=array();
  $sstart=time();
  for ($i=0;$i<$N;$i++)
  {
    $pos=strpos($arr[$i],".");
    $arr2[$i]=substr($arr[$i],0,$pos).".OUT";
  }
  $sfinish = time();
  echo "Алгоритм: ";
  echo $sfinish;
  echo '-';
  echo $sstart;
  echo '=';
  echo $sfinish-$sstart;
  echo "<br>";
  $arr3=array();
  $rstart=time();
  for ($i=0;$i<$N;$i++)
  {
    $arr3[$i]=ereg_replace("([[:alnum:]]*)\.([[:alnum:]]*)",'\\1.OUT',$arr[$i]);
  }
  $rfinish=time();
  echo "Выражение: ";
  echo $rfinish;
  echo '-';
  echo $rstart;
  echo '=';
  echo $rfinish-$rstart;
  echo "<br>";

  echo "<hr>";
  $string="На сайте http://www.mysite.myhost.ru можно найти...";
  echo "Строка: ".$string."<br>";
  echo ereg_replace("((.)*)((http|ftp|gopher)://www\.(([[:alnum:]])+\.)+[[:alpha:]]{1,4})+((.)*)",
                    '\\1<a href="\\3">\\3</a>\\7',$string);
  echo "<hr>";
  $string2="Мой адресс: myname@myhost.ru, можете писать...";
  echo "Строка2: ".$string2."<br>";
  echo ereg_replace("((.)*)( (([[:alnum:]])+)@{1}(([[:alnum:]])+)\.([[:alnum:]])+)+((.)*)",
                    ' \\1<a href="mailto:\\3">\\3</a>\\9',$string2);
?>















