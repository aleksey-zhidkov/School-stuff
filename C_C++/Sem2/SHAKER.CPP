#include <conio.h>
#include <stdlib.h>
#include <stdio.h>

int mas[10];
int i,j,x,l,r,fl;

void main()
{
 clrscr();
 randomize();
 for (i=0;i<10;i++)
 {
  mas[i]=random(100)+1;  //zadaem massiv randomom
  printf("%i ",mas[i]);
 }
 l=0;// \ zadaem iznachlnie granici
 r=9;// /
 do//(repeat) delay..
 {
  fl=1; // govorim chto massiv otsortirovan i nado vity iz cikla (sm nizhe)
  for (j=r;j>=l;j--)// ot pravoy do levoy granici delay..
  {
   if (mas[j-1]>mas[j])//elsi pred elt>tekyshego,to..
   {
    fl=0;//govorim chto massiv eshe ne otsort i ne nado vihodit iz cikla
    x=mas[j-1];     // \
    mas[j-1]=mas[j];//  >meniaem mestami elti
    mas[j]=x;       // /
   }
  }
  l++;// uvelichivaem levuu granicy
  for (j=l;j<=r;j++)// ot levoy do pravoy granici delaem..
  {
   if (mas[j-1]>mas[j])//elsi pred elt>tekyshego,to..
   {
    fl=0;//govorim chto massiv eshe ne otsort i ne nado vihodit iz cikla
    x=mas[j-1];     // \
    mas[j-1]=mas[j];//  >meniaem mestami elti
    mas[j]=x;       // /
   }
  }
  r--;// umenshaem pravuu granicy
 }
 while ((l<=r)||(fl==0));// (until) poka levaia granica< pravoy (to est otsort chasti ne vstritilis) ili ne pora vity iz cikla
			 // V C naoborot do..while krutitsia poka uslovie istinno
 printf("\n"); //perevodim kursor na novuu stroky
 for (i=0;i<10;i++)
 {
  printf("%i ",mas[i]); //vivodim otsort massiv
 }
 getch();
}