#include <iostream.h>
#include <fstream.h>
#include <process.h>
#include <string.h>
#include <stdio.h>
#include <conio.h>
#include <ctype.h>


typedef struct point// Struktura hranashia v sebe koordinati tochki
{
 int x;//Nomer stolbca (j)
 int y;// Nomer striki (j)
};

class matrix// Class predstavliaushiy matricy simvolov
{
 int size;// Razmer massiva
 char* matrix;// Massiv simvolov

 int wsize;// Razmer iskomogo slova
 char* word;// Iskomoe slovo
 point* points;// Massiv hroniashii koordinati nidennih bukv slova
 char a;// Peremenaaia hroniashia otvet na vopros o vazhnosti registra

 public:            //Raboche metodi class
 matrix(char*path); // Konstruktor
 void destruktor(); // Destruktor
				       //Metodi raboti s matricey simvolov
 void print();// Raspechatat matricy
 int getSize() {return size;};// Poluchit razmer matrici
 char getChar(int i, int j) {return matrix[i*size+j];};// Poluchit simvol iz [i,j]-oi vershini
 void setChar(int i,int j,char c) {matrix[i*size+j]=c;}; // Zadat [i,j]-iy simvol
 int search(int i,int j,int deph);// Glavniy metod realizuushiy poisk slova
					      // Metodi raboti s massivom koordinat
 void setPoint(point p,int i) {points[i]=p;}; // Zadat tochky
 point getPoint(int i) {return points[i];}; // Poluchit tochky
					  // Metodi raboti s iskomim slovom
 char getLetter(int i) {return word[i];}; // Poluchit i-yy bukvu simvola
 int getWordSize() {return wsize;}; // Poluchit dlinnu iskomogo slova
};

matrix::matrix(char*path)// Konstruktor classa
{
 ifstream fin(path);// Otkrit potok vvoda
 if(!fin)// Esli potok ne otkrit...
 {
  cout<<"Impossoble to open file!";// Vivesti soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  exit(0);// Vihod iz programmi
 }
 cout<<"Is register impotant? (y/n)\n";// Vivodim vopros o vazhnosti registra
 cin>>a;// Schitivanie otveta
 a=tolower(a);// Perevod otveta v nizhniy registr
 cout<<"Please, enter the word which neded to search\n";// Prosba vvesti iskomoe slovo
 cin>>word;// Vvod iskomogo slova
 wsize=strlen(word);// Poluchenie razmera iskomogo slova
 if (a=='n')// Esli registr nevazhen...
 {
  for (int i=0;i<wsize;i++)
  {
   word[i]=tolower(word[i]);// Perevodim simvoli iskomogo slova v nizhniy registr
  }
 }
 points= new point[wsize];// Sozdanie massiva koordinat
 char*str;// Vremennia peremenaia, v kotoroy budut hranitsa stroki, schitannie iz faila
 fin>>str;// Schitat stroky iz potoka v peremennuu
 size=strlen(str);// Opredelit razmer matrici
 matrix = new char[size*size];// Sozdanie matrici simvolov
 int i=0;
 for (int j=0;j<size;j++)
 {
  if (a=='n')
  {
   matrix[i*size+j]=tolower(str[j]);// Posimvolnoe kopirovanie iz stroki v matricy, bez ucheta registra
  }
  else
  {
   matrix[i*size+j]=str[j];// Posimvolnoe kopirovanie iz stroki v matricy, s uchetom registra
  }
 }
 for (i=1;i<size;i++)// Zapolnenie matrici
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
   if (a=='n')// Esli registr ne vazhen...
   {
    matrix[i*size+j]=tolower(str[j]);// Posimvolnoe kopirovanie iz stroki v matricy, bez ucheta registra
   }
   else// Inache
   {
    matrix[i*size+j]=str[j];// Posimvolnoe kopirovanie iz stroki v matricy, s uchetom registra
   }
  }
 }
 fin.close();// Zakritie vhodnogo potoka
};

void matrix::destruktor()// Destruktor classa
{                 // Visvobozhdenie pamati iz pod...
 delete[] points; // Massiva koordinat
 delete[] matrix; // Matrici simvolov
}

void matrix::print()// Vivod matrici na ekran
{
 for (int i=0;i<size;i++)
 {
  for (int j=0;j<size;j++)
  {
   cout<<matrix[i*size+j];// Posimvolniy vivod
  }
  cout<<"\n";// Perevod koretki na novuu stroky
 }
}

int matrix::search(int i,int j, int deph)// Glavniy metod realizuushiy poisk slova
{
 int ret=0;// Peremennaia hroniashaia sostoianie poiska: 0 - slovo ne naidenno, 1 - naidenno
 if (deph==wsize-1&&matrix[(i-1)*size+j]==word[deph]&&ret==0)
 {// Esli ishem poslednuu bykvy, i okolo tekyushey vershini est nuzhnaa, to slovo naidenno...
  ret=1;// Ustanavlivaem flag nahozhdenia
  points[deph].x=j;// Dobovliaem koordinati
  points[deph].y=i-1;
 };
 if (deph==wsize-1&&matrix[(i+1)*size+j]==word[deph]&&ret==0)
 {
  ret=1;
  points[deph].x=j;
  points[deph].y=i+1;
 };
 if (deph==wsize-1&&matrix[(i)*size+j-1]==word[deph]&&ret==0)
 {
  ret=1;
  points[deph].x=j-1;
  points[deph].y=i;
 };
 if (deph==wsize-1&&matrix[(i)*size+j+1]==word[deph]&&ret==0)
 {
  ret=1;
  points[deph].x=j+1;
  points[deph].y=i;
 };
 if (deph==size-1&&ret==0) {ret=0;};// Esli vokrug vershini net nuzhnih bukv, znachit poisk ne udachen
 if (matrix[i*size+(j+1)]==word[deph]&&ret==0)
 {// Esli ishem ne poslednuu bukvy, i vokrug imeetsa sleduushaia iskomaia...
  matrix[i*size+j+1]=matrix[i*size+j+1]-100;// Pomechiaem tekyshyy kak poseshenyy
  ret = search(i,j+1,deph+1);// Rekyrsivno vizivaem search dla naidennoy vershini
  points[deph].x=j+1;// Dobovlaem koordinati naidennoy vershini
  points[deph].y=i;
  matrix[i*size+j+1]=matrix[i*size+j+1]+100;// Snimaem flag poseshenia
 }
 if (matrix[i*size+(j-1)]==word[deph]&&ret==0)
 {
  matrix[(i)*size+j-1]=matrix[i*size+j-1]+100;
  ret = search(i,j-1,deph+1);
  points[deph].x=j-1;
  points[deph].y=i;
  matrix[(i)*size+j-1]=matrix[i*size+j-1]-100;
 }
 if (matrix[(i+1)*size+j]==word[deph]&&ret==0)
 {
  matrix[(i+1)*size+j]=matrix[(i+1)*size+j]-100;
  ret =  search(i+1,j,deph+1);
  points[deph].x=j;
  points[deph].y=i+1;
  matrix[(i+1)*size+j]=matrix[(i+1)*size+j]+100;
 }
 if (matrix[(i-1)*size+j]==word[deph]&&ret==0)
 {
  matrix[(i-1)*size+j]=matrix[(i+1)*size+j]-100;
  ret = search(i-1,j,deph+1);
  points[deph].x=j;
  points[deph].y=i-1;
  matrix[(i-1)*size+j]=matrix[(i+1)*size+j]+100;
 }
 return ret;// Vozvrashaem rezultat poiska
}

int main()// Glavnaia funkcia
{
 clrscr();// Ochistka ekrana
 char*path;// Peremennia s putom k faily
 cout<<"Please, enter path to the file with matrix\n";// Prosba vvesti put k failu
 cin>>path;// Schitivanie putia k faily
 matrix m(path);// Sozdanie ekzempliara classa matrix
 cout<<"\nThe matrix: \n";// Kosmetika
 m.print();// Vivod poluchennoy matrici
 int i=0;int j=0;// Nomer stroki i stolbca
 int isFind=0;// Peremennia v kotoroy hronitsa sostoianie poiska: 0 - slovo ne naideno, 1 - naidenno
 while(i<m.getSize()&&isFind==0)// Poka nomer stroki menshe razmera matrici i slovo ne naidenno...
 {
  j=0;// Obnulenie nomera stolbca
  while(j<m.getSize()&&isFind==0)// Poka nomer strlbca menshe razmera matrici i slovo ne naidenno...
  {
   if (m.getChar(i,j)==m.getLetter(0))// Esli [i,j]-iy simvol matrici raven 1 simvoly slova...
   {
    point p;// Sozdanie peremennoy hroniashey koordinati
    p.x=j;// Zadanie koordinat
    p.y=i;
    m.setPoint(p,0);// Dobovlenie koordint k massivy
    m.setChar(i,j,m.getChar(i,j)-100);// Zadanie poseshenia vershini
    isFind = m.search(i,j,1);// Poisk nachinaia s tekyshey vershini
    m.setChar(i,j,m.getChar(i,j)+100);// Snimaem poseshenie vershini
   }
   j++;// Uvelechenie nomera stolbca
  }
  i++;// Uvelechenie nomera stroki
 }
 if (isFind==0) {cout<<"\nThere are no the word";}// Esli slovo ne naidenno to vivodim soobshenie
 else// Inache...
 {
  cout<<"\nPath:\n";// Kosmetika
  for (i=0;i<m.getWordSize();i++)
  {
   point p = m.getPoint(i);// Poluchaem i-tyy koordinaty
   cout<<m.getLetter(i)<<"=["<<p.x+1<<"."<<p.y+1<<"] ";// Vivodim v formatirovannom vide (a=[0.0] )
  }
 }
 getch();// Zaderzhka pered zaversheniem programmi
 m.destruktor();// Visvobozhdenie pamati
 return 0;// Vozrvrat flaga normalnogo zavershenia programmi
}