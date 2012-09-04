#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define k 5

int *arr;
int totalArrSize;

void puzirek(int *mass, int len)
{
	for(int i=0;i<len;i++)
	{
		for(int j=0;j+1 < len - i;j++)
		{
			if(mass[j]>mass[j+1])
			{
				int m = mass[j];
				mass[j]=mass[j+1];
				mass[j+1]=m;
			}
		}
	}
}

void mergeSort(int *a, int *b, int *res, int len, int lena, int lenb)
{
	int i = 0, h = 0;
    for (int j = 0; j < len; j++)
	{
		if (h == lenb)
		{
			res[j] = a[i++];
		}
		else if (i == lena)
		{
			res[j] = b[h++];
		}
		else if (a[i] <= b[h])
		{
			res[j] = a[i];
			i++;
		}
		else
		{
			res[j] = b[h];
			h++;
		}
	}
}

int log2(int num)
{
	int res = 1;
	while (num / 2 >= 2)
	{
		res++;
		num = num / 2;
	}
	return res;
}

void printArray(int* arr, int len)
{
	for (int i =0; i < len; i++)
	{
		printf("%i ", arr[i]);
	}
	printf("\n");
}

int pow2(int a, int b)
{

	int res = a;
	for (int i = 1; i < b; i++)
	{
		res *= a;
	}
	return res;

}

int getMaxDeph(int elements)
{
	return log2(elements+1) - 1;
}

int main(int argc,char* argv[])  {	
	int* arr1;
	int* arr2;
	int* arr3;
    int numtasks, rank, rc, tag=1;
    int* inmsg;
    MPI_Status Stat;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);   	
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);	
	
	if  (pow2(2, log2(numtasks + 1)) != numtasks + 1 || numtasks == 1)
	{
		if (rank == 0)
		{
	        printf("Process count must be pow of 2\n");
		}	  
	    MPI_Finalize();
	    return -1;
	}


	int s = pow(2, getMaxDeph(numtasks)) - 1;

	arr = (int*) malloc(sizeof(int) * totalArrSize);

	arr1 = (int*) malloc(sizeof(int) * totalArrSize / 2);
	arr2 = (int*) malloc(sizeof(int) * totalArrSize / 2);
	arr3 = (int*) malloc(sizeof(int) * totalArrSize);
	totalArrSize = pow2(2,getMaxDeph(numtasks)) * k;	
	inmsg = (int*) malloc(sizeof(int) * totalArrSize);

    if (rank==0)  {
		arr = (int*) realloc(arr,sizeof(int) * totalArrSize);

		arr1 = (int*) realloc(arr1,sizeof(int) * totalArrSize / 2);
		arr2 = (int*) realloc(arr2,sizeof(int) * totalArrSize / 2);
		arr3 = (int*) realloc(arr3,sizeof(int) * totalArrSize);
	    for (int i = 0; i < totalArrSize; i++)
		{
		    arr[totalArrSize - i - 1] = i;		
		}
	    for (i = 0; i < totalArrSize;i++)
		{
            printf("%i ", arr[i]);
		}
	    printf("\n");
        
		int j = 0;		
		//printf("!!!%i %i\n", initSize, s);
		for (i = s; i < numtasks; i++)
		{			
			MPI_Send(arr+j*k,k,MPI_INT,i,i,MPI_COMM_WORLD);			
			j++;
		}
		rc = MPI_Recv(arr1,totalArrSize, MPI_INT, 1,1,MPI_COMM_WORLD,&Stat);
		rc = MPI_Recv(arr2,totalArrSize, MPI_INT, 2,2,MPI_COMM_WORLD,&Stat);
		printf("\n");
		mergeSort(arr1, arr2, arr3, totalArrSize, totalArrSize / 2, totalArrSize / 2);
		for (i = 0; i < totalArrSize;i++)
		{
            printf("%i ", arr3[i]);
		}
		printf("\n");
	}
    else if (rank >= pow(2,getMaxDeph(numtasks))-1)  {
		int maxDeph = log2(numtasks);
		arr = (int*) realloc(arr,sizeof(int) * k);
	    rc = MPI_Recv(arr, k, MPI_INT, 0, rank,MPI_COMM_WORLD, &Stat);
	    puzirek(arr, k);
		int d;
		if (rank % 2 == 0)
		{
			d = 2;
		}
		else
		{
			d = 1;
		}
		MPI_Send(arr,k,MPI_INT,(rank-d)/2,rank,MPI_COMM_WORLD);		
	}
	else
	{
		int maxDeph = log2(numtasks + 1);
		int p = 0;

		while(true)
		{
			int g = pow(2, p) - 1;
			if (rank < g)
			{
				break;
			}
			p = p + 1;
		}		
		int tmp = pow2(2,maxDeph - p)*k / 2;
		arr1 = (int*) realloc(arr1,sizeof(int) * tmp);
		arr2 = (int*) realloc(arr2,sizeof(int) * tmp);
		arr3 = (int*) realloc(arr3,sizeof(int) * tmp * 2);
		rc = MPI_Recv(arr1,tmp, MPI_INT, rank*2+1,rank*2+1,MPI_COMM_WORLD,&Stat);
		rc = MPI_Recv(arr2,tmp, MPI_INT, rank*2+2,rank*2+2,MPI_COMM_WORLD,&Stat);
		printArray(arr1, tmp);
		mergeSort(arr1,arr2,arr3,tmp*2, tmp, tmp);
		int d;
		if (rank % 2 == 0)
		{
			d = 2;
		}
		else
		{
			d = 1;
		}
		MPI_Send(arr3, tmp * 2,  MPI_INT,(rank-d)/2, rank, MPI_COMM_WORLD);
	}
    MPI_Finalize();
}