//#define PORT 666
int UDPConnect(char *SERVERADDR)
  {
  printf("UDP DEMO Client\nType quit to quit\n");
    
    SOCKET my_sock=socket(AF_INET, SOCK_DGRAM, 0);
	tout.tv_sec=500;tout.tv_usec=0;
	setsockopt(my_sock,SOL_SOCKET,SO_RCVTIMEO,(char*)&tout,sizeof(tout));
    if (my_sock==INVALID_SOCKET)
    {
      printf("socket() error: %d\n",WSAGetLastError());
      WSACleanup();
      return -1;
    }
    HOSTENT *hst;
    sockaddr_in dest_addr;
    dest_addr.sin_family=AF_INET;
    dest_addr.sin_port=htons(PORT);
    if (inet_addr(SERVERADDR))
      dest_addr.sin_addr.s_addr=inet_addr(SERVERADDR);
    else
      if (hst=gethostbyname(SERVERADDR))
        dest_addr.sin_addr.s_addr=((unsigned long **)
              hst->h_addr_list)[0][0];
    else
      {
        printf("Unknown host: %d\n",WSAGetLastError());
        closesocket(my_sock);
        WSACleanup();
        return -1;
      }

  
	  strcpy(buff,"Est' kto jivoy?");
      sendto(my_sock,&buff[0],strlen(&buff[0]),0,(sockaddr *) &dest_addr,sizeof(dest_addr));
      int server_addr_size=sizeof(server_addr);
      int n=recvfrom(my_sock,&buff[0],sizeof(buff)-1,0,(sockaddr *) &server_addr, &server_addr_size);
      if (n==SOCKET_ERROR)
      {
        closesocket(my_sock);
        return 1;
      }
    
    return 0;
  }