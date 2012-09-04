DWORD WINAPI WiteClients(LPVOID my_sock);
 int StartUDPServer()
  {
    
     int my_sock=0;
     DWORD thID;
	 CreateThread(NULL,NULL,WiteClients,&my_sock,NULL,&thID);
     
    
    
    return 0;
  }
  DWORD WINAPI WiteClients(LPVOID sock)
  {
   SOCKET my_sock;
    my_sock=socket(AF_INET,SOCK_DGRAM,0);
    if (my_sock==INVALID_SOCKET)
    {
      printf("Socket() error: %d\n",WSAGetLastError());
      WSACleanup();
      return -1;
    }

    
    sockaddr_in local_addr;
    local_addr.sin_family=AF_INET;
    local_addr.sin_addr.s_addr=INADDR_ANY;
    local_addr.sin_port=htons(PORT);

	sockaddr_in client_addr;
//	int my_sock=((int *)sock)[0];
     // setsockopt(my_sock,SOL_SOCKET,SO_RCVTIMEO,(char*)&tout,sizeof(tout));
	int client_addr_size = sizeof(client_addr);
	if (bind(my_sock,(sockaddr *) &local_addr,sizeof(local_addr)))
    {
      printf("Bind Error");
    }

	while(1)
    {
     
	  
      
      int bsize=recvfrom(my_sock,&buff[0],sizeof(buff)-1,0,(sockaddr *) &client_addr, &client_addr_size);
      if (bsize==SOCKET_ERROR)
        printf("recvfrom() error: %d\n",WSAGetLastError());
	  else
        sendto(my_sock,&buff[0],bsize,0,(sockaddr *)&client_addr, sizeof(client_addr));
    }
  }