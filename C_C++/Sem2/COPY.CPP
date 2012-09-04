#include <stdio.h>
#include <conio.h>
#include <string.h>

char s[11];
char t[11];
char nul[]="";
int n,i,ind,l;

void find_p(char start[],char rest[],int m);
void copy(char s[],int ind,int l);

void copy(char s[],int ind,int l)
{
 int j=0;
 if (ind<0) {ind=0;};
 while ((j<l) && (ind<strlen(s)))
 {
  t[j]=s[ind];
  ind++;
  j++;
 }
 t[j]='\0';
}

void find_p(char start[],char rest[],int m)
{
 char t1[11];
 char t2[11];
 char t3[11]="";
 if (m==n) {printf("%s",start);}
 else
 {
  for (i=0;i<=strlen(rest);i++)
  {
   copy(rest,i,1);
   strcat(t1,t);
   copy(rest,0,i-1);
   strcat(t2,t);
   copy(rest,i+1,strlen(rest)-i);
   strcat(t3,t);
   find_p(strcat(start,t1),strcat(t2,t3),m+1);
  }
 };
}

void main()
{
 nul[0]='\0';
 scanf("%s",&s);
 n=strlen(s);
 printf("%s\n",t);
 find_p(nul,s,0);
 getch();
}