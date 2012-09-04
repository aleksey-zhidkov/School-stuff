#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

#define k 4

int *arr;
int totalArrSize;

int puzirek(int *mass)
{
	for(int i=0;i<k;i++)
	{
		for(int j=0;j+1 < k - i;j++)
		{
			if(mass[j]>mass[j+1])
			{
				int m = mass[j];
				mass[j]=mass[j+1];
				mass[j+1]=m;
			}
		}
	}
	return 0;
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

int main(int argc,char* argv[])  {	
    int numtasks, rank, dest, source, rc, tag=1;
    int* inmsg;
    MPI_Status Stat;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);   	
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	totalArrSize = numtasks * k;
	arr = (int*) malloc(sizeof(int) * totalArrSize);
	inmsg = (int*) malloc(sizeof(int) * totalArrSize);

    if (rank==0)  {
	    for (int i = 0; i < totalArrSize; i++)
		{
		    arr[totalArrSize - i - 1] = i;		
		}
	    for (i = 0; i < totalArrSize;i++)
		{
            printf("%i ", arr[i]);
		}
	    printf("\n");

        MPI_Scatter(arr, k, MPI_INT, inmsg, k, MPI_INT, 0, MPI_COMM_WORLD);
	    puzirek(inmsg);
		MPI_Send(inmsg,k,MPI_INT,1,1,MPI_COMM_WORLD);
		rc = MPI_Recv(arr,totalArrSize, MPI_INT, numtasks-1,numtasks,MPI_COMM_WORLD,&Stat);
		for (i = 0; i < totalArrSize;i++)
		{
            printf("%i ", arr[i]);
		}
		printf("\n");
	}
    else if (rank > 0)  {
	    MPI_Scatter(arr, -10000, MPI_INT, inmsg, k, MPI_INT, 0,MPI_COMM_WORLD);		
	    puzirek(inmsg);
		int r = rank + 1;
		if (r == numtasks)
		{
			r = 0;
		}
		int* arr2 = (int*) malloc(sizeof (int) * (k*rank));
		rc = MPI_Recv(arr2,k*rank, MPI_INT, rank-1,r-1,MPI_COMM_WORLD,&Stat);		
		int* arr3 = (int*) malloc(sizeof(int)*(k*(rank+1)));
		mergeSort(inmsg,arr2,arr3, k*(rank+1), k, k*rank);			
		MPI_Send(arr3,k*(rank+1),MPI_INT,r,rank+1,MPI_COMM_WORLD);
		printf("Rank %i is done, sended to %i\n", rank, r);
	}
    MPI_Finalize();
}