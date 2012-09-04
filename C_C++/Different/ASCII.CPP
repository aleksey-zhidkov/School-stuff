#include <conio.h>
#include <stdio.h>
alloc
void main()
{
 clrscr();
 printf("\n%c %c %c %c %c %c\n",40,41,42,43,45,47);
 for(int i=48;i<58;i++)
 {
  printf("%c ",i);
 }
 printf("\n");
 for(i=97;i<123;i++)
 {
  printf("%c ",i);
 }
 printf("\n");
 for(i=2;i<26;i++)
 {
  int s=i*10;
  printf("\n%i: ",i);
  for (int j=s;j<s+10;j++)
  {
   printf("%c ",j);
  }
 }
 getch();
}