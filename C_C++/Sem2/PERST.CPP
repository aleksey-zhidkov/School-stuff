#include <stdio.h>
#include <conio.h>
#include <string.h>

int per[9];
char str[9];
int l,ai,aj,isOkg;
void toIntmas(char str[9]);
int findI(int per[9]);
int findJ(int per[9],int i);
int testisOk(int per[9]);

void exchange(int per[9],int i,int j);
void sort(int per[9],int i);

void sort(int per[9],int i)
{
 int t;
 int z=i;
 for (int j=l-1;j>i;j--)
 {
  for (int k=l-1;k>z;k--)
  {
   if (per[k]<per[k-1])
   {
    t=per[k-1];
    per[k-1]=per[k];
    per[k]=t;
   }
  }
 }
}

void exchange(int per[9],int i,int j)
{
 int t=per[i];
 per[i]=per[j];
 per[j]=t;
}

int findJ(int per[9],int i)
{
 int minj=l-1;
 int min=10;
 for (int j=l-1;j>i;j--)
 {
  if ((per[j]<min)&&(per[j]>per[i])) {min=per[j];minj=j;}
 }
 return(minj);
}

int findI(int per[9])
{
 int isOk=0;
 int i=l-2;
 do
 {
  if (per[i]>per[i+1]) {i--;}
  else {isOk=1;};
 }
 while (isOk==0);
 return(i);
}

void toIntmas(char str[9])
{
 for (int i=0;i<=l-1;i++)
 {
  per[i]=str[i]-48;
 }
}

int testisOk(int per[9])
{
 int t=0;
 for (int z=0;z<=l-2;z++)
 {
  if (per[z]<per[z+1]) {t=1;};
 }
 return(t);
}

void main()
{
 isOkg=1;
 clrscr();
 printf("Vvedite perestanovku\n");
 scanf("%s",&str);
 l=strlen(str);
 toIntmas(str);
 do
 {
  ai=findI(per);
  aj=findJ(per,ai);
  exchange(per,ai,aj);
  sort(per,ai+1);
  for (int z=0;z<=l-1;z++)
  {
   printf("%i",per[z]);
  }
  printf("\n");
  isOkg=testisOk(per);
 }
 while(isOkg==1);
 getch();
}








