#include <conio.h>
#include <stdlib.h>
#include <stdio.h>

int mas[10];
int i,j,t;

void main()
{
 clrscr();
 randomize();
 for (i=0;i<10;i++)
 {
  mas[i]=random(100)+1;  //zadaem massiv randomom
  printf("%i ",mas[i]);
 }
 for (i=1;i<10;i++)
 {
  t=mas[i]; //sohroniaem i-tiy elt massiva
  j=i-1; //ykazivaem index do kotorogo massiv otsortirovan
  while ((t<mas[j])&&(j>=0)) //poka mesto 't' ne naideno..
  {
   mas[j+1]=mas[j]; //smeshaem elti v levo
   j--; // i umenshaem index ukozatela
  }
  mas[j+1]=t; //vstavliaem na nuzhnoe mesto t (j+1 potomu chto mi
	      //lishniy raz delaem j--
 }
 printf("\n"); //perevodim kursor na novuu stroky
 for (i=0;i<10;i++)
 {
  printf("%i ",mas[i]); //vivodim otsort massiv
 }
 getch();
}