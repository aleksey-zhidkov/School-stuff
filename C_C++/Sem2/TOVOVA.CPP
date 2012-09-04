#include <conio.h>;
#include <stdio.h>;
#include <stdlib.h>;

#define n 10

int matrix[n][n];

int i,j,s,smax,imax;

void main()
{
 clrscr();
 randomize();
 smax=0;
 imax=0;
 for (i=0;i<n;i++)
 {
  s=0;
  for (j=0;j<n;j++)
  {
   matrix[i][j]=random(10);
   printf("%2i ",matrix[i][j]);
   s=s+matrix[i][j];
  }
  if (s>=smax) {smax=s;imax=i;};
  printf(" sum=%i \n",s);
 }
 printf("Max sum in %i string",imax+1);
 getch();
}