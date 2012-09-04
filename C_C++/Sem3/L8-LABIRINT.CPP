#include <iostream.h>
#include <fstream.h>
#include <process.h>
#include <string.h>
#include <stdio.h>
#include <conio.h>
#include <ctype.h>
#include <dos.h>


typedef struct point// Struktura hranashia v sebe koordinati tochki
{
 int x_j;//Nomer stolbca (j)
 int y_i;// Nomer striki (i)
};

class query// Class ocheredi
{
 point* queryAr;// Massiv hraniashiy elti ocheredi
 int size;// Kol-vo eltov v ocheredi

 public:
 query(int s) {queryAr = new point[s+1];size=0;};// Konstruktor ocheredi
 void destruktor() {delete[] queryAr;};// Destruktor ocheredi
 point getElt();// Posmotret elt ocheredi
 void addElt(point p);// Dobavit elt ocheredi
 point deleteElt();// Iziat elt iz ocheredi
};

point query::getElt()// Posmotret elt ocheredi
{
 if (size>0) {return queryAr[0];}// Esli elti est, vozvrashaem 1
 else// Inache...
 {
  point p;// Vozvrashiaem "pustoy" elt
  p.x_j=-1;
  p.y_i=-1;
  return p;
 }
}

void query::addElt(point p)// Dobavit elt v ochered
{
 queryAr[size]=p;// Dobavlenie elta v ochered
 size++;// Uvelechenie razmera ocheredi
}

point query::deleteElt()// Udalenie elta iz ocheredi
{
 point p;
 if (size<0) {p.y_i=-1;p.x_j=-1;}// Esli v ocheredi net eltov, vozvrashiaem "pustoy elt"
 else// Inache...
 {
  p=queryAr[0];// Vozvrashiaem 1 elt
  for (int i=0;i<size-1;i++)
  {
   queryAr[i]=queryAr[i+1];// Smeshiaem ochered na 1 vpered
  }
  size--;// Umenshaem razmer
 }
 return p;
}

class matrix// Class predstavliaushiy labirint
{
 int size;// Razmer massiva
 int* matrix;// labirint

 public:            //Rabochie metodi classa
 point start,finish;// Tochki starta i finisha
 matrix(char*path); // Konstruktor
 void destruktor(); // Destruktor
				       //Metodi raboti s labirintom
 int getLink(point p) {return matrix[p.y_i*size+p.x_j];};// Poluchit znachenie po tochke
 int getLink(int i, int j) {return matrix[i*size+j];};// Poluchit znachenie po koordintam
 void addLink(point p, int e) {matrix[p.y_i*size+p.x_j]=e;};// Dobavit znachenie po tochke
 void addLink(int i, int j, int e) {matrix[i*size+j]=e;};// Dobavit znachenie po koordinatam
 void print();// Raspechatat matricy
 int getSize() {return size;};// Poluchit razmer labirinta
 point minpoint(point p);// Poluchit min tochky vokrug dannoy
 int isInMatrix(point p);// Proverka na prenadlezhnost tochki matrice
};

matrix::matrix(char*path)// Konstruktor classa
{
 start.x_j=-1;// Start i finish ne opredeleni
 finish.x_j=-1;
 ifstream fin(path);// Otkrit potok vvoda
 if(!fin)// Esli potok ne otkrit...
 {
  cout<<"Impossoble to open file!";// Vivesti soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  exit(0);// Vihod iz programmi
 }
 char*str;// Vremennia peremenaia, v kotoroy budut hranitsa stroki, schitannie iz faila
 fin>>str;// Schitat stroky iz potoka v peremennuu
 size=strlen(str);// Opredelit razmer matrici
 matrix = new int[size*size];// vidilenie pamati pod labirint
 int i=0;
 for (int j=0;j<size;j++)
 {
  if (str[j]-48>4)// Proverka korrektnosti eltov matrici
  {
   cout<<"Incorrect matrix!";// Vivodim soobshenie
   getch();// Zaderzhka pered vihodom iz programmi
   destruktor();// Osvobozhdenie pamati
   exit(0);// Vihod iz programmi
  };
  if (str[j]-48==4)// Nashli start
  {
   start.x_j=j;start.y_i=i;
   addLink(start,-1);// Pometili start, kak perviy shag puta
   continue;
  }
  if (str[j]-48==4)// Nashli finish
  {
   finish.x_j=j;finish.y_i=i;
   addLink(finish,-(size*size+1));// Pometili finish
   continue;
  }
  matrix[i*size+j]=str[j]-48;// Posimvolnoe kopirovanie iz stroki v labirint
 }
 for (i=1;i<size;i++)// Zapolnenie labirinta
 {
  fin>>str;// Schitivanie stroki iz faila v peremenuu
  if(strlen(str)!=size)// Esli dlina stroki otlicha ot razmera matrici...
  {
   cout<<"Incorrect matrix!";// Vivodim soobshenie
   getch();// Zaderzhka pered vihodom iz programmi
   destruktor();// Osvobozhdenie pamati
   exit(0);// Vihod iz programmi
  }
  if(i<size-1&&fin.eof())// Esli dlina stroki otlicha ot razmera matrici...
  {
   cout<<"Incorrect matrix!";// Vivodim soobshenie
   getch();// Zaderzhka pered vihodom iz programmi
   destruktor();// Ovobozhdenie pamati
   exit(0);// Vihod iz programmi
  }
  for (int j=0;j<size;j++)
  {
   if (str[j]-48==4)// Nashli start
   {
    start.x_j=j;start.y_i=i;
    addLink(start,-1);
    continue;
   }
   if (str[j]-48==3)// Nashli finish
   {
    finish.x_j=j;finish.y_i=i;
    addLink(finish,-(size*size+1));
    continue;
   }
   matrix[i*size+j]=str[j]-48;// Posimvolnoe kopirovanie iz stroki v labirint
  }
 }
 if (start.x_j==-1)// Esli start tak i ne nashli...
 {
  cout<<"Incorrect matrix!"<<"\nMatrix mast have start (element 4)";// Vivodim soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  destruktor();// Osvobozhdenie pamati
  exit(0);// Vihod iz programmi
 }
 if (finish.x_j==-1)// Esli finish tak i ne nashli
 {
  cout<<"Incorrect matrix!"<<"\nMatrix mast have finish (element 3)";// Vivodim soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  destruktor();// Osvobozhdenie pamati
  exit(0);// Vihod iz programmi
 }
 fin.close();// Zakritie vhodnogo potoka
};

void matrix::destruktor()// Destruktor classa
{                 // Visvobozhdenie pamati iz pod...
 delete[] matrix; // labirinata
}

void matrix::print()// Vivod matrici na ekran
{
 clrscr();// ochista ekrana
 for (int i=0;i<size;i++)
 {
  for (int j=0;j<size;j++)
  {
   textcolor(0);// meniaem osnovnoy cvet
   if (matrix[i*size+j]<0)// esli put...
   {
    textcolor(1);// pishem sinim cvetom
   }
   textbackground(7);// meniaem cvet fona
   int isChang=0;// Meniali li mi matricu
   if (matrix[i*size+j]<0) {matrix[i*size+j]=-matrix[i*size+j];isChang=1;};// Esli elt<0 (put) vremenno menaem na +
   if (matrix[i*size+j]<10) {cprintf("%i  ",matrix[i*size+j]);}// Posimvolniy vivod 1-znachnih cifor
   else if (matrix[i*size+j]>99) {cprintf("%i",matrix[i*size+j]);}// Posimvolniy vivod 3-znachnih cifor
   else {cprintf("%i ",matrix[i*size+j]);}// Posimvolniy vivod 2-znachnih cifor
   if (isChang==1) {matrix[i*size+j]=-matrix[i*size+j];};// Esli elt menali na +, vozvrashiaem -
   textcolor(7);// Vosstanavlivaem serii cvet
   textbackground(0);// Vosstanavlivaem cvet fona
  }
  cout<<"\n";// Perevod koretki na novuu stroky
 }
 delay(300);// Zaderzhka
}

int matrix::isInMatrix(point p)// Proverka na prenadlezhnost tochki matrice
{
 int ans=1;// Predpologaem chto prenadlezhit
 if (p.x_j<0||p.x_j>=size) ans=0;// Proverka granic
 if (p.y_i<0||p.y_i>=size) ans=0;
 return ans;// Vozvrashenie rezultata
}

point matrix::minpoint(point p)
{
 int up,right,down,left;// Znachenia tochek
 point tu,tr,td,tl;// Koordinati tochek
 tu.y_i=p.y_i-1;// Ustanovka tochki
 tu.x_j=p.x_j;
 up=getLink(tu);// Poluchenie znachenia po tochke
 if (isInMatrix(tu)==0) {up=-size*size+5;};
 tr.y_i=p.y_i;
 tr.x_j=p.x_j+1;
 right=getLink(tr);
 if (isInMatrix(tr)==0) {right=-size*size+5;};
 td.y_i=p.y_i+1;
 td.x_j=p.x_j;
 down=getLink(td);
 if (isInMatrix(td)==0) {down=-size*size+5;};
 tl.y_i=p.y_i;
 tl.x_j=p.x_j-1;
 left=getLink(tl);
 if (isInMatrix(tl)==0) {left=-size*size+5;};
 if (up>=0) {up=-size*size+5;};// Iskluchenie + eltov
 if (right>=0) {right=-size*size+5;};
 if (down>=0) {down=-size*size+5;};
 if (left>=0) {left=-size*size+5;};
 if (up<0&&    up>=left&&    up>=down &&   up>=right) {p=tu;};// Esli elt <0 i blizhe vseh ostalnih k 0 - on min
 if (right<0&& right>left&& right>down&& right>up) {p=tr;};
 if (down<0&&  down>left&&  down>up&&    down>right) {p=td;};
 if (left<0&&  left>up&&    left>down&&  left>right) {p=tl;};
 return p;// Vozvrashiaem min
}

int main()// Glavnaia funkcia
{
 clrscr();// Ochistka ekrana
 char*path;// Peremennia s putom k faily
 cout<<"Please, enter path to the file with matrix\n";// Prosba vvesti put k failu
 cin>>path;// Schitivanie putia k faily
 matrix m(path);// Sozdanie ekzempliara classa matrix
 query q(m.getSize());// Sozdanie ocheredi
 q.addElt(m.start);// Zanisenie v ochered nachala labirinta
 cout<<"\nThe matrix: \n";// Kosmetika
 point curpoint;// Tekushaa tochka
 int step=2;// Tekushi shag
 int isFind=0;// Naiden li elt
 point* minpath = new point[m.getSize()*m.getSize()];// min put
 while (isFind==0&&q.getElt().x_j!=-1)// Poka elt ne naiden i v ocheredi est elti...
 {
  while (m.getLink(q.getElt())==-(step-1)&&isFind==0)// Poka v ocheredi elti kotorie dobavlenni poslednimi i ne dostignut finish...
  {
   curpoint=q.deleteElt();// Izat tochky
   point cp=curpoint;// Vremenaaia tochka
   if (cp.y_i-1>=0) {cp.y_i=cp.y_i-1;};// Smeshaem tochky
   if (m.getLink(cp)==-(m.getSize()*m.getSize()+1))// Esli finish...
   {
    m.addLink(cp,-step);// Dobavlaem vershinu
    isFind=1;// Ustanavlivaem flag nahozhdenia
    minpath[step]=cp;// Sohraniaem koordinati finisha
   }
   if (m.getLink(cp)==0)// Esli est put...
   {
    q.addElt(cp);// Pomeshiaem vershinu v ochered
    m.addLink(cp,-step);// Dobovliaem vershinu
   }
   cp=curpoint;
   if (cp.y_i+1<m.getSize()) {cp.y_i=cp.y_i+1;};
   if (m.getLink(cp)==-(m.getSize()*m.getSize()+1))
   {
    m.addLink(cp,-step);
    isFind=1;
    minpath[step]=cp;
   }
   if (m.getLink(cp)==0)
   {
    q.addElt(cp);
    m.addLink(cp,-step);
   }
   cp=curpoint;
   if (cp.x_j-1>=0) {cp.x_j=cp.x_j-1;};
   if (m.getLink(cp)==-(m.getSize()*m.getSize()+1))
   {
    m.addLink(cp,-step);
    isFind=1;
    minpath[step]=cp;
   }
   if (m.getLink(cp)==0)
   {
    q.addElt(cp);
    m.addLink(cp,-step);
   }
   cp=curpoint;
   if (cp.x_j+1<m.getSize()) {cp.x_j=cp.x_j+1;};
   if (m.getLink(cp)==-(m.getSize()*m.getSize()+1))
   {
    m.addLink(cp,-step);
    isFind=1;
    minpath[step]=cp;
   }
   if (m.getLink(cp)==0)
   {
    q.addElt(cp);
    m.addLink(cp,-step);
   }
  }
  step++;// Uvelichivaem shag
  m.print();// Vivodim matricu
 }
 delay(1000);
 step--;// Ubiraem lishiy shag
 for (int i=step-1;i>0;i--)
 {
  point p=m.minpoint(minpath[i+1]);
  minpath[i]=p;// Sohraniaem min put
 }
 for (i=0;i<m.getSize();i++)
 {
  for (int j=0;j<m.getSize();j++)
  {
   if (m.getLink(i,j)<0) {m.addLink(i,j,0);};// Udaliaem vse shagi
  }
 }
 for (int k=1;k<=step;k++)
 {
  m.addLink(minpath[k],-(k));// Dobovliaemshag iz min puta
  clrscr();// ochista ekrana
  for (i=0;i<m.getSize();i++)
  {
   for (int j=0;j<m.getSize();j++)
   {
    textcolor(0);// meniaem osnovnoy cvet
    if (m.getLink(i,j)<0)// esli put...
    {
     textcolor(4);// pishem krasnim cvetom
    }
    textbackground(7);// meniaem cvet fona
    int isChang=0;// Meniali li mi matricu
    if (m.getLink(i,j)<0) {m.addLink(i,j,-m.getLink(i,j));isChang=1;};// Esli elt<0 (put) vremenno menaem na +
    if (m.getLink(i,j)<10) {cprintf("%i  ",m.getLink(i,j));}// Posimvolniy vivod 1-znachnih cifor
    else if (m.getLink(i,j)>99) {cprintf("%i",m.getLink(i,j));}// Posimvolniy vivod 3-znachnih cifor
    else {cprintf("%i ",m.getLink(i,j));}// Posimvolniy vivod 2-znachnih cifor
    if (isChang==1) {m.addLink(i,j,-m.getLink(i,j));};// Esli elt menali na +, vozvrashiaem -
    textcolor(7);// Vosstanavlivaem serii cvet
    textbackground(0);// Vosstanavlivaem cvet fona
   }
   cout<<"\n";// Perevod koretki na novuu stroky
  }
  delay(400);// Zaderzhka
 }
 getch();// Zaderzhka pered zaversheniem programmi
 m.destruktor();// Visvobozhdenie pamati
 q.destruktor();// Visvobozhdenie pamati
 return 0;// Vozrvrat flaga normalnogo zavershenia programmi
}