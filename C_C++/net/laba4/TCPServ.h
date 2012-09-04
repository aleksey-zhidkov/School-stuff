
int ClntCount=0;
int RcvBytes;
int *ClientSockets;
DWORD WINAPI SexToClient(LPVOID client_socket);
int CreateTCPServ()
{
  ClientSockets=(int*)malloc(sizeof(int)*20);
  int MySock=socket(AF_INET,SOCK_STREAM,0);
 
  sockaddr_in local_addr;
  local_addr.sin_family=AF_INET;
  local_addr.sin_port=htons(ServPort);
  local_addr.sin_addr.s_addr=0;
  if (bind(MySock,(sockaddr *) &local_addr,sizeof(local_addr)))
  {
    printf("Error bind %d\n",WSAGetLastError());
    closesocket(MySock);
    WSACleanup();
    return -1;
  }
  if (listen(MySock, 0x100))
  {
    printf("Error listen %d\n",WSAGetLastError());
    closesocket(MySock);
    WSACleanup();
    return -1;
  }
  int ClntSock;    
  sockaddr_in ClntAddr;
  int ClntAddrSize=sizeof(ClntAddr);
  printf("Witing for connect\n");
  while((ClntSock=accept(MySock, (sockaddr *)&ClntAddr, &ClntAddrSize)))
  {
    //ClientSockets[ClntCount]=(int*)malloc(sizeof(int));
	ClientSockets[ClntCount]=ClntSock;
	ClntCount++;
	HOSTENT *hst;
    hst=gethostbyaddr((char *)&ClntAddr.sin_addr.s_addr,4, AF_INET);
    DWORD thID;
    CreateThread(NULL,NULL,SexToClient,&ClntSock,NULL,&thID);
  }
    return 0;
}
  DWORD WINAPI SexToClient(LPVOID client_socket)
  {
    int MySock;
	int i;
    MySock=((SOCKET *) client_socket)[0];
    char buff[20*1024];
    #define St "Hello\n"
    send(MySock,St,sizeof(St),0);
    while((RcvBytes=recv(MySock,&buff[0],sizeof(buff),0))&&(RcvBytes !=SOCKET_ERROR))
	{
		for(i=0;i<ClntCount;i++)
		  send(ClientSockets[i],&buff[0],RcvBytes,0);
//  send(MySock,&buff[0],RcvBytes,0);
        write(1,&buff[0],RcvBytes);
	}
    ClntCount--;
    printf("disconnect\n"); 
	closesocket(MySock);
    return 0;
}
