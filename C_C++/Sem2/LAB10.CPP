#include <conio.h>
#include <stdio.h>
#include <string.h>

FILE *f1;

#define false 0

int chars[256];
char ch;
int l,i;

void main()
{
 clrscr();
 for (i=0;i<=255;i++)
 {
  chars[i]=0;
 }
 f1=fopen("pr.txt","rt");
 printf("\n");
 do
 {
  ch=fgetc(f1);
  i=ch;
  printf("%c",ch);
  if (i!=-1) {chars[i]++;};
 }
 while (feof(f1)==false);
 printf("\n");
 printf("probeli - %i\n",chars[32]);
 for (i=1;i<=255;i++)
 {
  if ((chars[i]!=0)&&(i!=32)) {printf("%c - %i\n",i,chars[i]);};
 }
 fclose(f1);
 getch();
}