#include <conio.h>
#include <stdio.h>
#include <stdlib.h>

#define n 5

int mas[n];
int i,j,s,s1,ch;

void main()
{
 randomize();
 clrscr();
 for (i=0;i<n;i++)
 {
  mas[i]=random(9)+1;
  printf("%i ",mas[i]);
 }
 printf("\nVvedite S\n");
 scanf("%i",&s);
 ch=0;
 for (i=0;i<n;i++)
 {
  j=i;
  s1=0;
  while ((s1+mas[j]<=s)&&(j<n))
  {
   s1=s1+mas[j];
   j++;
  }
  if (s1==s) {ch++;};
 }
 printf("Otvet: %i",ch);
 getch();
}







