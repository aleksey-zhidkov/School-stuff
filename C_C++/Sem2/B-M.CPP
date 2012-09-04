#include <conio.h>
#include <stdio.h>
#include <string.h>

char s[256];
char q[256];
int d[256];
int n,m,i,j,k;

void main()
{
  printf("Vvedite stroky\n");
  gets(s);
  printf("Vvedite podstroky\n");
  gets(q);
  n=strlen(s);
  m=strlen(q);
  for (i=0;i<=255;i++)
  {
   d[i]=m;
  };
  for (i=1;i<m;i++)
  {
    d[q[i-1]]=m-i;
  };
  i=m-1;
  do
  {
    j=m-1;
    k=i;
    while ((j!=-1)&&(q[j]==s[k]))
    {
     k--;
     j--;
    };
    i=i+d[s[i]];
  }
  while ((j!=-1)&&(i<=n));
  if (j==-1) {printf("%i\n",k+2);}
  else {printf("net\n");};
 getch();
}