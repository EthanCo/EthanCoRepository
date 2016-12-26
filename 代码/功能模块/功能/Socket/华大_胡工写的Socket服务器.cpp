
#include "MusicWork.h"
#include "Common.h"
int m_sockListen;
bool  m_bExitThreadrecv;
bool  m_bExitThreadrecvSendAlarm;
bool m_bExitThread;
bool m_bAuthentication;
bool bGlobalOnline;
int mAlarmZone;
SHBACKGROUNDMUSIC_STAT BgmState[16];
int mLogOnID;

void*  ThreadProc(void *pParam)
{
	ThreadProc1();
	return 0;
}

void*  ThreadProcRecv(void *pParam)
{
 	ThreadProc1Recv((int)pParam);
	return 0;
}
void*  ThreadProcSendAlarm(void *pParam)
{
	ThreadProcSendAlarm2((int)pParam);
	return 0;
}



void  ThreadProc1(void)
{
	m_bExitThread = false;
	while ( !m_bExitThread )
	{
		Thread_Process();
		DH_Sleep(10);
	}

	closesocket(m_sockListen);
}

void ThreadProc1Recv(int sock)
{
	m_bExitThreadrecv = false;
	while ( !m_bExitThreadrecv )
	{
		Thread_ProcessRecv(sock);
		DH_Sleep(10);
	}
	closesocket(sock);
}

void ThreadProcSendAlarm2(int sock)
{
	m_bExitThreadrecvSendAlarm = false;
	while ( !m_bExitThreadrecvSendAlarm )
	{
		Thread_ProcessSendAlarm(sock);
		DH_Sleep(10);
	}
	closesocket(sock);
}


void Thread_Process()
{
	PollData();
}

void Thread_ProcessRecv(int sock)
{
	RecvData(sock);
}
void Thread_ProcessSendAlarm(int sock)
{
	RecvDataSendAlarm(sock);
}



bool StartListen(void)
{
 
	//(1)初始化套结字动态库:版本信心等...
	WSADATA wsd; //WSADATA变量
	if (WSAStartup(MAKEWORD(2,2), &wsd) != 0)
	{
		//cout << "WSAStartup failed!" << endl;
		my_printf(PRINT_NORMAL,"WSAStartup failed!");
		return 1;
	}
	//（2）创建套接字
	m_sockListen = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if(INVALID_SOCKET == m_sockListen)
	{ 
		my_printf(PRINT_NORMAL,"%d",GetLastError());
		my_printf(PRINT_NORMAL,"socket failed!");
		WSACleanup();//释放套接字资源;
		return  -1;
	}
	sockaddr_in addrServ;
	//（3）填写服务器套接字地址 
	addrServ.sin_family = AF_INET;
	addrServ.sin_port = htons(MPORT);
	addrServ.sin_addr.s_addr = INADDR_ANY;//注意INADDR_ANY的含义
	//绑定套接字
	int retVal = bind(m_sockListen, (LPSOCKADDR)&addrServ, sizeof(SOCKADDR_IN));

	if(SOCKET_ERROR == retVal)
	{
		my_printf(PRINT_NORMAL,"bind failed!");
		
		closesocket(m_sockListen); //关闭套接字
		WSACleanup(); //释放套接字资源;
		return -1;
	}
	//开始监听 
	retVal = listen(m_sockListen, 5);
	if(SOCKET_ERROR == retVal)
	{
		my_printf(PRINT_NORMAL,"listen failed!");
		closesocket(m_sockListen); //关闭套接字
		WSACleanup(); //释放套接字资源;
		return -1;
	}

	return true;	
}
bool DH_BeginThread(void *startAddress, void *parameter)
{
	HANDLE handle = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)startAddress, (LPVOID)parameter, 0, NULL);
	if (handle == NULL)
	{
		fprintf(stderr, "pthread_create() fail : %s\n", strerror(GetLastError()));
		return false;
	}
	return true;
}
 
bool SetNoBlock(SOCKET sock)
{
	int iRet;

	int iBlock = 1; 
	iRet = ::ioctlsocket(m_sockListen,FIONBIO,(u_long FAR *)&iBlock);
	if ( iRet == FCL_SOCKET_ERROR ) 
	{
		my_printf(PRINT_NORMAL,"set nonblock failed.err .");
		return false;
	}
 
	return true;
}
bool SetReuseAddr(SOCKET sock) 
{
	int iValue = 1; 
	//bool bRet; 
	int iRet; 
	iRet = setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,(char*)&iValue,sizeof(iValue)); 
	if ( iRet == FCL_SOCKET_ERROR ) 
	{ 
		my_printf(PRINT_NORMAL,"setsockopt failed.err=%d",iRet); 
		return false; 
	} 
	else 
	{ 
		return true; 
	} 
}

void PollData()
{
 

	int fds;
	timeval tv;
	int iTotal;
	fd_set fd_send;
	fd_set fd_recv;
	fd_set fd_expt;
	sockaddr_in addr;
	int iAddrSize = sizeof(addr);

	tv.tv_sec = 0;
	tv.tv_usec = 200*1000;
	fds = 0;

	FD_ZERO(&fd_send);
	FD_ZERO(&fd_recv);
	FD_ZERO(&fd_expt);
	if ( FCL_INVALID_SOCKET != m_sockListen )
	{
		FD_SET(m_sockListen,&fd_recv);
		fds = (int)m_sockListen;
	}

	if ( fds <= 0 )
	{
		DH_Sleep(1);
		return ;
	}
	iTotal = select((int)fds+1,&fd_recv,&fd_send,&fd_expt,&tv);
 
	if ( SOCKET_ERROR == iTotal && (errno = WSAGetLastError()) == WSAEWOULDBLOCK )
	{
 
		if ( FCL_SOCKET_ERROR == iTotal )
		{
			my_printf(PRINT_NORMAL,"recv eror. errno");
			return ;
		}
	}

	if ( 0 == iTotal )
	{
		//超时
		return ;
	}
	//处理新连接
	if ( FD_ISSET(m_sockListen,&fd_recv) ) //新连接
	{
		int sock = accept(m_sockListen,(sockaddr*)&addr,&iAddrSize);
		if ( FCL_INVALID_SOCKET == sock ) //错误
		{
			my_printf(PRINT_NORMAL,"CVTGeneral::NetTransTcp() : socket errno .");
		}
		else
		{
			if ( !SetNoBlock(sock) )
			{
				my_printf(PRINT_NORMAL,"set noblock error. errno .");
				FCL_CLOSE_SOCKET(m_sockListen);
			}
			else
			{

 


					if (!DH_BeginThread((void*)ThreadProcRecv,(void *)sock)) 
					{
						my_printf(PRINT_NORMAL,"DH_BeginThread() ThreadProcRecv failed.");
						return  ;
					}


 

			}
		}
	}

}

 

void RecvData(int sock)
{
	struct timeval timeout = {3,0}; 
	setsockopt(sock,SOL_SOCKET,SO_RCVTIMEO, (char *)&timeout,sizeof(struct timeval));

	unsigned char recvbuf[100] = {0};
	memset(recvbuf,0,sizeof(recvbuf));
	int len = recv(sock,(char *)recvbuf,sizeof(recvbuf),0);


	if((len >0))
	{
		//debug_print(PRINT_INFO,"len ===%d------------",len);
		//debug_print(PRINT_INFO,"\n%s",(char *)recvbuf);
		OnDataRecv(sock,(unsigned char *)recvbuf,len); 
	}
}


int OnDataRecv(SOCKET sock,unsigned char *pData, int iDatalen)
{
	if(pData[12] != 0x02)
	{
		my_printf0XRECV((unsigned char *)pData,iDatalen);
	}
	
	OnDataSend(sock,(unsigned char *)pData,iDatalen);		
	return 0;
}
int RecvDataSendAlarm(SOCKET sock)
{

	char c;
	scanf("%c",&c);


	switch (c)
	{
	case 'y':
	case 'Y':
		{
			unsigned char sendbuf[100];

			memset(sendbuf,0,100);
			int coldlen = Encode_AlarmAppend((unsigned char *)sendbuf,1,mLogOnID,0,1);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
			break;
		}
	case 'n':
	case 'N':
		{
			
			unsigned char sendbuf[100];

			memset(sendbuf,0,100);
			int coldlen = Encode_AlarmAppend((unsigned char *)sendbuf,1,mLogOnID,0,0);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
			break;
		
		}

	default:break;
	}
	return 0;
}

 
int OnDataSend(SOCKET sock,unsigned char *pData, int iDatalen)
{

	if(pData[0]!=0xae ||pData[1]!=0xea )
	{	
		return -1;
	}


	int  mIdRecv=(pData[2]<<24)|(pData[3]<<16)|(pData[4]<<8)|(pData[5]);
	int  mSeqDataRecv=(pData[6]<<24)|(pData[7]<<16)|(pData[8]<<8)|(pData[9]);
	int  mlen = pData[10];
	int  mCh  = pData[11];

	unsigned char sendbuf[100];
	switch(pData[12])
	{
	case 0x01:
		{
			char pUser[16];
			char pPassword[100];
			memset(pUser,0,16);
			memset(pPassword,0,100);	
			strncpy(pUser,(char *)pData+15,pData[14]);
			pUser[pData[14]] ='\0';
			strncpy(pPassword,(char *)pData+17+pData[14],pData[16+pData[14]]);
			pPassword[pData[16+pData[14]]] ='\0'; 
			my_printf(PRINT_NORMAL,"pUser =%s ,pPassword =%s",(char *)pUser,pPassword);
			if(m_bAuthentication)//二次鉴权
			{

				if(0==strncmp(pPassword,"******",6))//first jianquan 
				{

					if(0 == strncmp(MPUSER,pUser,pData[14]))
					{
						int coldlen =Encode_Register2SuccessSendPwd((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
						if(0 == send(sock,(char *)sendbuf,coldlen,0))
						{
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!---------");
						}
						else
						{
							my_printf0X((unsigned char *)sendbuf,coldlen);
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!---------");


						}
					}
					else
					{
						int coldlen =Encode_Register1ErrByUserName((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
						if(0 == send(sock,(char *)sendbuf,coldlen,0))
						{
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!(Register error by pUser is not right)---------");
						}
						else
						{
							my_printf0X((unsigned char *)sendbuf,coldlen);
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!(Register error by pUser is not right)---------");
						}
					}
				}
				else
				{


					//Sleep(1000);
					//计算md5鉴权分析做对比然后
					 
					if(0 == strncmp(MPUSER,pUser,pData[14]) )
					{
						int coldlen =Encode_Register2Success((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
						if(0 == send(sock,(char *)sendbuf,coldlen,0))
						{
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!---------");
						}
						else
						{
							my_printf0X((unsigned char *)sendbuf,coldlen);
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!---------");


						}
					}
					else
					{
						int coldlen =Encode_Register1ErrByUserName((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
						if(0 == send(sock,(char *)sendbuf,coldlen,0))
						{
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!(Register error by pUser is not right)---------");
						}
						else
						{
							my_printf0X((unsigned char *)sendbuf,coldlen);
							my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!(Register error by pUser is not right)---------");
						}
					}
				}


			}
			else//不做鉴权
			{
				if(0 == strncmp(MPUSER,pUser,pData[14]))
				{
					int coldlen =Encode_Register1Success((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
					if(0 == send(sock,(char *)sendbuf,coldlen,0))
					{
						my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!---------");
					}
					else
					{
						my_printf0X((unsigned char *)sendbuf,coldlen);
						my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!---------");
					}
				}
				else
				{
					int coldlen =Encode_Register1ErrByUserName((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
					if(0 == send(sock,(char *)sendbuf,coldlen,0))
					{
						my_printf(PRINT_NORMAL,"SendRegisterResponse  send fail!(Register error by pUser is not right)---------");
					}
					else
					{
						my_printf0X((unsigned char *)sendbuf,coldlen);
						my_printf(PRINT_NORMAL,"SendRegisterResponse  send success!(Register error by pUser is not right)---------");
					}
				}


 

			}

			mLogOnID = mIdRecv;

			//int a = sock;
			//if (!DH_BeginThread((void*)ThreadProcSendAlarm,(void *)a)) 
			//{
			//	my_printf(PRINT_NORMAL,"DH_BeginThread() ThreadProcRecv failed."); 
			//}


		}break;//登录
	case 0x10:
		{
			int coldlen = Encode_RegisterLogOut((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);
			my_printf0X((unsigned char *)sendbuf,coldlen);
		}break;//登出
	case 0x02:
		{
			int coldlen = Encode_heartbeat((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);
			//my_printf0X((unsigned char *)sendbuf,coldlen);
			bGlobalOnline =false;
		}break;//心跳保活
	case 0x03:
		{
			int coldlen = Encode_QuerySerialNumber((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
			bGlobalOnline =true;
		}break;//查询序列号
	case 0x04:break;//查询设备型号
	case 0x05:break;//查询程序版本
	case 0x11:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			BgmState[iChannel - 1].bIsOn = m_flag;
			int coldlen = Encode_PowerControl((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
		}break;//开关机
	case 0x12:
		{
			int iChannel = pData[11];

			int coldlen = Encode_GetChInfo((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
		}break;//查询通道信息
	case 0x21:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			BgmState[iChannel - 1].m_Src = m_flag;
			int coldlen = Encode_SwitchSoundSrc((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//音源切换
	case 0x22:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			BgmState[iChannel - 1].iVolume = m_flag ;
			int coldlen = Encode_SetVolume((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//音量设置
	case 0x23:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			int mTreble =(m_flag&0xf0)>>4;
			int mBass=m_flag&0x0f;
			BgmState[iChannel - 1].m_VolHigh =mTreble;
			BgmState[iChannel - 1].m_VolBass =mBass;
			int coldlen = Encode_SetTrebleBass((unsigned char *)sendbuf,iChannel,mTreble,mBass,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//高低音设置
	case 0x24:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			BgmState[iChannel - 1].bMute =m_flag;
			int coldlen = Encode_SetMuteMode((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
		}break;//静音/取消
	case 0x30:
		{
			int iChannel = pData[11];  
			BgmState[iChannel - 1].playtime.iHour =0;
			BgmState[iChannel - 1].playtime.iMin =3;
			BgmState[iChannel - 1].playtime.iSec =45;
			strcpy(BgmState[iChannel - 1].sSong,"abc.mp3");

			int coldlen = Encode_GetCurrentSongInfo((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);	

	

		}break;//查询当前歌曲信息
	case 0x31:
		{

			int iChannel = pData[11];
			int m_flag = pData[13];
			if (m_flag) strcpy(BgmState[iChannel - 1].sState,"Play");
			else	strcpy(BgmState[iChannel - 1].sState,"Pause");
			int coldlen = Encode_SetPauseResumePlay((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//播放/暂停
	case 0x32:
		{
			int iChannel = pData[11];
			int m_flag = pData[13]; //该值无效
			int coldlen = Encode_SetUpDownSong((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}
		break;//上一曲/下一曲
	case 0x33:
		{
			int iChannel = pData[11];
			int m_flag = pData[13];
			BgmState[iChannel - 1].m_SingleCycle =m_flag;
			int coldlen = Encode_SetPlayMode((unsigned char *)sendbuf,iChannel,m_flag,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//播放模式选择	
	case 0x34:
		{
			int iChannel = pData[11]; 
			int m_flag = pData[13]<<8|pData[14];
			BgmState[iChannel - 1].Currentplaytime.iHour =m_flag/3600;
			BgmState[iChannel - 1].Currentplaytime.iMin =(m_flag - BgmState[iChannel - 1].Currentplaytime.iHour*3600)/60;
			BgmState[iChannel - 1].Currentplaytime.iHour =m_flag - BgmState[iChannel - 1].Currentplaytime.iHour*3600 -BgmState[iChannel - 1].Currentplaytime.iMin*60;
			int coldlen = Encode_SetPlayTime((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//播放时间控制
	case 0x40:
		{
			int iChannel = pData[11]; 
			int coldlen = Encode_BrowseCatalogInfo1((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
 
			memset(sendbuf,0,100);
			coldlen = Encode_BrowseCatalogInfo2((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
 
			memset(sendbuf,0,100);
			coldlen = Encode_BrowseCatalogInfo3((unsigned char *)sendbuf,iChannel,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//浏览目录
	case 0x41:
		{
			int iChannel = pData[11]; 
			int mCatalog = pData[13]; 
			int coldlen = Encode_BrowseSongInfo_a((unsigned char *)sendbuf,iChannel,mCatalog,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

			memset(sendbuf,0,100);
			coldlen = Encode_BrowseSongInfo_b((unsigned char *)sendbuf,iChannel,mCatalog,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

			memset(sendbuf,0,100);
			coldlen = Encode_BrowseSongInfo_c((unsigned char *)sendbuf,iChannel,mCatalog,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;//浏览歌曲
	case 0x42:
		{

			int iChannel = pData[11]; 
			int mCatalog = pData[13]; 
			int mSong = pData[14]; 
			int coldlen = Encode_ChooseSong((unsigned char *)sendbuf,iChannel,mCatalog,mSong,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);
		}break;//选择歌曲
		
		//报警主机的查询	
	case 0x51:
		{
			int coldlen = Encode_QueryAlarmZoneInfo((unsigned char *)sendbuf,mIdRecv,mSeqDataRecv);
			send(sock,(char *)sendbuf,coldlen,0);	
			my_printf0X((unsigned char *)sendbuf,coldlen);

		}break;

	}


	return 0 ; 
}
int Encode_Register1Success(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 5;
	buf[11] = 0;
	buf[12] = 0x01;
	buf[13] = 0x00;
	buf[14] = 0x01;
	buf[15] = 0x01;

	buf[16] = CalcPFKcrc8r((unsigned char*)buf, 16);

	return 17;

}
int Encode_Register1ErrByUserName(unsigned char *buf,int mId,int mSeqData)//2次鉴权第一次的错误发也是这个回复
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = 0;
	buf[12] = 0x01;
	buf[13] = 0x01; 
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14);

	return 15;
}
int Encode_Register2SuccessSendPwd(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10]= 0x17;
	buf[11]= 0;
	buf[12]= 0x01;
	buf[13]= 0x04;
	buf[14]= 0x01;
	buf[15]= 0x01;	
	buf[16]= 0x73;
	buf[17]= 0x10;

	// 65 39 61 63 30 39 34 30 39 31 62 39 36 62 38 34 
	buf[18]= 0x65;
	buf[19]= 0x39;
	buf[20]= 0x61;
	buf[21]= 0x63;
	buf[22]= 0x30;
	buf[23]= 0x39;
	buf[24]= 0x34;
	buf[25]= 0x30;
	buf[26]= 0x39;
	buf[27]= 0x31;
	buf[28]= 0x62;
	buf[29]= 0x39;
	buf[30]= 0x36;
	buf[31]= 0x62;
	buf[32]= 0x38;
	buf[33]= 0x34;


	buf[34] = CalcPFKcrc8r((unsigned char*)buf, 34);



	return 35;
 
}

int Encode_Register2Success(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 5;
	buf[11] = 0;
	buf[12] = 0x01;
	buf[13] = 0x00;
	buf[14] = 0x01;
	buf[15] = 0x01;

	buf[16] = CalcPFKcrc8r((unsigned char*)buf, 16);

	return 17;

}


int Encode_heartbeat(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = 0;
	buf[12] = 0x02;
	buf[13] = 0x01;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14);
 
	return 15;

}

int Encode_QuerySerialNumber(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 0x0B;
	buf[11] = 0;
	buf[12] = 0x03;
	buf[13] = 0x37;
	buf[14] = 0x01;
	buf[15] = 0x09;
	buf[16] = 0x94;
	buf[17] = 0x10;
	buf[18] = 0x00;
	buf[19] = 0x01;
	buf[20] = 0x88;
	buf[21] = 0x21;
	buf[22] = CalcPFKcrc8r((unsigned char*)buf, 22);

	return 23;
}

int Encode_RegisterLogOut(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 2;
	buf[11] = 0;
	buf[12] = 0x10;
	buf[13] = CalcPFKcrc8r((unsigned char*)buf, 13); 

	return 14;
}
int Encode_PowerControl(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{

	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x11;
	buf[13] = m_flag;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}
int Encode_GetChInfo(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 9 ;
	buf[11] = iChannel;
	buf[12] = 0x12;
	int bPause =0;
	if(0== strcmp(BgmState[iChannel-1].sState,"Pause"))
	{
		bPause = 0;
	}
	else
	{
		bPause = 1;
	}
	
	buf[13] = (BgmState[iChannel-1].bMute<<2)|(bPause<<1)|(BgmState[iChannel-1].bIsOn);
	buf[14] = BgmState[iChannel-1].iVolume ;
	buf[15] =((BgmState[iChannel-1].m_VolHigh<<4)|BgmState[iChannel-1].m_VolBass);
	buf[16] = BgmState[iChannel-1].m_Src;
	buf[17] = BgmState[iChannel-1].m_SingleCycle;
	buf[18] = 0;
	buf[19] = 0;
	buf[20] = CalcPFKcrc8r((unsigned char*)buf, 20); ;


	return 21 ; 



}

int Encode_SwitchSoundSrc(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData )
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x21;
	buf[13] = m_flag;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}


int Encode_SetVolume(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x22;
	buf[13] = m_flag;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}

int Encode_SetTrebleBass(unsigned char *buf,int iChannel,int mTreble,int mBass,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x23;
	buf[13] = mTreble<<4|mBass;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}

int Encode_SetMuteMode(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x24;
	buf[13] = m_flag;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}

int Encode_GetCurrentSongInfo(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 0x0c;
	buf[11] = iChannel;
	buf[12] = 0x30;
	buf[13] = 0x00;
	buf[14] = 0xE1;
	buf[15] = 0x07;
	//abc.mp3 61 62 63 2E 6D 70 33 
	buf[16] = 0x61;
	buf[17] = 0x62;
	buf[18] = 0x63;
	buf[19] = 0x2E;
	buf[20] = 0x6D;
	buf[21] = 0x70;
	buf[22] = 0x33;

	buf[23] = CalcPFKcrc8r((unsigned char*)buf, 23); 
 
	return 24;
}
int Encode_SetPauseResumePlay(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x31;
	buf[13] = m_flag;
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}
int Encode_SetUpDownSong(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{

	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x32;
	buf[13] = 1;//默认成功
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}
int Encode_SetPlayMode(unsigned char *buf,int iChannel,int m_flag,int mId,int mSeqData)
{	
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x33;
	buf[13] = m_flag;//默认成功
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;

}

int Encode_SetPlayTime(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x34;
	buf[13] = 1;//默认成功
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}
int Encode_ChooseSong(unsigned char *buf,int iChannel,int mCatalog,int mSong,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x42;
	buf[13] = 1;//默认成功
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}
int Encode_BrowseCatalogInfo1(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 10;
	buf[11] = iChannel;
	buf[12] = 0x40;
	buf[13] = 3;
	buf[14] = 1;
	//part1 70 61 72 74 31 
	buf[15] = 0x70;
	buf[16] = 0x61;
	buf[17] = 0x72;
	buf[18] = 0x74;
	buf[19] = 0x31;
	buf[20] = 0x00;
	buf[21] = CalcPFKcrc8r((unsigned char*)buf, 21); 

	return 22;

}
int Encode_BrowseCatalogInfo2(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 10;
	buf[11] = iChannel;
	buf[12] = 0x40;
	buf[13] = 3;
	buf[14] = 2;
	//part2 70 61 72 74 32 
	buf[15] = 0x70;
	buf[16] = 0x61;
	buf[17] = 0x72;
	buf[18] = 0x74;
	buf[19] = 0x32;
	buf[20] = 0x00;
	buf[21] = CalcPFKcrc8r((unsigned char*)buf, 21); 

	return 22;
}
int Encode_BrowseCatalogInfo3(unsigned char *buf,int iChannel,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 10;
	buf[11] = iChannel;
	buf[12] = 0x40;
	buf[13] = 3;
	buf[14] = 3;
	//part3 70 61 72 74 33 
	buf[15] = 0x70;
	buf[16] = 0x61;
	buf[17] = 0x72;
	buf[18] = 0x74;
	buf[19] = 0x33;
	buf[20] = 0x00;
	buf[21] = CalcPFKcrc8r((unsigned char*)buf, 21); 

	return 22;
}
int Encode_BrowseSongInfo_a(unsigned char *buf,int iChannel,int mCatalog,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 12;
	buf[11] = iChannel;
	buf[12] = 0x41;
	buf[13] = 3;
	buf[14] = 1;
	buf[15] = 3;
	buf[16] = 1;


	//a.mp3 61 2E 6D 70 33 00
	buf[17] = 0x61;
	buf[18] = 0x2E;
	buf[19] = 0x6D;
	buf[20] = 0x70;
	buf[21] = 0x33;
	buf[22] = 0x00;
	buf[23] = CalcPFKcrc8r((unsigned char*)buf, 23); 
	return 24;
}
int Encode_BrowseSongInfo_b(unsigned char *buf,int iChannel,int mCatalog,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 12;
	buf[11] = iChannel;
	buf[12] = 0x41;
	buf[13] = 3;
	buf[14] = 1;
	buf[15] = 3;
	buf[16] = 2;


	//a.mp3 61 2E 6D 70 33 00
	buf[17] = 0x62;
	buf[18] = 0x2E;
	buf[19] = 0x6D;
	buf[20] = 0x70;
	buf[21] = 0x33;
	buf[22] = 0x00;
	buf[23] = CalcPFKcrc8r((unsigned char*)buf, 23); 
	return 24;
}
int Encode_BrowseSongInfo_c(unsigned char *buf,int iChannel,int mCatalog,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 12;
	buf[11] = iChannel;
	buf[12] = 0x41;
	buf[13] = 3;
	buf[14] = 1;
	buf[15] = 3;
	buf[16] = 3;


	//a.mp3 61 2E 6D 70 33 00
	buf[17] = 0x63;
	buf[18] = 0x2E;
	buf[19] = 0x6D;
	buf[20] = 0x70;
	buf[21] = 0x33;
	buf[22] = 0x00;
	buf[23] = CalcPFKcrc8r((unsigned char*)buf, 23); 
	return 24;
}

int Encode_QueryAlarmZoneInfo(unsigned char *buf,int mId,int mSeqData)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 18;
	buf[11] = 0;
	buf[12] = 0x51;
 
	for(int i = 0 ; i <mAlarmZone; i++)
	{
		buf[13+i] =0x01;
	}
	buf[13+mAlarmZone] =CalcPFKcrc8r((unsigned char*)buf, 13+mAlarmZone); 


	return 14+mAlarmZone;
}

int	Encode_AlarmAppend(unsigned char *buf,int iChannel,int mId,int mSeqData,int flag)
{
	buf[0] = 0xae;
	buf[1] = 0xea;
	buf[2] = (mId&0xff000000)>>24;
	buf[3] = (mId&0x00ff0000)>>16;
	buf[4] = (mId&0x0000ff00)>>8;
	buf[5] = (mId&0x000000ff) ; 
	buf[6] = (mSeqData&0xff000000)>>24;
	buf[7] = (mSeqData&0x00ff0000)>>16;
	buf[8] = (mSeqData&0x0000ff00)>>8;
	buf[9] = (mSeqData&0x000000ff) ;
	buf[10] = 3;
	buf[11] = iChannel;
	buf[12] = 0x52;
	buf[13] = flag;//默认成功
	buf[14] = CalcPFKcrc8r((unsigned char*)buf, 14); 

	return 15;
}

bool MusicWorkStart()
{

	memset(BgmState,0,16*sizeof(SHBACKGROUNDMUSIC_STAT));
	m_bAuthentication = false;
	//printf("/*******************************************************************/\n");
	//printf("   Do You Need do Twice Authentication?(Y/N)                           \n");
	//printf("/*******************************************************************/\n");

	//char c;
	//scanf("%c",&c);

	//switch (c)
	//{
	//case 'y':
	//case 'Y':
	//	m_bAuthentication =true;
	//	printf("/*******************you choose Complicated Authentication!***************/\n");
	//	break;
	//default :m_bAuthentication =false;
	//	printf("/******************you choose Simple Authentication!*********************/\n");
	//	break;
	//}

	//printf("/*****************************Music Server Work Start**************/\n");

	//printf("/*******************************************************************/\n");
	//printf("   How many alarmzone is here?                            \n");
	//printf("/*******************************************************************/\n");

	//char d[10];
	//scanf("%s",d); 
	//mAlarmZone =atoi(d);
	mAlarmZone=0;

	if ( !StartListen() )
	{
		my_printf(PRINT_NORMAL,"StartListen() failed.please check listen is in use");
		return false;
	}

	if (!DH_BeginThread((void*)ThreadProc,NULL)) 
	{
		my_printf(PRINT_NORMAL,"DH_BeginThread() failed.");
		return false;
	}


 
	return true ;

}

bool MusicWorkEnd()
{
	my_printf(PRINT_NORMAL,"/***************Music Server Work end*************/");


	return true ;

}