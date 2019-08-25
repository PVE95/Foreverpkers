package server.model.players;

import server.model.players.packets.*;
import server.util.Misc;

public class PacketHandler{

	private static PacketType packetId[] = new PacketType[256];
	
	static {
		
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[247] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[124] = u;
		packetId[199] = u;
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[16] = new ItemClick2();		
		packetId[75] = new ItemClick3();	
		packetId[122] = new ClickItem();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new Commands();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[139] = new Trade();
		packetId[39] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[74/*59*/] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new BankX2();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packetId[121] = cr;
		packetId[210] = cr;
		packetId[60] = new ClanChat();
	}

	public static final int validPackets[] = {
		1, 0, 0, 1, 1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 1, 0, 1, 1, 1, 0,  //10
		0, 1, 0, 0, 0, 1, 0, 0, 0, 0,   //20
		0, 0, 0, 0, 0, 1, 1, 0, 0, 1,  //30
		1, 1, 0, 1, 0, 1, 0, 0, 0, 0,  //40
		0, 0, 0, 1, 0, 0, 0, 1, 0, 0,  //50
		1, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		1, 0, 1, 1, 1, 1, 0, 1, 1, 1,   //70
		0, 0, 0, 0, 0, 1, 1, 1, 0, 0,  //80
		0, 0, 0, 0, 0, 1, 0, 0, 1, 0,  //90
		0, 1, 0, 1, 0, 0, 0, 0, 0, 0,  //100
		0, 0, 0, 0, 0, 0, 0, 1, 0, 0,  //110
		1, 1, 1, 0, 0, 0, 1, 0, 1, 1,   //120
		1, 1, 1, 1, 0, 1, 1, 0, 0, 1,   //130
		0, 0, 0, 0, 0, 1, 0, 0, 1, 0,   //140
		1, 0, 1, 1, 0, 1, 1, 0, 0, 0,  //150
		0, 0, 0, 0, 1, 1, 0, 0, 0, 0,  //160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   //170
		0, 1, 0, 1, 0, 1, 0, 1, 1, 1,  //180
		0, 0, 1, 0, 0, 0, 0, 0, 0, 0,  //190
		1, 0, 1, 0, 0, 0, 0, 0, 1, 0,  //200
		1, 0, 0, 0, 1, 1, 0, 0, 1, 0,   //210
		0, 0, 0, 0, 0, 0, 1, 0, 1, 0,   //220
		1, 0, 0, 0, 1, 0, 1, 1, 1, 0,   //230
		0, 1, 0, 0, 0, 0, 1, 0, 1, 1,  //240
		0, 0, 1, 1, 0, 0, 0            //250
	};

	/*public static int validPackets[] = { 0, 202, 121, 122, 248, 164, 98, 4, 132, 252,
			70, 236, 241, 103, 214, 41, 145, 117, 43, 129, 135, 208, 87, 210,
			192, 155, 17, 21, 72, 253, 53, 14, 101, 188, 215, 133, 74, 126, 95,
			73, 75, 128, 153, 139, 199, 237, 249, 131, 86, };*/

	public static void processPacket(Client c, int packetType, int packetSize) {	
		if(packetType == -1) {
			return;
		}
		if(validPackets[packetType] == 0){
			c.logout();
			return;
		}
		PacketType p = packetId[packetType];
		if(p != null) {
				try {
					p.processPacket(c, packetType, packetSize);//e
					} catch(Exception e) {
							e.printStackTrace();
					}
		} else {
			System.out.println("Unhandled packet type: "+packetType+ " - size: "+packetSize);
		}
	}
	

}