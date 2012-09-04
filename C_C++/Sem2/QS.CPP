#include <conio.h>
#include <stdlib.h>
#include <stdio.h>

int mas[10];
int i,p;

void qs(int l,int r);

void qs(int l,int r)
{
 int w,x,j;
 i=l;
 j=r;
 p=(l+r)/2;
 x=mas[p];
 do
 {
  while (mas[i]<x)
  {
   i++;
  };
  while (x<mas[j])
  {
   j--;
  };
  if (i<=j)
  {
   w=mas[i];
   mas[i]=mas[j];
   mas[j]=w;
   i++;
   j--;
  }
 }
 while (i<=j);
 if (l<j) {qs(l,j);};
 if (i<r) {qs(i,r);};
}

void main()
{
 clrscr();
 randomize();
 for (i=0;i<10;i++)
 {
  mas[i]=random(100)+1; //zadaem i vivodim massiv
  printf("%i ",mas[i]);
 }
 qs(0,9);
 printf("\n");// pereveli kursor na novuu stroku
 for (i=0;i<10;i++)
 {
  printf("%i ",mas[i]);//viveli massiv
 }
 getch();
}