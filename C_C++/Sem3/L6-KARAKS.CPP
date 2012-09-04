#include <iostream.h>
#include <stdlib.h>
#include <stdio.h>
#include <conio.h>

typedef struct point// Struktura hraniashia infu o tochke
{
 int x_j;// koord po x/Nomer stolbca matrici
 int y_i;// koord po y/Nomer stroki matrici
 int s;// stoimost svazi/Znachenie elta matrici
};

class graph// class predstavlaushiy matricu
{
  int size;// razmer grapha
  int* graph;// matrica smezhnosti
  int* set;// mnozhestvo dereviev (alg Kruskalla)

 public:// Obshie metodi class
  graph(int s);// Konstruktor graha
  void destructor() {delete[] graph; delete[] set;};// Destruktor grapha
   // Metodi raboti s matricey smezhnosti

  void setSize(int s) {size=s;};// Ustanovka razmera grapha
  int getSize() {return size;};// Poluchenie razmera grapha
  void setGraph();// Zadanie grapha
  point findWeakLink();// Poisk naibolee deshovoy svazi
  void deleteLink(point p) {graph[p.y_i*size+p.x_j]=0;};// Udalenie svazi
  void addLink(point p) {graph[p.y_i*size+p.x_j]=p.s;};// Dobavlenie svazi
  void addLink(int i,int j, int s) {graph[i*size+j]=s;};// Dobavlenie svazi
  int getLink(int y, int x) {return graph[y*size+x];};// Poluchit svaz
  void print();// Vivodm matrici smezhnosti na ekran
   // Metodi rabosti s mnozhestvom derevev
  int getElementOfSet(int y, int x) {return set[y*size+x];};// Poluchenie elementa mnozhestva
  void setElementOfSet(int y,int x, int e) {set[y*size+x]=e;};// Zadanie elementa mnozhestva
  int findStringInSet(int e);// Poisk stroki mnozhestva v kotoroy soderzhitsa ukazanniy element
  int isFullSet();// Mnozhestvo yavliaetsa polnim, kogda vse elementi v 1 stroke
  void printset();// Vivod mnozhestva na ekran
}

graph::graph(int s)// Konstruktor grapha
{
 setSize(s);// Ustanovit razmer grapha
 graph=new int[size*size];// Vidilenie pamati pod matricu smezhnosti
 for (int i=0;i<size;i++)
 {
  for (int j=0;j<size;j++)
  {
   graph[i*size+j]=0;// Inicializacia matrici smezhnosti
  }
 }
 set= new int[size*size];// Vidilenie pamati pod mnozhestvo dereviev
 for (i=0;i<size;i++)
 {
  for (int j=0;j<size;j++)
  {
   set[i*size+j]=0;//inicializatia mnozhestva
   if (i==j) {set[i*size+j]=i+1;};// Snachala v kazdom dereve po 1 vershine
  }
 }
}

void graph::setGraph()// Zadaine grapha
{
 int i,j,c;// Peremennie hroniashe ishodnuu, celevuu vershini i stoimost svazi
 cout<<"Please, enter links\nLike <from> <to> <cost>\nTo finish type '0'\n";// Priglasitelnoe soobshenie
 int* vortex = new int[size];// Vidilenie pamati pod mnozhestvo vershin prinadlezhshih graphu
 for (i=0;i<size;i++)
 {
  vortex[i]=0;// Snachala ni odna vershina ne prenadlezhit mnozhestvu
 }
 while(1)//Beskonechniy cikl s vihodom v nutri
 {
  cin>>i;// Schitat i
  if (i==0)// Esli i=0
  {
   int isOk=1;// Peremennaia hronashia vse li vershini prinadlezhat graphu
   for (int k=0;k<size;k++)
   {
    if (vortex[k]==0) {isOk=0;};// Ustanovka flaga, chto ne vse vershini prenadlezhat graphu
   }
   if (isOk==1) {break;}// Esli vse vershini prenadlezhat graphu, to vihodim iz while'a
   else// Inache..
   {
    cout<<"All vortexes of groph must be linked!\n";// Vivodim preduprezhdeine
    continue;// I perehodim k sleduushey iteracii while'a
   }
  };
  cin>>j>>c;// Schitivaem j i c
  if (i>size||i<1||j>size||j<1)// Esli 1 iz vershi ne prenadlezhit otrezky [1,size]..
  {
   cout<<"Incorrect arguments!\nVortex must be grather than 0 and less than "<<size<<"\n";// Vivodim preduprezhdenie
   continue;// I perehodim k sleduushey iteracii
  }
  vortex[i-1]=1;vortex[j-1]=1;// Ustanavlivaem prinadlezhnost k graphy soedennenih vershin
  graph[(i-1)*size+j-1]=c;// Dobovliaem svaz v matricu smezhnosti
 }
 delete[] vortex;// Osvobozhdaem pamat iz pod mnozhestva prindlezhnosti vershin
}

point graph::findWeakLink()// Poisk naibolee deshovoy svazi
{
 point weaklink;// peremennaia hroniashia tochky s samoy deshovoy svazu
 weaklink.x_j=-1;// Inicializatia
 weaklink.y_i=-1;// Inicializatia
 weaklink.s=32767;
 int i,j;// Tekyshie koordinati i minimum
 for(i=size-1;i>=0;i--)
 {
  for(j=size-1;j>=0;j--)
  {
   if (graph[i*size+j]<weaklink.s&&graph[i*size+j]>0)// Esli tekushaa tochka menshe minimalnoy
   {
    weaklink.x_j=j;// I ustanavlivaem minimalnuu tochky na tekyshuu
    weaklink.y_i=i;
    weaklink.s=graph[i*size+j];
   }
  }
 }
 return weaklink;// Vozvrashenie tekushey tochki
}

int graph::findStringInSet(int e)// Nahozhdenie stroki mstva, v kotoroy hranitsa peredavaemiy element
{
 int strnum=0;// Inicializatia stroki
 for (int i=0;i<size;i++)
 {
  if (set[i*size+e]!=0) {strnum=i;};// Zadanie stroki v kotoroy nahoditsa element
 }
 return strnum;// Vozvrashenie stroki
}

int graph::isFullSet()// Proverka mva na polnoty
{
 int isFullS;// Otvet na proverky 1 - polnoe, 0 - net
 for (int i=0;i<size;i++)
 {
  if (set[i*size+0]==0) {continue;}// Esli 1 elt = 0, to mvo tochno ne polnoe -> na sled iteraciy
  else {isFullS=1;};// Inache predpologaem, chto mvo polnoe
  for (int j=0;j<size;j++)
  {
   if (set[i*size+j]==0) {isFullS=0;break;};// Esli elt oprovergaet predpolozhenie - mvo ne polnoe
  }
  if (isFullS==1) {break;};// Esli v 1 iz strok net eltov oprovergaushih predpolozhenie o polnote tekyshega mva, to dalshe mozhno ne iskat
 }
 return isFullS;// Vozvrashenie otveta
}

void graph::printset()// Vivod mva na ekran
{
 for (int i=0;i<size;i++) {cout<<"--";};// Kosmetika
 cout<<"\n";// Kosmetika
  for (i=0;i<size;i++)
  {
   for (int j=0;j<size;j++)
   {
    cout<<set[i*size+j];
    cout<<" ";
   }
   cout<<"\n";
  }
 for (i=0;i<size;i++) {cout<<"--";};
 cout<<"\n";
}

void graph::print()// Vivod matrici smezhnosti na ekran
{
 for (int i=0;i<size;i++) {cout<<"--";};
 cout<<"\n";
  for (i=0;i<size;i++)
  {
   for (int j=0;j<size;j++)
   {
    cout<<graph[i*size+j];
    cout<<" ";
   }
   cout<<"\n";
  }
 for (i=0;i<size;i++) {cout<<"--";};
 cout<<"\n";
}

main()// Glavnaia funkcia
{
 clrscr();// Ochistka ekrana
 cout<<"Enter the graph size\n";// Priglashenie vvesti razmer grapha
 int size;
 cin>>size;// Schitivanie razmera
 if (size<2)// Esli v graphe menshe 1 vershini...
 {
  cout<<"Graph must have at least 2 vortexes.\nTo exit press any key\n";// Soobshenie o nekorrectnosti vvoda
  getch();// Zaderzhka pered zaversheniem programmi
  return 0;// Vihod iz programmi
 }
 graph g(size);// Sozdanie ekzemplara classa graph, s vvedennim razmerom
 g.setGraph();// Zadanie grapha
 cout<<"Start matrix:\n";// Kosmetika
 g.print();// Vivod ishodnoy matrici smezhnosti
 while(g.isFullSet()==0)// Do teh por, poka ne vse elti prinadlezhat odnomy iz dereviev
 {
  point p = g.findWeakLink();// Nahozhdenie min svazi
  if (g.getElementOfSet(p.y_i,p.y_i)!=0&&g.getElementOfSet(p.x_j,p.x_j)!=0)// Esli vershini mezhdu kotorimi min svaz ne prinadlezhat drugim mvam...
  {
   for (int j=0;j<g.getSize();j++)// Obedenenie mstv s ishodnoy i celevoy vershinami
   {
    if (g.getElementOfSet(p.x_j,j)==0)// Esli element mva s celevoy vershinoy pust...
    {
     g.setElementOfSet(p.x_j,j,g.getElementOfSet(p.y_i,j));// perenosim vse vershini iz mnozhestva s ishodnoy vershinoy v mvo s celevoy
     g.setElementOfSet(p.y_i,j,0);// Mvo s ishodnoy vershinoy obnulaem
    }
   }
   p.s=-p.s;    //\\
   g.addLink(p);//// pometka svazi, ee stoimostu, no s minusom
  }
  else// inache
  {
   int fromstr,tostr;// peremennie hroniashe nomera strok mva, v kotorih hraniatsa ishodnaia i celevaia vershini
   fromstr=g.findStringInSet(p.y_i);// Poisk stroki s ishodnoy vershini
   tostr=g.findStringInSet(p.x_j);// Poisk stroki s celevoi vershinoy
   if (fromstr==tostr)// Esli vershini v 1 mve...
   {
    p.s=0;        //\\
    g.addLink(p); ////udalenie svazi iz matrici smezhnosti
   }
   else// Inache
   {
    for (int j=0;j<g.getSize();j++)// Obedenenie mstv s ishodnoy i celevoy vershinami
    {
     if (g.getElementOfSet(fromstr,j)==0)// Esli j-ii elt ishodnogo mva pust...
     {
      g.setElementOfSet(fromstr,j,g.getElementOfSet(tostr,j));// to perenosim v nego h-ii elt celevogo mva
     }
     g.setElementOfSet(tostr,j,0);// obnulaem celevoe mvo
    }
    p.s=-p.s;    //\\
    g.addLink(p);//// Pomechaem svaz ee stoimostu s minusom
   }
  }
 }
 for (int i=0;i<size;i++)// Obnovlenie ishodnoy matrici
 {
  for (int j=0;j<size;j++)
  {
   int l=g.getLink(i,j);// Polushenie znachenia i,j-togo elta matrici
   if (l<0) {g.addLink(i,j,-l);}// Esli on menshe 0, znachit vhodit v karkas -> vozvrahsiaem ishodnoe znachenie
   else {g.addLink(i,j,0);};// Inache udalaem
  }
 }
 cout<<"Finish matrix:\n";// Kosmetika
 g.print();// Vivod novoy matrici smezhnosti
 getch();// Zaderzhka pered zavershieniem programmi
 g.destructor();// Vizov destrukrora classa
 return 0;// Vihod iz programmi
}