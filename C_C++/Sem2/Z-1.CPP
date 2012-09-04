#include <conio.h>
#include <stdlib.h>
#include <stdio.h>

int mas[10];
int i,j,ran;

void main()
{
 clrscr();
 randomize();
 for (i=0;i<10;i++)
 {
  ran=random(5);
  if (ran==0) {mas[i]=0;}
  else {mas[i]=random(100);};
  printf("%i ",mas[i]);
 }
 i=0;
 while ((mas[i]!=-1)&&(i<10))
 {
  if (mas[i]==0)
  {
   for (j=i;j<9;j++)
   {
    mas[j]=mas[j+1];
   }
   mas[9]=-1;
  }
  else {i++;};
 }
 i=9;
 while (mas[i]==-1)
 {
  mas[i]=0;
  i--;
 }
 printf("\n");
 for (i=0;i<=9;i++)
 {
  printf("%i ",mas[i]);
 }
 printf("\n");
 getch();
}