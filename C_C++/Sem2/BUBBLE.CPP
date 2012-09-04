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
  mas[i]=random(100)+1; //zadaem i vivodim massiv
  printf("%i ",mas[i]);
 }
 for (i=0;i<=9;i++) //povtoriaem po kol-vu eltov (ih 10 no indexi idut s 0)..
 {
  for (j=9;j>i;j--) //s konca massiva do konca otsortirovannoi chasti delaem..
  {
   if (mas[j]<mas[j-1])//esli tekushiy elt< predidushego (chto narushaet uporadochennost), to..
   {
    t=mas[j-1];//sohroniaem pred elt                       \
    mas[j-1]=mas[j];//pred eltu prisvaevaem znach tekushego >proizveli obmen 2 eltov narushayshih poradok
    mas[j]=t;//tekushemu prisvaevaem znachenie pred(t)     /
   }
  }
 }
 printf("\n");// pereveli kursor na novuu stroku
 for (i=0;i<10;i++)
 {
  printf("%i ",mas[i]);//viveli massiv
 }
 getch();
}