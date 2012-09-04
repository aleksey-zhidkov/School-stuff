#include <conio.h>
#include <stdio.h>
#include <string.h>
#include <alloc.h>

#define true 1
#define false 0

int* matrix;
int size;
int*vortexes;
int vorsize;

void printMatrix()
{
 for(int i=0;i<size;i++)
 {
  for(int j=0;j<size;j++)
  {
   printf("%i ",matrix[i*size+j]);
  }
  printf("\n");
 }
 printf("\n");
}
void tozero()
{
 for(int i=0;i<size;i++)
 {
  for(int j=0;j<size;j++)
  {
   matrix[i*size+j]=0;
  }
 }
}

int isHave(int n)
{
 for(int c=0;c<vorsize;c++)
 {
  if (vortexes[c]==n) return true;
 }
 return false;
}

void getVortex(int i,int deph)
{
 for(int k=0;k<size;k++)
  {
   if ((matrix[i*size+k]==1)&&(isHave(k)==false))
   {
    vortexes=(int*)realloc(vortexes,sizeof(int)*vorsize+1);
    vortexes[vorsize]=k;
    vorsize++;
    getVortex(k,deph+1);
   }
  }
  if (deph==0)
  {
   int g=i;
   g++;
   printf("%i: ",g);
   for (int v=0;v<vorsize;v++)
   {
    printf(" -%i- ",vortexes[v]+1);
    matrix[(g-1)*size+vortexes[v]]=1;
   }
   vorsize=0;
   printf("\n");
  }
}

void main()
{
 printf("\nPlease, enter the graph size\n");
 scanf("%i",&size);
 vorsize=0;
 tozero();
 printf("Please, enter the links\nlike <from> <to>\nTo exit type '0 0'\n");
 while(1==true)
 {
  int from=0, to=0;
  scanf("%i %i",&from, &to);
  if (from>size||to>size)
  {
   printf("Incorrect vortex!\n");
   continue;
  }
  if (from==0&&to==0) {break;};
  matrix[(from-1)*size+(to-1)]=1;
 }
 printf("\n---------------------\nMatrix before search:\n");
 printMatrix();
 printf("\n------\nPaths:\n");
 for (int i=0;i<size;i++)
 {
  getVortex(i,0);
 }
 printf("\n--------------------\nMatrix after search:\n");
 printMatrix();
 free(matrix);
 free(vortexes);
 getch();
}