#include <stdio.h>
#include <conio.h>
#include <stdlib.h>

#define n 10
#define true 1
#define false 0

FILE *f1;
FILE *f2;
FILE *f3;

int mas[n];
int i,j,p,f1t,f2t;

void qs(int l,int r);

void qs(int l,int r)
{
 int w,x,j;
 i=l;
 j=r;
 p=(l+r)/2;
 x=mas[p];
 do
 {
  while (mas[i]<x)
  {
   i++;
  };
  while (x<mas[j])
  {
   j--;
  };
  if (i<=j)
  {
   w=mas[i];
   mas[i]=mas[j];
   mas[j]=w;
   i++;
   j--;
  }
 }
 while (i<=j);
 if (l<j) {qs(l,j);};
 if (i<r) {qs(i,r);};
}


void main()
{
 randomize();
 clrscr();
 for (i=0;i<n;i++)
 {
  mas[i]=random(100);
 }
 qs(0,n-1);
 printf("\n file1: ");
 f1=fopen("f1.num","wt");
 for (i=0;i<n;i++)
 {
  fputc(mas[i],f1);
 }
 fclose(f1);
 f1=fopen("f1.num","rt");
 for (i=0;i<n;i++)
 {
  printf("%i ",fgetc(f1));
 }
 fclose(f1);
 for (i=0;i<n;i++)
 {
  mas[i]=random(100);
 }
 qs(0,n-1);
 f2=fopen("f2.num","wt");
 for (i=0;i<n;i++)
 {
  fputc(mas[i],f2);
 }
 printf("\n file2: ");
 fclose(f2);
 f2=fopen("f2.num","rt");
 for (i=0;i<n;i++)
 {
  printf("%i ",fgetc(f2));
 }
 fclose(f2);
 printf("\n");
 f1=fopen("f1.num","rt");
 f2=fopen("f2.num","rt");
 f3=fopen("f3.num","wt");
 f1t=true;
 f2t=true;
 do
 {
  if ((f1t==true)&&(feof(f1)==0)) {i=fgetc(f1);}
  if ((f2t==true)&&(feof(f2)==0)) {j=fgetc(f2);}
  if ((feof(f1)==0)&&(feof(f2)==0))
  {
   if (i>=j)
   {
    fputc(j,f3);
    f1t=false;
    f2t=true;
   };
   if (i<j)
   {
    fputc(i,f3);
    f1t=true;
    f2t=false;
   };
  }
  else
  {
   if (feof(f2)!=0)
   {
    do
    {
     fputc(i,f3);
     i=fgetc(f1);
    }
    while (feof(f1)==0);
   }
   else
   {
    do
    {
     fputc(j,f3);
     j=fgetc(f1);
    }
    while (feof(f2)==0);
   }
  }
 }
 while ((feof(f1)==0)||(feof(f2)==0));
 fclose(f1);
 fclose(f2);
 fclose(f3);
 f3=fopen("f3.num","rt");
 printf("\n file3: ");
 for (i=0;i<n*2;i++)
 {
  printf("%i ",fgetc(f3));
 }
 getch();
}









