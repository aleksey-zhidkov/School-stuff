#include <conio.h>
#include <stdio.h>
#include <stdlib.h>

#define n 6

int a[n];
int b[n];
int i,sa,sb;

void main()
{
 clrscr();
 randomize();
 printf("A: ");
 for (i=0;i<n;i++)
 {
  a[i]=random(9);
  printf("%i",a[i]);
 }
 printf("\n");
 printf("B: ");
 for (i=0;i<n;i++)
 {
  b[i]=random(9);
  printf("%i",b[i]);
 }
 sa=0;
 sb=0;
 for (i=0;i<n;i++)
 {
  if (a[i]-b[i]>0)
  {
   sa=sa+abs(a[i]-b[i]);
  }
  else
  {
   sb=sb+abs(a[i]-b[i]);
  }
 }
 printf("\n");
 printf("Sa=%i; Sb=%i\n",sa,sb);
 if (sa==sb) {printf("Nichia");};
 if (sa<sb) {printf("Pobedil B");}
 if (sa>sb) {printf("Pobedil A");}
 getch();
}