package server.model.items;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.ArrayList;

import server.Config;
import server.Server;
import server.model.items.GameItem;
import server.model.items.Item;
import server.util.Misc;
import server.model.players.*;
	
public class BookHandler {
	public final Book DROP_GUIDE = new DropGuide();
}