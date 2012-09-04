#include <conio.h>
#include <stdlib.h>
#include <stdio.h>

int mas[8];
int i,j,t,step;

void main()
{
 clrscr();
 randomize();
 for (i=0;i<8;i++)
 {
  mas[i]=random(80)+1; //zadaem i vivodim massiv
  printf("%i ",mas[i]);
 }
 for (i=1;i<=4;i++)
 {
  switch (i) {
	      case 1:step=4;break;
	      case 2:step=2;break;
	      case 3:step=1;break;
	     }
  for (j=0;j+step<=7;j++)
  {
   if (mas[j]>mas[j+step])
   {
    t=mas[j+step];
    mas[j+step]=mas[j];
    mas[j]=t;
   }
  }
 }
 printf("\n");// pereveli kursor na novuu stroku
 for (i=0;i<8;i++)
 {
  printf("%i ",mas[i]);//viveli massiv
 }
 getch();
}