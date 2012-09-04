#include <conio.h>
#include <stdio.h>

#define n 10

int mas[n];

int i,j;

void main()
{
 clrscr();
 for (i=0;i<n;i++)
 {
  mas[i]=i+1;
  printf("%i ",mas[i]);
 }
 for (i=0;i<n;i++)
 {
  j=i+3;
  if (j>=n) {j=j-n;};
  mas[i]=mas[i]+mas[j];
  mas[j]=mas[i]-mas[j];
  mas[i]=mas[i]-mas[j];
  printf("\n");
  for (j=0;j<n;j++)
  {
   printf("%i ",mas[j]);
  }
 }
 getch();
}