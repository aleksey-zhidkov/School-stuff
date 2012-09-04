#include <stdio.h>
#include <string.h>
#include <winsock2.h>
#include <windows.h>
#include<io.h>
#include<conio.h>

#define TCP_PORT 1400
#define UDP_PORT 1500
#define MASK "192.168.11.255"
#define CHECK_MESSAGE "Any body here?"
#define HELLO "Hello!\n"

struct timeval tout;
  
int checkServers();
int clientMode();  
int runClientListener();
int serverMode();
DWORD WINAPI listener(LPVOID my_sock);
DWORD WINAPI processClient(LPVOID client_socket);

int clntCount=0;
int rcvBytes;
int *clientSockets;
char buff[10*1024];  
sockaddr_in server_addr;

int main(int argc, char* argv[]) { 
  if (WSAStartup(0x202,(WSADATA *)&buff[0])) {
    printf("WSAStartup error: %d\n",WSAGetLastError());
    return -1;
  }	

  int tmp=checkServers();
  if(tmp) {
    clientMode();
  } else if(tmp!=-1) {
    runClientListener();
    serverMode();  
  } else
    return -1;
  return 0;
}


//----------------------CHECK SERVERS IN THE NET-----------------------------//
int checkServers() {
  printf("Search for servers\n");
    
  SOCKET my_sock=socket(AF_INET, SOCK_DGRAM, 0);
  tout.tv_sec=100;
  tout.tv_usec=0;
  setsockopt(my_sock,SOL_SOCKET,SO_RCVTIMEO,(char*)&tout,sizeof(tout));

  if (my_sock==INVALID_SOCKET) {
    printf("socket() error: %d\n",WSAGetLastError());
    WSACleanup();
    return -1;
  }

  HOSTENT *hst;
  sockaddr_in dest_addr;
  dest_addr.sin_family=AF_INET;
  dest_addr.sin_port=htons(UDP_PORT);
  if (inet_addr(MASK))
    dest_addr.sin_addr.s_addr=inet_addr(MASK);
  else if (hst=gethostbyname(MASK))
    dest_addr.sin_addr.s_addr=((unsigned long **) hst->h_addr_list)[0][0];
  else {
    printf("Unknown host: %d\n",WSAGetLastError());
    closesocket(my_sock);
    WSACleanup();
    return -1;
  }

  strcpy(buff,CHECK_MESSAGE);
  sendto(my_sock,&buff[0],strlen(&buff[0]),0,(sockaddr *) &dest_addr,sizeof(dest_addr));
  int server_addr_size=sizeof(server_addr);
  int n=recvfrom(my_sock,&buff[0],sizeof(buff)-1,0,(sockaddr *) &server_addr, &server_addr_size);
  if (n==SOCKET_ERROR) {
    printf("No servers\n");
    closesocket(my_sock);
    return 0;
  }
    
  printf("Has server\n");
  return 1;
}

//-------------------------------------------CLIENT--------------------------------//
int clientMode() {
  printf("Client mode\n");
  int MySock;
  MySock=socket(AF_INET,SOCK_STREAM,0);
  if (MySock < 0) {
    printf("Socket() error %d\n",WSAGetLastError());
    return -1;
  }
    
  server_addr.sin_port=htons(TCP_PORT);
  if (connect(MySock,(sockaddr *)&server_addr,sizeof(server_addr))) {
    printf("Connect error %d\n",WSAGetLastError());
    return -1;
  }
  
  printf("Connection successfully\n");
  
  setsockopt(MySock,SOL_SOCKET,SO_RCVTIMEO,(char*)&tout,sizeof(tout));  
  while(1){
    rcvBytes=recv(MySock,&buff[0],sizeof(buff)-1,0);
    if(rcvBytes!=SOCKET_ERROR) {
      buff[rcvBytes]=0;
      printf("%s",&buff[0]);
	}
	if(_kbhit()) {
      fgets(&buff[0],sizeof(buff)-1,stdin);
	  if (!strcmp(&buff[0],"quit\n")) {
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

//--------------------------------UDP-SERVER---------------------------------------//
int runClientListener() {    
  int my_sock=0;
  DWORD thID;
  CreateThread(NULL,NULL,listener,&my_sock,NULL,&thID);             
  return 0;
}

DWORD WINAPI listener(LPVOID sock) {
  printf("UDP listener started\n");
  SOCKET my_sock;
  my_sock=socket(AF_INET,SOCK_DGRAM,0);
  if (my_sock==INVALID_SOCKET) {
    printf("Socket() error: %d\n",WSAGetLastError());
    WSACleanup();
    return -1;
  }
    
  sockaddr_in local_addr;
  local_addr.sin_family=AF_INET;
  local_addr.sin_addr.s_addr=INADDR_ANY;
  local_addr.sin_port=htons(UDP_PORT);

  sockaddr_in client_addr;
  int client_addr_size = sizeof(client_addr);
  if (bind(my_sock,(sockaddr *) &local_addr,sizeof(local_addr))) {
    printf("Bind Error");    
    closesocket(my_sock);
    WSACleanup();
    return -1;
  }

  while(1) {     	        
    int bsize=recvfrom(my_sock,&buff[0],sizeof(buff)-1,0,(sockaddr *) &client_addr, &client_addr_size);
    if (bsize==SOCKET_ERROR)
      printf("recvfrom() error: %d\n",WSAGetLastError());
    else
      sendto(my_sock,&buff[0],bsize,0,(sockaddr *)&client_addr, sizeof(client_addr));
  }
}

//--------------------------------TCP-SERVER------------------------------------//
int serverMode() {
  printf("Server mode\n");
  clientSockets=(int*)malloc(sizeof(int)*20);
  int MySock=socket(AF_INET,SOCK_STREAM,0);
 
  sockaddr_in local_addr;
  local_addr.sin_family=AF_INET;
  local_addr.sin_port=htons(TCP_PORT);
  local_addr.sin_addr.s_addr=0;
  if (bind(MySock,(sockaddr *) &local_addr,sizeof(local_addr))) {
    printf("Error bind %d\n",WSAGetLastError());
    closesocket(MySock);
    WSACleanup();
    return -1;
  }

  if (listen(MySock, 0x100)) {
    printf("Error listen %d\n",WSAGetLastError());
    closesocket(MySock);
    WSACleanup();
    return -1;
  }

  int clntSock;    
  sockaddr_in clntAddr;
  int clntAddrSize=sizeof(clntAddr);
  printf("Witing for connect\n");
  while((clntSock=accept(MySock, (sockaddr *)&clntAddr, &clntAddrSize))) {
    printf("New client accepted!\n");
    clientSockets[clntCount]=clntSock;
    clntCount++;
    HOSTENT *hst;
    hst=gethostbyaddr((char *)&clntAddr.sin_addr.s_addr,4, AF_INET);
    DWORD thID;
    CreateThread(NULL,NULL,processClient,&clntSock,NULL,&thID);
  }
  return 0;
}

DWORD WINAPI processClient(LPVOID client_socket) {
  int MySock;
  int i;
  MySock=((SOCKET *) client_socket)[0];
  char buff[20*1024];
  send(MySock,HELLO,sizeof(HELLO),0);
  while((rcvBytes=recv(MySock,&buff[0],sizeof(buff),0))&&(rcvBytes !=SOCKET_ERROR)) {
   for(i=0;i<clntCount;i++)
     send(clientSockets[i],&buff[0],rcvBytes,0);
    write(1,&buff[0],rcvBytes);
  }
  clntCount--;
  printf("disconnect\n"); 
  closesocket(MySock);
  return 0;
}