package server.model.npcs;

import server.Server;
import server.util.Misc;
import server.util.Stream;

public class NPC {
	public int npcId;
	public int npcType;
	public int absX, absY;
	public int heightLevel;
	public int makeX, makeY, maxHit, defence, attack, moveX, moveY, direction, walkingType;
	public int spawnX, spawnY;
    public int viewX, viewY;
	public boolean transformUpdateRequired = false;
public int transformId;
	/**
	 * attackType: 0 = melee, 1 = range, 2 = mage
	 */
	public int attackType, projectileId, endGfx, endGfx0, spawnedBy, hitDelayTimer, HP, MaxHP, hitDiff, animNumber, actionTimer, enemyX, enemyY;
	public boolean applyDead, isDead, needRespawn, respawns;
	public boolean walkingHome, underAttack;
	public int freezeTimer, attackTimer, killerId, killedBy, oldIndex, underAttackBy, tickRevs;
	public long lastDamageTaken;
	public boolean randomWalk;
	public boolean dirUpdateRequired;
	public boolean forcedDeath;
	public boolean animUpdateRequired;
	public boolean hitUpdateRequired;
	public boolean updateRequired;
	public boolean forcedChatRequired;
    public boolean faceToUpdateRequired;
    public boolean isInstanced;
	public int firstAttacker;
	public String forcedText;
	
	
	public NPC(int _npcId, int _npcType) {
		npcId = _npcId;
		npcType = _npcType;
		direction = -1;
		isDead = false;
		applyDead = false;
		actionTimer = 0;
		randomWalk = true;		
		isInstanced = false;
	}
	
	public void appendTransformUpdate(Stream str) {
        	str.writeWordBigEndianA(transformId);
    	}
		
	public void requestTransform(int Id) {
        	transformId = Id;
        	transformUpdateRequired = true;
        	updateRequired = true;
    	}
	
	public void updateNPCMovement(Stream str) {
		if (direction == -1) {
			
			if (updateRequired) {
				
				str.writeBits(1, 1);
				str.writeBits(2, 0);
			} else {
				str.writeBits(1, 0);
			}
		} else {
			
			str.writeBits(1, 1);
			str.writeBits(2, 1);		
			str.writeBits(3, Misc.xlateDirectionToClient[direction]);
			if (updateRequired) {
				str.writeBits(1, 1);		
			} else {
				str.writeBits(1, 0);
			}
		}
	}

	/**
	* Text update
	**/
	
	public void forceChat(String text) {
		forcedText = text;
		forcedChatRequired = true;
		updateRequired = true;
	}
	
	/**
	*Graphics
	**/	
	
	public int mask80var1 = 0;
    public int mask80var2 = 0;
    protected boolean mask80update = false;
	
    public void appendMask80Update(Stream str) {
		str.writeWord(mask80var1);
	    str.writeDWord(mask80var2);
    }
	
	public void gfx100(int gfx){
		mask80var1 = gfx;
        mask80var2 = 6553600;
        mask80update = true;
		updateRequired = true;
	}
	
	public void gfx0(int gfx){
		mask80var1 = gfx;
        mask80var2 = 65536;
        mask80update = true;
		updateRequired = true;
	}
	
	public void appendAnimUpdate(Stream str) {
		str.writeWordBigEndian(animNumber);
		str.writeByte(1);
	}
	
	/**
	*
	Face
	*
	**/
	
	public int FocusPointX = -1, FocusPointY = -1;
	public int face = 0;
	
	private void appendSetFocusDestination(Stream str) {
        str.writeWordBigEndian(FocusPointX);
        str.writeWordBigEndian(FocusPointY);
    }
	
	public void turnNpc(int i, int j) {
        FocusPointX = 2 * i + 1;
        FocusPointY = 2 * j + 1;
        updateRequired = true;

    }
	
	public void appendFaceEntity(Stream str) {
		str.writeWord(face);
	}
        	
	public void facePlayer(int player) {
		face = player + 32768;
		dirUpdateRequired = true;
		updateRequired = true;
	}

	public void appendFaceToUpdate(Stream str) {
			str.writeWordBigEndian(viewX);
			str.writeWordBigEndian(viewY);
	}
	
	
	public void appendNPCUpdateBlock(Stream str) {
        if(!updateRequired) return ;        
        int updateMask = 0;
        if(animUpdateRequired) updateMask |= 0x10; 
        if(hitUpdateRequired2) updateMask |= 8;
        if(mask80update) updateMask |= 0x80;
        if(dirUpdateRequired) updateMask |= 0x20;
        if(forcedChatRequired) updateMask |= 1;
        if(hitUpdateRequired) updateMask |= 0x40; 
        if (transformUpdateRequired) updateMask |= 2;
        if(FocusPointX != -1) updateMask |= 4;        
            
        str.writeByte(updateMask);
                
        if (animUpdateRequired) appendAnimUpdate(str);
        if (hitUpdateRequired2) appendHitUpdate2(str);
        if (mask80update)       appendMask80Update(str);
        if (dirUpdateRequired)  appendFaceEntity(str);
        if(forcedChatRequired) {
            str.writeString(forcedText);
        }
        if (hitUpdateRequired)	appendHitUpdate(str);
        if (transformUpdateRequired)	appendTransformUpdate(str);
        if(FocusPointX != -1) appendSetFocusDestination(str);
        
    }

	public void clearUpdateFlags() {
        updateRequired = false;
        forcedChatRequired = false;
        hitUpdateRequired = false;
        hitUpdateRequired2 = false;
        animUpdateRequired = false;
        dirUpdateRequired = false;
        transformUpdateRequired = false;
        mask80update = false;
        forcedText = null;
        moveX = 0;
        moveY = 0;
        direction = -1;
        FocusPointX = -1;
        FocusPointY = -1;
    }

	
	public int getNextWalkingDirection() {
		int dir;
		dir = Misc.direction(absX, absY, (absX + moveX), (absY + moveY));
		if(dir == -1) return -1;
		dir >>= 1;
		absX += moveX;
		absY += moveY;
		return dir;
	}

	public void getNextNPCMovement(int i) {
		direction = -1;
		if(Server.npcHandler.npcs[i].freezeTimer == 0) {
			direction = getNextWalkingDirection();
		}
	}


	public void appendHitUpdate(Stream str) {		
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteC(hitDiff); 
		if (hitDiff > 0) {
			str.writeByteS(1); 
		} else {
			str.writeByteS(0); 
		}	
		str.writeByteS(Misc.getCurrentHP(HP, MaxHP, 100));
		str.writeByteC(100);
	}
	
	public int hitDiff2 = 0;
	public boolean hitUpdateRequired2 = false;
	
	public void appendHitUpdate2(Stream str) {		
		if (HP <= 0) {
			isDead = true;
		}
		str.writeByteA(hitDiff2); 
		if (hitDiff2 > 0) {
			str.writeByteC(1); 
		} else {
			str.writeByteC(0); 
		}	
		str.writeByteA(HP); 
		str.writeByte(MaxHP); 	
	}
	
	public void handleHitMask(int damage) {
		if (!hitUpdateRequired) {
			hitUpdateRequired = true;
			hitDiff = damage;
		} else if (!hitUpdateRequired2) {
			hitUpdateRequired2 = true;
			hitDiff2 = damage;		
		}
		updateRequired = true;
	}
	
	public int getX() {
		return absX;
	}
	
	public int getY() {
		return absY;
	}
	
	public boolean inMulti() {
		if((absX >= 2048 && absX <= 2110 && absY >= 3136 && absY <= 3236) ||
			(absX >= 3136 && absX <= 3327 && absY >= 3519 && absY <= 3607) || 
			(absX >= 3190 && absX <= 3327 && absY >= 3648 && absY <= 3839) ||  
			(absX >= 3200 && absX <= 3390 && absY >= 3840 && absY <= 3967) || 
			(absX >= 2992 && absX <= 3007 && absY >= 3912 && absY <= 3967) || 
			(absX >= 2824 && absX <= 2842 && absY >= 5296 && absY <= 5308) ||
			(absX >= 2770 && absX <= 2820 && absY >= 4705 && absY <= 4740) ||
		(absX >= 2918 && absX <= 2936 && absY >= 5318 && absY <= 5331) ||
		(absX >= 2864 && absX <= 2876 && absY >= 5351 && absY <= 5369) ||
		(absX >= 2889 && absX <= 2907 && absY >= 5258 && absY <= 5276) ||
			(absX >= 2946 && absX <= 2959 && absY >= 3816 && absY <= 3831) || 
			(absX >= 3008 && absX <= 3199 && absY >= 3856 && absY <= 3903) || 
			(absX >= 3008 && absX <= 3071 && absY >= 3600 && absY <= 3711) || 
			(absX >= 3072 && absX <= 3327 && absY >= 3608 && absY <= 3647) ||
			(absX >= 2624 && absX <= 2690 && absY >= 2550 && absY <= 2619) ||
			(absX >= 2371 && absX <= 2422 && absY >= 5062 && absY <= 5117) ||
			(absX >= 2896 && absX <= 2927 && absY >= 3595 && absY <= 3630) ||
			(absX >= 2892 && absX <= 2932 && absY >= 4435 && absY <= 4464) ||
			(absX >= 2578 && absX <= 2607 && absY >= 3149 && absY <= 3175) ||
			(absX >= 2510 && absX <= 2536 && absY >= 4633 && absY <= 4660) ||
			(absX >= 2943 && absX <= 3071 && absY >= 3400 && absY <= 3500) ||//north falador multi
			(absX >= 2991 && absX <= 3010 && absY >= 3400 && absY <= 3415) ||//north falador multi 2
			(absX >= 3042 && absX <= 3059 && absY >= 3409 && absY <= 3422) ||//north falador multi 3
			(absX >= 2940 && absX <= 2958 && absY >= 3452 && absY <= 3470) ||//north falador multi 4
			(absX >= 2402 && absX <= 2424 && absY >= 4369 && absY <= 4384) ||//chaos ele donor
			(absX >= 2480 && absX <= 2490 && absY >= 4360 && absY <= 4390) ||//kbd donor
			(absX >= 2944 && absX <= 2970 && absY >= 9770 && absY <= 9798) ||//black knights
			(absX >= 2460 && absX <= 2500 && absY >= 4410 && absY <= 4475) ||//barrows dz
			(absX >= 2956 && absX <= 2995 && absY >= 3735 && absY <= 3766) ||//barrows wildy
			(absX >= 2780 && absX <= 2815 && absY >= 2770 && absY <= 2800) || //ape atoll
			(absX >= 2512 && absX <= 2550 && absY >= 4760 && absY <= 4795) || //broodoo brothers
			(absX >= 2645 && absX <= 2695 && absY >= 3700 && absY <= 3740) || //relekka
			(absX >= 2256 && absX <= 2287 && absY >= 4680 && absY <= 4711)) {
			return true;
		}
		return false;
	}
	
	
	public boolean inWild() {
		if(absX > 2941 && absX < 3392 && absY > 3518 && absY < 3966 ||
			absX > 2941 && absX < 3392 && absY > 9918 && absY < 10366) {	
			return true;
		}
		return false;
	}
}
