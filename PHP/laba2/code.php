<?
   Header("Content-type:image/gif");
   $im= imageCreateFromGIF("bg.gif");
   $blue=imageColorAllocate($im,0,0,110);
   imageString($im,4,5,1,$code,$blue);
   imageGIF($im);
   imageDestroy($im);
?>