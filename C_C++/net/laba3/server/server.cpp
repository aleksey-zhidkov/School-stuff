//������ �������� UDP ��� �������
#include <stdio.h>
#include <winsock2.h>
#define PORT 1986 //���� �������
DWORD WINAPI exit(LPVOID sp);

int main(int argc,char*argv []) {
  char buff [1024 ];
  printf("UDP DEMO echo Server \n");
  //��� 1 ����������� ����������
  if (WSAStartup(0x202,(WSADATA *)&buff [0 ])) {
    printf("WSAStartup error:%d \n",WSAGetLastError());
    return -1;
  }
  
  //��� 2 �������� ������
  SOCKET my_sock;
  my_sock=socket(AF_INET,SOCK_DGRAM,0);
  if (my_sock==INVALID_SOCKET) {
    printf("Socket()error:%d \n",WSAGetLastError());
    WSACleanup();
    return -1;
  }
  
  //��� 3 ���������� ������ � ��������� �������
  sockaddr_in local_addr;
  local_addr.sin_family=AF_INET;
  local_addr.sin_addr.s_addr=INADDR_ANY;
  local_addr.sin_port=htons(PORT);
  if (bind(my_sock,(sockaddr *) &local_addr,sizeof(local_addr))) {
    printf("bind error:%d \n",WSAGetLastError());
    closesocket(my_sock);
    WSACleanup();
    return -1;
  }
  
  long lala= 10;
  DWORD exId;
  CreateThread(NULL,NULL,exit,&lala,NULL,&exId);
  //��� 4 ��������� �������,���������� ���������
  while(1) {
    sockaddr_in client_addr;
    int client_addr_size =sizeof(client_addr);
    int bsize=recvfrom(my_sock,&buff [0],sizeof(buff)-1,0,(sockaddr *)&client_addr,&client_addr_size);
    if (bsize==SOCKET_ERROR)
      printf("recvfrom()error:%d \n",WSAGetLastError());
      //���������� IP ����� ������� � ������ ��������
    HOSTENT *hst;
    hst=gethostbyaddr((char *)&client_addr.sin_addr,4,AF_INET);
    printf("+%s [%s:%d ] new DATAGRAM!!\n",
    (hst)?hst->h_name:"Unknown host",inet_ntoa(client_addr.sin_addr),
    ntohs(client_addr.sin_port));
    //���������� ������������ ����
    buff [bsize ]=0;
    //������� ���������� �������
    sendto(my_sock,&buff [0 ],bsize,0,
    (sockaddr *)&client_addr,sizeof(client_addr));
  }
  return 0;
}

DWORD WINAPI exit(LPVOID s) {
  printf("To exit type exit");
  char* input;
  input = (char*) malloc(4);
  gets(input);
  exit(0);
  return 0;
}