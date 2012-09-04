#include <iostream.h>
#include <stdlib.h>
#include <stdio.h>
#include <conio.h>

int* graph; // peremennaia hroniashia matricu smezhnosti
int* minput; // peremennaia hroniashia minimalniy put
int* put; // rabochiy massiv s tekushim putem
int mincena; //cena minimalnogo puti
int size;   // peremennaia hronashia razmer grapha

void print()// Vivod matrici smezhnosti na ekran
{
  for (int i=0;i<size;i++) // bezhim po strokam...
  {
   for (int j=0;j<size;j++)// bezim po stolbcam...
   {
    cout<<graph[i*size+j]<<" "; // vivodim element i probel
   }
   cout<<"\n"; // perevodim kursor na novuu stroky
  }
}

void obhod(int i,int cena,int glubina)// Obhod grapha
// parameri funkcii: i - nomer vershini v kotoroy nahodimsa (i chi svazi budem proverat),
// cena - summarnia stoimost proidennogo puti,
// glubina - tekushaa glubina puti (nomer vershini po schetu v puti, nachinaa s 0)
{
 if (glubina==size-1)// esli glubina = size-1 (oboshli vse vershini)....
 {
  put[glubina]=i+1; // Pomeshaem tekushuu (poslednuu) vershinu v massiv put
  for (int k=0;k<size;k++)
  {
   cout<<put[k]<<" "; // vivodim put na ekran
  }
  cout<<"\n"; // perevodim kursor na novuu striky
  if (cena<mincena)  // esli stoimost tekushego puti menshe minimalnoy (tekushiy pyt samiy deshoviy, poka chto)...
  {
   mincena=cena; // v min cenu skladivaem tekushuu
   for (int k=0;k<size;k++)
   {
    minput[k]=put[k];  // i perenosim v massiv s min putom tekushiy put
   }
  }
  return; // vozvrashiaemsa na uroven vishe
 }
 else // inache...
 {
  for (int j=0;j<size;j++) // idem po vsem svazam vershini
  {
   if (graph[i*size+j]!=0&&graph[j*size+j]==0) // Esli est svaz i celevuu vershinu eshe ne poseshali...
   {
    put[glubina]=i+1;// dobovlaem vershiny k massivu puti
    graph[i*size+i]=-1; // pomechem na deagonali vershinu kak poseshenuu
    obhod(j,cena+graph[i*size+j],glubina+1);// opuskaem na uroven nizhe (delaem obhod uzhe po j, pribavlaem cenu puti i uvelichivaem glubinu)
    graph[i*size+i]=0; // snimaem metky poseshenia
   }
  }
 }
}

int main()
{
 clrscr();// Ochistka ekrana
 cout<<"Vvedite razmer grahpa\n";// Priglashenie vvesti razmer grapha
 cin>>size;// Schitivanie razmera
 mincena=32767; // min cenu pomeshaem max vozmozhnoe znachenie dla int
 graph=(int*)malloc(sizeof(int)*size*size);// Vidilenie pamati pod matricu smezhnosti
 minput=(int*)malloc(sizeof(int)*size); //Vidilenie pamati pod massiv min puti
 put=(int*)malloc(sizeof(int)*size); //Vidilenie pamati pod massiv puti
 for (int i=0;i<size;i++)
 {
  minput[i]=0; // obnulenie min puti
  put[i]=0; // obnulenie puti
  for (int j=0;j<size;j++)
  {
   graph[i*size+j]=0;// Inicializacia matrici smezhnosti
  }
 }
 int j,c;// Peremennie hroniashe celevuu vershiny i stoimost svazi
 cout<<"Vvedite svazi: <iz> <v> <stoimost>\n";// Vivodim priglashenie
 while(1)//Beskonechniy cikl s vihodom v nutri
 {
  cin>>i;// Schitat i
  if (i==0)// Esli i=0
  {
   break;// Vihodim iz while'a
  };
  cin>>j>>c;// Schitivaem j i c
  if (i>size||i<1||j>size||j<1)// Esli 1 iz vershi ne prenadlezhit otrezky [1,size-1]..
  {
   cout<<"Vershini dolzni bit bolshe nola i menshe "<<size+1;// Vivodim preduprezhdenie
   continue;// I perehodim k sleduushemy prohodu cikla (bez dobavlenia svazi)
  }
  graph[(i-1)*size+j-1]=c;// Dobovliaem svaz v matricu smezhnosti
  graph[(j-1)*size+i-1]=c;// Dobovliaem v matricu smezhnosti dobavliam obratnuu svaz (t.k. graf ne orientirovanny, t.k. po dorogam mozhno dvigatsa v obe storoni)
 }
 print(); // Vivodim matricu smezhnosti na ekran
 for (i=0;i<size;i++) // idem po vsem vershinam...
 {
  for (j=0;j<size;j++) // idem po vsem svazam vershini...
  {
   if (graph[i*size+j]!=0) // Esli est svaz...
   {
    put[0]=i+1;// dobovlaem vershini v nachalo
    graph[i*size+i]=-1; // pomechem na deagonali vershinu kak poseshenuu
    obhod(j,graph[i*size+j],1); //nachinaem rekursivno idti po graphy (peredaem nomer vershini po kotoroy poidem dalshe, stoimost puti i glubinu rekursii)
    graph[i*size+i]=0; // snimaem metky poseshenia
   }
  }
 }
 cout<<"Minimalniy put:\n"; // Soobshaem? chto sleduushiy nizhe put - minimalniy
 for (i=0;i<size;i++)
 {
  cout<<minput[i]<<" "; // Vivodim minimalniy put
 }
 getch();// Zaderzhka pered vihodom
 return 0; // Vihod iz programmi
}