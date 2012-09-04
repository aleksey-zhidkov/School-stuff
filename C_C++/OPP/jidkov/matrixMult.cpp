#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

#define N 5

/*int A[N*N] = {3,3,3,4,3,3,
              5,5,5,4,5,5,
			  6,6,6,4,6,6,
			  7,7,7,4,7,7,
			  8,8,8,4,8,8,
			  0,0,0,4,0,0};

int B[N*N] = {1,0,0,0,0,0,
              0,1,0,0,0,0,
			  0,0,1,0,0,0,
			  0,0,0,1,0,0,
			  0,0,0,0,1,0,
			  0,0,0,0,0,1};

int C[N*N] = {-1, -1, -1,-1,-1,-1,
              -1, -1, -1,-1,-1,-1,
              -1, -1, -1,-1,-1,-1,
			  -1, -1, -1,-1,-1,-1,
			  -1, -1, -1,-1,-1,-1,
			  -1, -1, -1,-1,-1,-1};*/

int A[N*N] = {3,3,3,4,3,
              5,5,5,4,5,
			  6,6,6,4,6,
			  7,7,7,4,7,
			  8,8,8,4,8};

int B[N*N] = {1,0,0,0,0,
              0,1,0,0,0,
			  0,0,1,0,0,
			  0,0,0,1,0,
			  0,0,0,0,1};

int C[N*N] = {-1,-1,-1,-1,-1,
              -1,-1,-1,-1,-1,
              -1,-1,-1,-1,-1,
			  -1,-1,-1,-1,-1,
			  -1,-1,-1,-1,-1};

int* a;
int* b;

void printMatrix(int* matrix, int width, int height)
{
    for (int i = 0; i < width; i++)
	{
		for (int j = 0; j < height; j++)
		{
			printf("%i ", matrix[i*width +j]);
		}
		printf("\n");
	}
}

int* getRow(int *matrix, int rowIdx, int rowSize) {
	int *res = (int*) malloc(sizeof(int) * N * rowSize);

	int k = 0;
	for (int i = rowIdx*rowSize; k < rowSize; i++, k++)
	{
		for (int j = 0; j < N; j++)
		{
			res[k*N + j] = matrix[i*N + j];
		}
	}
	return res;
}

int* getCol(int *matrix, int colIdx, int colSize) {
	int *res = (int*) malloc(sizeof(int) * N * colSize);
	
	for (int i = 0; i < N; i++)
	{
		int k = 0;
		for (int j = colIdx*colSize; k < colSize; j++, k++)
		{
			res[i*colSize + k] = matrix[i*N + j];
		}
	}

	return res;
}

int* mtrMul(int *a, int *b, int size)
    {
        int *res = (int*) malloc(sizeof(int) * size * size);

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < size; j++)
            {
                int sum = 0;

                for (int k = 0; k < N; k++)
                {
                    sum+= a[i*N+k] * b[k*size+j];
                }

                res[i*size+j] = sum;
            }
        }

        return res;
    }

void fillMatrix(int *a, int *c, int iStart, int jStart, int al1, int al2)
    {
        for (int i = 0; i < al1; i++)
        {
            for (int j = 0; j < al2; j++)
            {
                c[(iStart + i)*N+jStart + j] = a[i*al1 + j];
            }
        }
    }

int main(int argc,char* argv[])  {
	//int* mm = mtrMul(getRow((int*) &A, 0, N/6),getCol((int*) &B, 0, N/6),N/6);		
	int numtasks, rank, rc;
    MPI_Status Stat;
	MPI_Request req;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);   	
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int size = N*N/numtasks;

    if (rank==0)  {
		for (int i = 0; i < numtasks; i++)
		{			
			MPI_Isend(getRow((int*) &A, i, N/numtasks),size,MPI_INT,i,i,MPI_COMM_WORLD, &req);
			MPI_Isend(getCol((int*) &B, i, N/numtasks),size,MPI_INT,i,i,MPI_COMM_WORLD, &req);			
		}
	}	
	a = (int*) malloc(sizeof(int) * size);
	b = (int*) malloc(sizeof(int) * size);

	rc = MPI_Recv(a,size, MPI_INT, 0, rank,MPI_COMM_WORLD,&Stat);
	rc = MPI_Recv(b,size, MPI_INT, 0, rank,MPI_COMM_WORLD,&Stat);	

	int bSource = rank;
	for (int i = 0; i < numtasks; i++)
	{
		int* mm = mtrMul(a,b,N/numtasks);		
    		
	    fillMatrix(mm,(int*) &C, rank*N/numtasks, (bSource--)*N/numtasks,N/numtasks,N/numtasks);

		if (bSource == -1)
		{
			bSource = numtasks - 1;
		}
		int nextRank = rank + 1;
	    if (nextRank == numtasks)
		{
		    nextRank = 0;
		}
		int prevRank = rank - 1;
	    if (prevRank == -1)
		{
		    prevRank = numtasks - 1;
		}
		MPI_Isend(b,size,MPI_INT,nextRank,rank,MPI_COMM_WORLD, &req);
		rc = MPI_Recv(b,size, MPI_INT, prevRank, prevRank,MPI_COMM_WORLD,&Stat);		
	}    

	if (rank > 0)
	{
		MPI_Isend(getRow((int*) &C, rank, N / numtasks),size,MPI_INT,0,rank,MPI_COMM_WORLD, &req);
	}

	if (rank == 0)
	{
		for (int i = 1; i < numtasks; i++)
		{
			rc = MPI_Recv(a,size, MPI_INT, i, i,MPI_COMM_WORLD,&Stat);
			fillMatrix(a,(int*) &C, i * N / numtasks, 0, N, N);
		}		
		printf("Result:\n");
	    printMatrix((int*) &C, N, N);
	}
	
    MPI_Finalize();	
    return 0;
}