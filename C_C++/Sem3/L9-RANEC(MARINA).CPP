#include <iostream.h>
#include <conio.h>
#include <alloc.h>

typedef struct thing // Struktura oredstavlaushaa vesh
{
 int weight; // ves veshi
 int poleznost; // poleznost veshi
 int inuse; // flag o tom zadeistvovanna li uzhe vesh v tekushem nabore
};

thing* have; // massiv hroniashiy tekyshiy nabor
thing* also; // massiv hroniashiy ves nabor (ispolzovannie veshi pomecheni flagom)
thing* maxnabor; // massiv hroniashi max nabor

int size; // kol-vo veshey
int maxweight; // max ves rukzaka
int maxnaborpolza; // poleznost max nabora
int cost; // ves tekushego nabora
int naborlength; // kol-vo veshey v max nabore

int poleznost(int s) // funkcia vozvrashaushaa poleznost tekushego nabora
{
 int res=0; // obsha poleznost
 for (int i=0;i<s;i++)
 {
  res+=have[i].poleznost; // pribavlenie k obshey poleznosti poleznosti tekyshey veshi
 }
 return res; // vozvrat poleznosti
}

void perebor(int deph,int cost) // funkcia osushestvlaushaa perebor
{
 for (int i=0;i<size;i++) //dla vseh veshey
 {
  if (also[i].weight+cost<=maxweight&&also[i].inuse==0) // esli vesh mozno zatalkat v rukzak i ona ne ispolzuetsa...
  {
   also[i].inuse=1; // pomechem vesh kak ispolzuemuu
   have[deph]=also[i]; // zatalkivaem v rukzak
   perebor(deph+1,cost+also[i].weight); // rekursivno pitaemsa vatalkat eshe chenit
   also[i].inuse=0; // snimaem metky ispolzovania
  }
  else // inache...
  {
   int polza=poleznost(deph); // opredelaem poleznost tekushego nabora
   if (polza>maxnaborpolza) // esli ona bolshe poleznosti max nabora...
   {
    naborlength=deph; // dliny max nabora priravnivaem k tekyshey glubine rekyrsii
    for (i=0;i<deph+1;i++)
    {
     maxnabor[i]=have[i]; // perenosim vse veshi iz tekyshego nabora v max nabor
    }
    maxnaborpolza=polza; // ystanavlivaem polzu max nabora ravnoy polze tekyshego nabora
   }
  }
 }
}

int main() // glavnaia funkcia
{
 maxnaborpolza=0; // poleznost max nabora
 naborlength=0; // dlina max nabora
 cout<<"Vvedite kol-vo veshey:\n"; // vivodim priglashenie
 cin>>size; // schitivaem kol-vo veshey
 have=(thing*)malloc(sizeof(thing)*size); // vidilaem pamat pod tekyshiy nabor
 also=(thing*)malloc(sizeof(thing)*size); // vidilaem pamat pod massiv vseh veshey
 maxnabor=(thing*)malloc(sizeof(thing)*size); // vidilaem pamat pod max nabor
 cout<<"Vvedite veshi <ves> <poleznost>:\n"; // vivodim preglashenie
 for (int i=0;i<size;i++)
 {
  int v,p; // peremennie hronashie ves i poleznost veshi
  cin>>v>>p; // schitivane vesa i poleznosti
  thing t; // peremenna hroniashaa tekushuu vesh
  t.weight=v; // pomeshem ves
  t.poleznost=p; // pomeshem poleznost
  t.inuse=0; // snachala vesh ne ispolzuetsa
  also[i]=t; // doboavlaem vesh k massivy vse veshey
 }
 cout<<"Vvedite max ves rukzaka\n"; // Vivod priglashenie
 cin>>maxweight; // schitivaem max ves ranca
 for (i=0;i<size;i++)
 {
  cost=0; // snachala ves ranca raven nulu
  have[0]=also[i]; // pomeshem pervuu vesh
  also[i].inuse=1; // pomechem vesh kak ispolzuemuu
  if (have[0].weight<=maxweight) // esli mozho ehse zatalkat chenit...
  {
   perebor(1,cost+have[0].weight); // rekursivno vizivaem funkcuu perebora
  }
  also[i].inuse=0; // snimaem metky ispolzovania
 }
 cout<<"Optimalniy nabor:\n"; // soobshaem o tom, chto nizhe sleduet optimalniy nabor
 for (i=0;i<naborlength;i++)
 {
  cout<<"["<<maxnabor[i].weight<<" , "<<maxnabor[i].poleznost<<"]; "; // vivodim veshi nabora v formate [ves , poleznost];
 }
 cout<<"\n"; // perevod kursora na novuu stroky
 getch(); // zaerzhka pered vihodom iz programmi
 return 0; // vihod iz programmi
}