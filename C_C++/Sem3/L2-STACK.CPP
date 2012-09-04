#include <conio.h>
#include <stdio.h>
#include <alloc.h>
#include <stdlib.h>
#include <string.h>

typedef struct stack
{
 char s;
 stack* next;
};

typedef struct stacki
{
 int data;
 stacki* nexti;
};

#define true 1
#define false 0

int isStackEmpty=true;
int isStackEmptyi=true;
stack* head;
stacki* headi;
char instr[256];
char outstr[256];

void pushi(int i)
{
  if(isStackEmptyi==true)
  {
   headi=(stacki*)malloc(sizeof(stacki));
   headi->nexti=NULL;
   headi->data=i;
   isStackEmptyi=false;
   return;
  }
  stacki* h;
  h=(stacki*)malloc(sizeof(stacki));
  h->nexti=headi;
  h->data=i;
  headi=h;
}

void push(char e)
{
  if(isStackEmpty==true)
  {
   head=(stack*)malloc(sizeof(stack));
   head->next=NULL;
   head->s=e;
   isStackEmpty=false;
   return;
  }
  stack* h;
  h=(stack*)malloc(sizeof(stack));
  h->next=head;
  h->s=e;
  head=h;
}

char pop()
{
 char c;
 if(isStackEmpty==true)
 {
  c='\0';
  printf("Error! Stack is Empty!");
 }
 else
 {
  c=head->s;
  stack* h=head->next;
  free(head);
  head=h;
  if (head==NULL) {isStackEmpty=true;};
 }
 return c;
}

int popi()
{
 int i;
 if(isStackEmptyi==true)
 {
  i=-1;
  printf("Error! Stack is Empty!");
 }
 else
 {
  i=headi->data;
  stacki* h=headi->nexti;
  free(headi);
  headi=h;
  if (headi==NULL) {isStackEmptyi=true;};
 }
 return i;
}

void printStack()
{
 stack* h=head;
 do
 {
  printf("%c ",h->s);
  h=h->next;
 }while (h!=NULL);
}

int getPriority(char e)
{
  int priority=-1;
  if (e==40||e==41) priority=0;
  if (e==43||e==45) priority=1;
  if (e==42||e==47) priority=2;
  return priority;
}

int isCorrect()
{
 int iC=true;
 int j=0;
 char tmp1[256];
 char tmp2[256];
 strcpy(tmp1,instr);
 if (strlen(tmp1)%2==0)
 {
  iC=false;
 }
 else
 {
  for (int i=0;i<strlen(tmp1);i++)
  {
   if ((tmp1[i]!=40&&tmp1[i]!=41&&tmp1[i]!=42&&tmp1[i]!=43&&tmp1[i]!=45&&tmp1[i]!=47)&&
       (tmp1[i]<48||tmp1[i]>58)&&(tmp1[i]<97||tmp1[i]>123))
   {
    iC=false;
   };
   if (getPriority(tmp1[i])!=0)
   {
    tmp2[j]=tmp1[i];
    j++;
   }
   else
   {
    if (tmp1[i]==40)
    {
     push('(');
    }
    else
    {
     if (isStackEmpty==true)
     {
      iC=false; break;
     };
     pop();
    }
   }
  }
  if (isStackEmpty==false) {iC=false;};
  tmp2[j]='\0';
  int n=strlen(tmp2)/2;
  for (i=0;i<n;i=i+2)
  {
   if (getPriority(tmp2[i])>0)
   {
    iC=false;
   };
   if (getPriority(tmp2[i+1])==-1)
   {
    iC=false;
   }
  }
  if (getPriority(tmp2[i])>0)
  {
   iC=false;
  }
 }
 return iC;
}

void main()
{
 clrscr();
 printf("Type the expression\n");
 gets(instr);
 int j=0;
 if(isCorrect()==false)
 {
  printf("This isn't correct expression");
 }
 else
 {
  for (int i=0;i<strlen(instr);i++)
  {
   if (getPriority(instr[i])==-1) {outstr[j]=instr[i];j++;}
   else
   {
    if (instr[i]==40) {push(instr[i]);}
    else
    {
     if (instr[i]==41)
     {
      while (head->s!=40)
      {
       outstr[j]=pop();
       j++;
      }
      pop();
     }
     else
     {
      if (getPriority(head->s)<getPriority(instr[i]))
      {
       push(instr[i]);
      }
      else
      {
       while(getPriority(head->s)>getPriority(instr[i]))
       {
	outstr[j]=pop();j++;
       }
       push(instr[i]);
      }
     }
    }
   }
  }
  while(head!=NULL)
  {
   outstr[j]=pop();j++;
  }
 }
 printf("%s",outstr);
 int isSym=false;
 for (int i=0;i<strlen(outstr);i++)
 {
  if (outstr[i]>96&&outstr[i]<124) {isSym=true;};
 }
 if (isSym==false)
 {
  printf(" = ");
  for(int i=0;i<strlen(outstr);i++)
  {
   if (getPriority(outstr[i])==-1)
   {
    pushi(outstr[i]-48);
   }
   else
   {
    int ne,s1,s2;
    ne=0;
    s1=popi();
    s2=popi();
    switch (outstr[i])
    {
     case 43:ne=s1+s2;break;
     case 45:ne=s1-s2;break;
     case 42:ne=s1*s2;break;
     case 47:ne=s1/s2;break;
    }
    pushi(ne);
   }
  }
  printf("%i",popi());
 }
 getch();
}