#include <conio.h>
#include <string.h>
#include <stdio.h>

#define true 1
#define false 0

char str[255];
char shabl[255];
char shrev[255];
char curstr[255];
int i,j,k,isHave,isNeed;

void main()
{
 isHave=false;
 clrscr();
 printf("Vvedite stroky\n");
 gets(str);
 i=0;
 do
 {
  j=0;
  do
  {
   shabl[j]=str[i];
   i++;
   j++;
  }
  while ((str[i]!=32)&&(i<=strlen(str)-1));
  shabl[j]='\0';
  i++;
  if (strlen(shabl)>strlen(curstr))
  {
   isNeed=true;
   *shrev= *shabl;
   strrev(shrev);
   int t=strlen(shabl);
   if (strlen(shabl)==1)
   {
    isHave=true;
    for (j=0;j<=strlen(shabl)-1;j++)
    {
     curstr[j]=shabl[j];
    }
   }
  }
 }
 while(i<strlen(str)-1);
 if (isHave==true) {printf("%s",curstr);}
 else {printf("Net simmetrichnih slov");};
 getch();
}