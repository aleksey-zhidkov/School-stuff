int TCPConnect(sockaddr_in dest_addr)
{
  int MySock;
  MySock=socket(AF_INET,SOCK_STREAM,0);
  if (MySock < 0)
  {
    printf("Socket() error %d\n",WSAGetLastError());
    return -1;
  }
  dest_addr.sin_port=htons(ServPort);
  if (connect(MySock,(sockaddr *)&dest_addr,sizeof(dest_addr)))
  {
    printf("Connect error %d\n",WSAGetLastError());
    return -1;
  }
  printf("Connection successfully");
  int RcvBytes;
  setsockopt(MySock,SOL_SOCKET,SO_RCVTIMEO,(char*)&tout,sizeof(tout));
  while(1)
  {
    RcvBytes=recv(MySock,&buff[0],sizeof(buff)-1,0);
    if(RcvBytes!=SOCKET_ERROR)
	{
	  buff[RcvBytes]=0;
	  printf("Message recv %s",&buff[0]);
	}
	if(_kbhit())
	{
     fgets(&buff[0],sizeof(buff)-1,stdin);
	 if (!strcmp(&buff[0],"quit\n"))
    {
      printf("Exit...");
      closesocket(MySock);
      WSACleanup();
      return 0;
    }
	  send(MySock,&buff[0],strlen(buff),0);
	}
    
	
    
  }
  printf("Recv error %d\n",WSAGetLastError());
  closesocket(MySock);
  WSACleanup();
  return -1;
}