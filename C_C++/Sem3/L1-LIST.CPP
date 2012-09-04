#include <conio.h>
#include <stdio.h>
#include <alloc.h>

typedef struct list
{
 int data;
 list* next;
};
list* head;
int listsize;
int isInitialized=0;

void createList()
{
 int e;
 printf("\nType first element\n");
 scanf("%i",&e);
 isInitialized=1;
 head=(list*)malloc(sizeof(list));
 head->data=e;
 head->next=NULL;
 listsize=1;
 printf("\n The list is sucsefful created!");
}

void printList()
{
 printf("\n");
 list* h=head;
 if (listsize==0)
 {
  printf("List is empty");
  return;
 };
 while(h!=NULL)
 {
  printf("%i ",h->data);
  h=h->next;
 }
}

void addLastElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.\n");
  return;
 }
 int e;
 list* h=head;
 printf("Type element\n");
 scanf("%i", &e);
 if (head==NULL)
 {
  head->data=e;
  head->next=NULL;
 }
 else
 {
  while(h->next!=NULL)
  {
   h=h->next;
  }
  list* p;
  p=(list*)malloc(sizeof(list));
  h->next=p;
  p->data=e;
  p->next=NULL;
 }
 listsize++;
 printList();
}

void addFirstElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.");
  return;
 }
 int e;
 printf("Type element\n");
 scanf("%i", &e);
 list* p=(list*)malloc(sizeof(list));
 p->next=head;
 p->data=e;
 head=p;
 printList();
 listsize++;
}

void addMiddleElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.");
  return;
 }
 if (listsize<=2)
 {
  printf("Too little elements. Need at least 2.");
  return;
 }
 int e,p,i=1;
 list* h=head;
 printf("Type element and position <= %i\n",listsize);
 scanf("%i %i",&e,&p);
 if (p==2) {p=p-2;}
 else p--;
 while(i++<p)
 {
  h=h->next;
 }
 list* q=(list*) malloc(sizeof(list));
 q->data=e;
 q->next=h->next;
 h->next=q;
 printList();
 listsize++;
}
void removeFirstElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.");
  return;
 }
 list* h=head->next;
 free(head);
 head=h;
 if (head==NULL) {isInitialized=0;};
 printList();
 listsize--;
}

void removeMiddleElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.");
  return;
 }
 if (listsize<=2)
 {
  printf("Too little elements. Need at least 2.");
  return;
 }
 printf("Type position\n");
 int p;
 scanf("%i",&p);
 if (p==1)
 {
  printf("\n To delete first element type 4\n");
  return;
 }
 else if(p==listsize)
 {
 {
  printf("\n To delete last element type 6\n");
  return;
 }
 }
 int i=1;
 list* h=head;
 if (p>2) p--;
 else p=p-2;
 while (i++<p)
 {
  h=h->next;
 }
 list* q=h;
 h=h->next;
 q->next=h->next;
 free(h);
 listsize--;
 printList();
}

void removeLastElement()
{
 if(isInitialized!=1)
 {
  printf("\n List isn't initialized!\n Type 7 for initialize.");
  return;
 }
 if (listsize==1)
 {
  free(head);
  isInitialized=0;
  return;
 }
 list* h=head;
 while (h->next->next!=NULL)
 {
  h=h->next;
 }
 list* p=h->next;
 h->next=NULL;
 free(p);
 listsize--;
 printList();
}

void removeList()
{
 for(int i=listsize;i>0;i--)
 {
  list* h=head;
  for(int j=0;j<=i;j++)
  {
   h=h->next;
  }
  free(h);
  listsize--;
 }
 free(head);
 head=NULL;
 isInitialized=0;
 printList();
}

void showMenu()
{
 int choose=0;
 do
 {
  printf("\n");
  printf("1. add element at ends of list\n");
  printf("2. add element at start of list\n");
  printf("3. add element at middle of list\n");
  printf("4. remove element from starts of list\n");
  printf("5. remove element from middle of list\n");
  printf("6. remove element from ends of list\n");
  printf("7. create list\n");
  printf("8. remove list\n");
  printf("9. exit\n");
  scanf("%i", &choose);
  switch (choose)
  {
   case 1: addLastElement();break;
   case 2: addFirstElement();break;
   case 3: addMiddleElement();break;
   case 4: removeFirstElement();break;
   case 5: removeMiddleElement();break;
   case 6: removeLastElement();break;
   case 7: createList();break;
   case 8: removeList();
  }
 }
 while(choose!=9);
}

void main()
{
 showMenu();
}