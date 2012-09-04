#include <string.h>
#include <conio.h>
#include <stdio.h>
#include <alloc.h>

#define true 1
#define false 0

typedef struct linkList
{
 int tagertPosition;
 linkList*next;
};

typedef struct elementsList
{
 char element[25];
 linkList* linksHead;
 elementsList*next;
};

elementsList* head;
int size;
int* linksArray;

void addElement(int i)
{
 elementsList*h=head;
 while(h->next!=NULL)
 {
  h=h->next;
 }
 printf("%i: ",i);
 elementsList*p=(elementsList*)malloc(sizeof(elementsList));
 scanf("%s",p->element);
 p->next=NULL;
 p->linksHead=NULL;
 h->next=p;
}

int searchElement(char word[100])
{
 elementsList* h=head;
 int isFind=false;
 int i=-1,j=1;
 while(isFind==false&&h!=NULL)
 {
  if(strcmp(word,h->element)==0){isFind=true;i=j;}
  else
  {
   h=h->next;
   j++;
  };
 }
 return i;
}

int addLink(char link[100],elementsList*h)
{
 int result = 1;
 char Fvortex[100];
 int i=0;
 for(;i<strlen(link);i++)
 {
  Fvortex[i]=link[i];
 }
 Fvortex[i]='\0';
 result=searchElement(Fvortex);
 if (result!=-1)
 {
  linksArray[result-1]++;
  linkList*l=h->linksHead;
  linkList*p=(linkList*)malloc(sizeof(linkList));
  p->tagertPosition=result;
  p->next=NULL;
  if (l==NULL)
  {
   h->linksHead=p;
  }
  else
  {
   while(l->next!=NULL)
   {
    l=l->next;
   }
   l->next=p;
  }
 }
 return result;
}

void deleteVortex(int i)
{
 int k=i;
 elementsList*h=head;
 elementsList*hd;
 for (int j=0;j<i-1;j++)
 {
  h=h->next;
 }
 if (k!=0) {hd=h->next;}
 else {hd=h;};
 printf("-%s- ",hd->element);
 linkList* l=hd->linksHead;
 linkList* ln=hd->linksHead;
 while(l!=NULL)
 {
  linksArray[l->tagertPosition-1]--;
  ln=l->next;
  free(l);
  l=ln;
 }
}

void printElement(int i)
{
 elementsList*h=head;
 while(i>0)
 {
  h=h->next;i--;
 }
 printf("-%s- ",h->element);
}

void main()
{
 linksArray=NULL;
 head=NULL;
 clrscr();
 printf("Please, size of the set\n");
 scanf("%i",&size);
 for(int i=0;i<size;i++)
 {
  linksArray=(int*)realloc(linksArray,(sizeof(int)*size+1));
  linksArray[i]=0;
 }
 printf("Please, enter the elemnts of set:");
 printf("\n1: ");
 head=(elementsList*)malloc(sizeof(elementsList));
 scanf("%s",head->element);
 head->linksHead=NULL;
 head->next=NULL;
 for (i=2;i<=size;i++)
 {
  addElement(i);
 }
 elementsList *h=head;
 for(i=0;i<size;i++)
 {
  printf("Please, enter the links of %s\nTo finish type \'exit\'\n",h->element);
  char link[100];
  do
  {
   link[0]='\0';
   scanf("%s",&link);
   if (strcmp(link,h->element)==0) {printf("The graph must be acyclik!\n");};
   if (strcmp(link,"exit")!=0)
   {
    if (addLink(link,h)==-1)
    {
     printf("Incorrect votexes! Link isn't added!");
    }
   }
  }while(strcmp(link,"exit")!=0);
  h=h->next;
 }
 for (i=0;i<size;i++)
 {
  int j=0;
  while(linksArray[j]!=0)
  {
   j++;
  }
  deleteVortex(j);
  linksArray[j]=-1;
 }
 free(linksArray);
 h=head;
 elementsList*h2;
 while(h!=NULL)
 {
  h2=h->next;
  free(h);
  h=h2;
 }
 getch();
}



