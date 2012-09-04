#include <conio.h>
#include <stdio.h>
#include <alloc.h>
#include <string.h>
#include <ctype.h>

#define true 1
#define false 0

typedef struct tree
{
  char word[256];
  tree* leftSun;
  tree* rightSun;
};

tree* root;
char words[256];

int getWordsNumber()
{
 int number=0;
 for (int i=1;i<strlen(words)-1;i++)
 {
  if (words[i]==' '&&words[i+1]!=' ') number++;
 }
 return ++number;
}

int isLess(char w1[30], char w2[30])
{
 int i=0;
 int isFind=false;
 int isLess=false;
 while (isFind==false&&i<strlen(w1)&&i<strlen(w2))
 {
  if (tolower(w1[i])!=tolower(w2[i]))
  {
   isFind=true;
   if (tolower(w1[i])<tolower(w2[i]))
   {
    isLess=true;
   }
  }
  i++;
 }
 if (isFind==false&&i<strlen(w2))
 {
  isLess=true;
 }
 return isLess;
}

void showTree(tree* p)
{
 if (p->leftSun!=NULL)
 {
  showTree(p->leftSun);
 }
 {printf("%s\n",p->word);};
 if (p->rightSun!=NULL)
 {
  showTree(p->rightSun);
 }
}

tree* addElement(tree* p,char w[30])
{
 if (p==NULL)
 {
  p=(tree*)malloc(sizeof(tree));
  strcpy(p->word,w);
  p->leftSun=NULL;
  p->rightSun=NULL;
  if (root==NULL)
  {
   root=p;
  };
 }
 else
 {
  if (isLess(w,p->word))
  {
   p->leftSun=addElement(p->leftSun,w);
  }
  else
  {
   p->rightSun=addElement(p->rightSun,w);
  }
 }
 return p;
}

void main()
{
 root=NULL;
 printf("\nType words: \n");
 gets(words);
 int WordsN=getWordsNumber();
 printf("\nNumber of words = %i \n",WordsN);
 int j=0;
 j=0;
 for(int i=0;i<WordsN;i++)
 {
  char word[30];
  int k=0;
  while (words[j]==' '&&j<strlen(words))
  {
   j++;
  }
  while(words[j]!=' '&&j<strlen(words))
  {
   word[k]=words[j];k++;j++;
  }
  word[k]='\0';
  addElement(root,word);
 }
 showTree(root);
 getch();
}