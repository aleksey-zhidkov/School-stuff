#include <iostream.h>
#include <stdlib.h>
#include <stdio.h>
#include <conio.h>

typedef struct vesh // struktura predstavl vesh
{
 int ves; // ves veshi
 int poleznost; // poleznost veshi
};

int* matrix; // peremennaia hroniashia matricu smezhnosti
vesh* nabor; // vse veshi
vesh* optnabor; //nabor s max poleznostu
vesh* teknabor; // massiv veshey, v kotoriy skladivaem veshi
int n;   // kol-vo veshey
int maxpol; // poleznost luchshego nabora
int maxves; // max ves rukzaka
int dlina=0; // kol-vo veshey v optimalnom nabore

void obhod(int i,int ves,int poleznost,int glubina)// Obhod matrixa
{
 if (poleznost>maxpol)  // esli tekushaa poleznost bolshe maximalnoy
 {
  dlina=glubina; // to ustanavliavem dlinu (glibina=kol-vo veshey v tekushem nabore +1)
  maxpol=poleznost; // ustanavlivaem max poleznost
  for (int k=0;k<glubina;k++)
  {
   optnabor[k]=teknabor[k];  // i copiruem vse veshi iz tekushego massiva v optimalniy
  }
 }
 for (int j=0;j<n;j++) // idem po vsem svazam vershini
 {
  if (matrix[i*n+j]!=0&&matrix[j*n+j]==0&&ves+nabor[i].ves<=maxves)
  // Esli est svaz, celevuu vershinu eshe ne poseshali i ves imuushihsa veshey +
  //                                                     ves tekushey veshi < max dopustimogo...
  {
   teknabor[glubina]=nabor[i];// dobovlaem vesh k tekushemu naboru
   matrix[i*n+i]=-1; // pomechem na deagonali vershinu kak poseshenuu
   obhod(j,ves+nabor[i].ves,poleznost+nabor[i].poleznost,glubina+1);// opuskaem na uroven nizhe (delaem obhod uzhe po j, pribavlaem ves,poleznost i uvelichivaem glubinu)
   matrix[i*n+i]=0; // snimaem metky poseshenia
  }
 }
}

int main()
{
 clrscr();// Ochistka ekrana
 cout<<"Vvedite razmer kolvo veshey\n";// Priglashenie vvesti kol-vo veshey
 cin>>n;// Schitivanie razmera
 maxpol=0; // inicializiruem max poleznost
 matrix=(int*)malloc(sizeof(int)*n*n);// Vidilenie pamati pod matricu smezhnosti
 optnabor=(vesh*)malloc(sizeof(vesh)*n); //Vidilenie pamati pod massiv opt nabira
 nabor=(vesh*)malloc(sizeof(vesh)*n); //Vidilenie pamati pod massiv vseh veshey
 teknabor=(vesh*)malloc(sizeof(vesh)*n); //Vidilenie pamati pod massiv tekushego nabora
 for (int i=0;i<n;i++)
 {
  for (int j=0;j<n;j++)
  {
   if (i!=j) {matrix[i*n+j]=1;} // iz kazhdoy vershini est put vo vse ostalnie...
   else
   {
    matrix[i*n+j]=0;// krome samoy seba
   }
  }
 }
 for (i=0;i<n;i++)
 {
  cout<<"Vvedite ves i poleznost "<<i+1<<"-oy veshi\n";// vivodim priglashenie
  cin>>nabor[i].ves>>nabor[i].poleznost; // schitivaem ves i poleznost
 }
 cout<<"Vvedite max ves rukzaka\n";
 cin>>maxves;
 for (i=0;i<n;i++) // idem po vsem vershinam...
 {
  for (int j=0;j<n;j++) // idem po vsem svazam vershini...
  {
   if (matrix[i*n+j]!=0&&nabor[i].ves<=maxves) // Esli est svaz i ves < max vesa...
   {
    teknabor[0]=nabor[i];// dobovlaem vesh k tekushemy nabory
    matrix[i*n+i]=-1; // pomechem na deagonali vershinu kak poseshenuu
    obhod(j,nabor[i].ves,nabor[i].poleznost,1); //nachinaem rekursivno idti po matrixy (peredaem nomer vershini po kotoroy poidem dalshe, ves, poleznost i glubinu rekursii)
    matrix[i*n+i]=0; // snimaem metky poseshenia
   }
  }
 }
 cout<<"Optimalniy nabor:\n"; // Soobshaem, chto sleduushiy nizhe put - minimalniy
 for (i=0;i<dlina;i++)// vivodim kol-vo veshey v opt nabore
 {
  cout<<"["<<optnabor[i].ves<<","<<optnabor[i].poleznost<<"]; "; // Vivodim minimalniy put
 }
 getch();// Zaderzhka pered vihodom
 return 0; // Vihod iz programmi
}