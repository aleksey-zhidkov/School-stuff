#include <conio.h>
#include <stdio.h>
#include <stdlib.h>

#define n 11

int i,j,step;
int mas[n];

void main()
{
 clrscr();
 for (i=0;i<n-1;i++)
 {
  mas[i]=random(100);
  printf("%i ",mas[i]);
 }
 printf("\nVvedit shag\n");
 scanf("%i",&step);
 printf("\n");
 for (i=0;i<step;i++)
 {
  for (j=n;j>0;j--)
  {
   mas[j]=mas[j-1];
  }
  mas[0]=mas[n-1];
 }
 for (i=0;i<n-1;i++)
 {
  printf("%i ",mas[i]);
 }
 getch();
}