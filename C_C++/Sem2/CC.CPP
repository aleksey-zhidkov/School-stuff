#include <stdio.h>
#include <conio.h>
#include <process.h>
#include <string.h>

int b1,b2,n,tip;
char num[255];
char digs[16]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};


int toInt(char t[255])
{
 int temp[5];
 for (int i=0;i<strlen(t);i++)
 {
  temp[i]=t[i]-48;
 }
 int k=1;
 int ch=0;
 for (i=strlen(t)-1;i>=0;i--)
 {
  ch=ch+temp[i]*k;
  k=k*10;
 }
 return(ch);
}

void a1(char num[255],int b2)
{
 int temp[255];
 int n=toInt(num);
 int k=0;
 do
 {
  b2=b2;
  temp[k]=n%b2;
  n=n/b2;
  k++;
 }
 while(n!=0);
 int j=0;
 for (int i=k-1;i>=0;i--)
 {
  num[j]=digs[temp[i]];
  j++;
 }
 num[j]='\n';
 printf("%s",num);
}

int st(int s,int l)
{
 for (int z=0;z<l-1;z++)
 {
  s=s*b1;
 }
 if (l==0) {s=1;};
 return(s);
}

int a2(int k,int b1,char num[255])
{
 int a,b;
 int c=0;
 int j=k-1;
 for (int i=0;i<k;i++)
 {
  if (num[i]<58) {a=(num[i]-48);}
  else {a=(num[i]-87);};
  b=st(b1,j);
  c=c+(a*b);
  j--;
 }
 return(c);
}

void toString(int g)
{
 char temp[255];
 int k=0;
 do
 {
  temp[k]=digs[g%10];
  g=g/10;
  k++;
 }
 while (g!=0);
 temp[k]='\0';
 int j=0;
 for (int i=k-1;i>=0;i--)
 {
  num[j]=temp[i];
  j++;
 }
 num[j]='\0';
}
void main()
{
 tip=0;
 printf("Vvedite b1 b2 n\n");
 scanf("%i %i %s",&b1,&b2,&num);
 if (b1==10) {tip=1;};
 if (b2==10) {tip=2;};
 if ((b1!=10)&&(b2!=10)) {tip=3;};
 if (b1==b2) {tip=0;};
 switch (tip)
 {
  case 0:printf("perevod ne trebuetsia");break;
  case 1:printf("Otvet: ");a1(num,b2);break;
  case 2:printf("Otvet: ");n=a2(strlen(num),b1,num);printf("%i",n);break;
  case 3:printf("Otvet: ");n=a2(strlen(num),b1,num);toString(n);a1(num,b2);break;
 }
 printf("\n");
 getch();
}