#include <conio.h>
#include <stdio.h>
#include <string.h>

FILE *f1;

#define false 0

int chars[256][2];
char ch;
char str[256][20];
char text[256];
int l,i,k;

void bub()
{
 for (i=0;i<=255;i++)
 {
  for (int j=255;j>i;j--)
  {
   if (chars[j][0]>chars[j-1][0])
   {
    int t=chars[j-1][0];
    chars[j-1][0]=chars[j][0];
    chars[j][0]=t;
    t=chars[j-1][1];
    chars[j-1][1]=chars[j][1];
    chars[j][1]=t;
    char c=str[j-1][0];
    str[j-1][0]=str[j][0];
    str[j][0]=c;
   }
  }
 }
 printf("\n");
 for (i=0;i<k;i++)
 {
  printf("%3i ",chars[i][0]);
 }
 printf("\n");
 for (i=0;i<k;i++)
 {
  printf("%3i ",chars[i][1]);
 }
 printf("\n");
 for (i=0;i<k;i++)
 {
  printf("%3c ",str[i][0]);
 }
}

void buildTree()
{
 char one,zero;
 zero='0';
 one='1';
 for (int j=1,i=k;j<=k;j++,i--)
 {
  int sch=0;
  while (sch<k-i)
  {
   str[sch][j]=one;
   sch++;
  }
  if (j!=k) {str[sch][j]=zero;};
 }
}
int seek(char t)
{
 int h=0;
 while(t!=str[h][0])
 {
  h++;
 }
 return(h);
}
void main()
{
 clrscr();
 for (i=0;i<=255;i++)
 {
  chars[i][0]=0;
  chars[i][1]=i;
 }
 f1=fopen("l10.txt","rt");
 printf("\n");
 k=0;
 int j=0;
 do
 {
  l++;
  ch=fgetc(f1);
  i=ch;
  text[j++]=ch;
  printf("%c",text[j]);
  if (i!=-1) {chars[i][0]++;};
  if (chars[i][0]==1)
  {
   str[i][0]=ch;
   k++;
  };
 }
 while (feof(f1)==false);
 printf("\n");
 if (chars[32][0]!=0) {printf("< > - %i\n",chars[32][0]);};
 for (i=1;i<=255;i++)
 {
  if ((chars[i][0]!=0)&&(i!=32)) {printf("%c - %i\n",i,chars[i][0]);};
 }
 bub();
 buildTree();
 printf("\n");
 int sch=0;
 for (j=1;j<=k;j++)
 {
  printf("\n%c - ",str[sch][0]);
  sch++;
  for (i=0;i<k-1;i++)
  {
   printf("%c",str[i][j]);
  }
 }
 printf("\n");
 printf("\n");
 char one,zero;
 zero='0';
 one='1';
 for (i=0;i<strlen(text)-1;i++)
 {
  int d=seek(text[i]);
  for (j=0;j<=k;j++)
  {
   if ((str[j][d+1]==zero)||(str[j][d+1]==one))
   {
    printf("%c",str[j][d+1]);
   }
  }
 }
 getch();
}




