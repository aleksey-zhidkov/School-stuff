#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>
#include <conio.h>
#include <stdio.h>

#define n 9
#define true 1
#define false 0

int mwr[n][n]; //matrica v kotoroy stroki - mizhchiny, stolbci - zhenshini,
	       //a i-j-iy elt - zhenshina s j-tim rangom y i-togo muzhchini
int wmr[n][n]; // tozhe, no stroki - zhenshini
int rwm[n][n]; // matrica gde i-j-iy elt - rang i-oy zhenshini dla j-togo muzhchini
int rmw[n][n];
int single[n]; // massiv v kotorom hranitsa sostoianie zhenshini (zmuzhem/net)
int x[n];      // massiv v kotorom i-y elt - nomer zheni dla j-ogo muzhini
int y[n];      // to zhe dla zhenshin

void print()   // vivod braka na ekran
{
 int m,rm,rw;
 rm=0;         // sumarnaia udacha muzhin (summa zhelannosti zhen) !! Ne znau koroche !!
 rw=0;         // to zhe dla zhenshin
 for (m=1;m<n;m++)
 {
  cout<<x[m]<<" "; // vivod braka
  rm=rmw[m][x[m]]+rm; // podschet summi...
  rw=rwm[x[m]][m]+rw;
 }
 cout<<rm<<" "<<rw<<"\n"; // vivod summ i perevod na novuu stroky
}

int stable(int m,int w, int r) // proverka stabilnosti braka
{
 int pm,pw,i,lim,s;
 s=true;  // otvet na vopros o stabil nosti braka
 i=1;
 while(i<r && s==true) // pereberaem zhenshin chey rang bolshe predpolagaemoy zheni
 {
  pw=wmr[m][i]; // poluchem nomer zhenshini
  i++;
  if (single[pw]==false) // esli zhenshina zamuzhem
  {
   s = (rwm[pw][m]>rwm[pw][y[pw]]); // s prisvaem rezultat sravneniarang tekushego
				    // muzhini dla tekushey zhenshini s ee muzhem
				    // 1 esli tekushii muzhik lische i 0 v protivnom sluchae
  }
 }
 i=1;
 lim=rwm[w][m]; // predel - rang tekushego muzhchini dla tekushey zhensjini
 while(i<lim&&s==true)
 {
  pm=mwr[w][i]; // muzhchina dla w-ou zhenshini s i-tim rangom
  i++;
  if (pm<m) // esli muzhchina imeet rang bolshiy chem u tekushego....
  {
   s = (rmw[pm][w]>rmw[pm][x[pm]]); // prisvaevaem s rezultat sravnenia
				    // ranga muzhnini u tekushey zhenishini
				    // s rangom ee tekushego muzha
  }
 }
 return s;
}

void btry(int m)
{
 int w,r;
 for (r=1;r<n;r++)
 {
  w=wmr[m][r]; // nomer zhenshini s tekushim rangom dla tekushego muzhini
  if (single[w]==1 && stable(m,w,r)==1) // esli zhenshina odinoka i brak s ney stabilen...
  {
   x[m]=w; // zhenim muzhchinu na zhenshine
   y[w]=m; // vidaem zhenshinu z muzhchinu
   single[w]=false; // pomechem zhenshinu kak zamuzhuu
   if (m<n-1) // esli eshe imeutsa holostaki
   {
    btry(m+1); // ishem im paru
   }
   else {print();}; // inache vivodim m-vo brakov
   single[w]=true; // snimaem okovi s bednazhki
  }
 }
}

int main()
{
 clrscr();
 ifstream mfin("mans.opb");// Otkrit potok vvoda
 if(!mfin)// Esli potok ne otkrit...
 {
  cout<<"Impossoble to open file!";// Vivesti soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  exit(0);// Vihod iz programmi
 }
 char* str; // stroka rangov
 for (int m=1;m<n;m++)
 {
  mfin>>str; // schitivaem stroky iz fila
  for (int r=1;r<n;r++)
  {
   wmr[m][r]=str[r-1]-'0'; // pomeshaem nomer zhenshini sr-tim rangom dla m-togo muzhchini
   rmw[m][wmr[m][r]]=r; // pomeshaem rang schitannogo nomera zhenshini dla m-togo muzhchini
  }
 }
 mfin.close();// Zakritie vhodnogo potoka
 ifstream wfin("womans.opb");// Otkrit potok vvoda
 if(!wfin)// Esli potok ne otkrit...
 {
  cout<<"Impossoble to open file!";// Vivesti soobshenie
  getch();// Zaderzhka pered vihodom iz programmi
  exit(0);// Vihod iz programmi
 }
 for (int w=1;w<n;w++)
 {
  single[w]=1; // Svobody negram!!! (v smisle zhenshinam) (ne obizhaisa :-))
  wfin>>str;
  for (int r=1;r<n;r++)
  {
   mwr[w][r]=str[r-1]-'0'; // schitivaem nomer muzhchini s r-tim rangom dla w-toy zhenshini
   rwm[w][mwr[w][r]]=r; // ustanavlivaem rang dla nomera schitannogo muzhchini y w-toy zhenshini
  }
 }
 wfin.close();// Zakritie vhodnogo potoka
 btry(1);
 getch();
 return 0;
}