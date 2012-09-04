<?  
  Header("Content-type:application/msword");
  $f=fopen("sert.rtf","r");
  $s=fread($f,filesize("sert.rtf"));
  $s=str_replace("data1",$p1,$s);
  $s=str_replace("data2",$p2,$s);
  $date=date("d.m.y");
  $s=str_replace("date",$date,$s);
  echo $s;
?>