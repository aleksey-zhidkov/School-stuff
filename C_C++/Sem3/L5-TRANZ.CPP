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

 public:// Obshie metodi class
  graph(int s);// Konstruktor graha
  void destructor() {delete[] graph;};// Destruktor grapha
   // Metodi raboti s matricey smezhnosti

  void setSize(int s) {size=s;};// Ustanovka razmera grapha
  int getSize() {return size;};// Poluchenie razmera grapha
  void setGraph();// Zadanie grapha
  void deleteLink(point p) {graph[p.y_i*size+p.x_j]=0;};// Udalenie svazi
  void addLink(point p) {graph[p.y_i*size+p.x_j]=p.s;};// Dobavlenie svazi
  void addLink(int i,int j, int s) {graph[i*size+j]=s;};// Dobavlenie svazi
  int getLink(int y, int x) {return graph[y*size+x];};// Poluchit svaz
  void print();// Vivodm matrici smezhnosti na ekran
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
 for (int j=0;j<size;j++)
 {
  for (int i=0;i<size;i++)
  {
   if (g.getLink(i,j)==1)
   {
    for (int k=0;k<size;k++)
    {
     if (g.getLink(j,k)==1){g.addLink(i,k,-1);};
    }
   }
  }
 }
 for (int i=0;i<size;i++)// Obnovlenie ishodnoy matrici
 {
  for (int j=0;j<size;j++)
  {
   int l=g.getLink(i,j);// Polushenie znachenia i,j-togo elta matrici
   if (l<0) {g.addLink(i,j,-l);}// Esli on menshe 0, znachit vhodit v karkas -> vozvrahsiaem ishodnoe znachenie
  }
 }
 cout<<"Finish matrix:\n";// Kosmetika
 g.print();// Vivod novoy matrici smezhnosti
 getch();// Zaderzhka pered zavershieniem programmi
 g.destructor();// Vizov destrukrora classa
 return 0;// Vihod iz programmi
}