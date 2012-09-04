#include<string.h>
#include<conio.h>
#include<stdio.h>

char ch[10],zap[10];

void permut(char *res,char *src)
{
 int l=strlen(src);
 if (l==0) printf("%s\n",res);
 else
 for(int i=0;i<l;i++)
 {
  int dl=strlen(res);
  res[dl]=src[i];
  res[dl+1]=0;
  for(int k=i;k<l;k++)
  {
   src[k]=src[k+1];
  }
  permut(res,src);
  for(k=i;k<l-1;k++)
  {
   zap[k]=src[k];
  }
  src[i]=res[dl];
  for(k=i;k<l-1;k++)
  {
   src[k+1]=zap[k];
  }
  res[dl]=0;
 }
}
void main()
{
 clrscr();
 printf("Vvedite simvoli\n");
 scanf("%s",&ch);
 permut("",ch);
 getch();
}