package com.iconmaster.aec;

import java.io.File;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;

import com.iconmaster.aec.aether.AVRegistry;
import com.iconmaster.aec.block.BlockAetherCondenser;
import com.iconmaster.aec.block.BlockAetherConduit;
import com.iconmaster.aec.block.BlockAetherContainer;
import com.iconmaster.aec.block.BlockAetherExtractor;
import com.iconmaster.aec.block.BlockAetherInfuser;
import com.iconmaster.aec.block.BlockAetherManipulator;
import com.iconmaster.aec.block.BlockAetherReconstructor;
import com.iconmaster.aec.block.BlockInfused;
import com.iconmaster.aec.client.ClientProxy;
import com.iconmaster.aec.command.ConfigCommand;
import com.iconmaster.aec.item.ItemAetherAxe;
import com.iconmaster.aec.item.ItemAetherBattery;
import com.iconmaster.aec.item.ItemAetherHammer;
import com.iconmaster.aec.item.ItemAetherPickaxe;
import com.iconmaster.aec.item.ItemAetherShears;
import com.iconmaster.aec.item.ItemAetherShovel;
import com.iconmaster.aec.item.ItemAetherSword;
import com.iconmaster.aec.item.ItemDummy;
import com.iconmaster.aec.item.ItemFlyingRing;
import com.iconmaster.aec.item.ItemInfused;
import com.iconmaster.aec.item.ItemRepairRing;
import com.iconmaster.aec.network.AetherCraftPacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = AetherCraft.MODID, version = AetherCraft.VERSION)
//TODO: add packet handler init here
//@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = {
//		"Aec", "AecReq", "AecTrans" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = {
//		"Aec", "AecReq", "AecTrans" }, packetHandler = ServerPacketHandler.class))
public class AetherCraft {
	
    public static final String MODID = "AetherCraft";
    public static final String VERSION = "@VERSION@";

	public static final String DEFAULT_CONFIG_FILE = "default.cfg";
	public static final int GUI_ID_MANIPULATOR = 0;
	public static final int GUI_ID_CONTAINER = 1;
	public static final int GUI_ID_EXTRACTOR = 2;
	public static final int GUI_ID_CONDENSER = 3;
	public static final int GUI_ID_INFUSER = 4;
	public static final int GUI_ID_RECONSTRUCTOR = 5;
	
	public static final byte PACKET_TTID_CONFIG = 0;

	@Instance("AetherCraft")
	public static AetherCraft instance = new AetherCraft();

	@SidedProxy(clientSide = "com.iconmaster.aec.client.ClientProxy", serverSide = "com.iconmaster.aec.CommonProxy")
	public static CommonProxy proxy;

	public static Block blockAetherManipulator;
	public static Block blockAetherContainer;
	public static Block blockAetherConduit;
	public static Block blockAetherExtractor;
	public static Block blockAetherCondenser;
	public static Block blockAetherInfuser;
	public static Block blockAetherReconstructor;
	public static Block blockInfused;
	public static Item itemAetherBattery;
	public static Item itemFlyingRing;
	public static Item itemRepairRing;
	public static Item itemInfused;
	public static Item dummy;
	public static Item aetherPickaxe;
	public static Item aetherAxe;
	public static Item aetherShovel;
	public static Item aetherSword;
	public static Item aetherHammer;
	public static Item aetherShears;

	//static ArrayList<Integer> blockIds = new ArrayList<Integer>();
	//private static ArrayList<Integer> itemIds = new ArrayList<Integer>();

	private static HashMap<String, Float> currentConfigAV = new HashMap<String, Float>();
	private static HashMap<String, String> options = new HashMap<String, String>();

	private static File configDir;

	private static File forgeConfigFile;

	private static File currentAVConfig;
	
	public static final AetherCraftPacketHandler packetHandler = new AetherCraftPacketHandler();
	
	 public static CreativeTabs tabAetherCraft  = new CreativeTabs("aetherCraft") {
         public ItemStack getIconItemStack() {
                 return new ItemStack(blockAetherManipulator, 1, 0);
         }

		@Override
		public Item getTabIconItem() {
			return null;
		}
	 };
	 
	 public static ToolMaterial aetherMaterial = EnumHelper.addToolMaterial("aether", 3, 1250, 6.0F, 2.0F, 18);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//configDir = event.getSuggestedConfigurationFile().getParentFile();
		configDir = new File(event.getModConfigurationDirectory(), "aec-values/");
		getConfigDir().mkdir();
		forgeConfigFile = event.getSuggestedConfigurationFile();

		this.reloadConfigFiles();
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
		// Blocks
		blockAetherManipulator  = new BlockAetherManipulator(
				Material.rock,"Manipulator").setLightLevel(0.3f);
		blockAetherContainer  = new BlockAetherContainer(
				Material.rock,"Container").setLightLevel(0.3f);
		blockAetherConduit  = new BlockAetherConduit(
				Material.rock).setLightLevel(0.2f);
		blockAetherExtractor = new BlockAetherExtractor(
				Material.rock,"Extractor").setLightLevel(0.3f);
		blockAetherCondenser = new BlockAetherCondenser(
				Material.rock,"Condenser").setLightLevel(0.3f);
		blockAetherInfuser = new BlockAetherInfuser(
				Material.rock,"Infuser").setLightLevel(0.3f);
		blockAetherReconstructor = new BlockAetherReconstructor(
				Material.rock,"Reconstructor").setLightLevel(0.3f);
		blockInfused = new BlockInfused(
				Material.rock).setLightLevel(0.7f);
		// Items
		itemAetherBattery = new ItemAetherBattery();
		itemInfused = new ItemInfused();
		dummy = new ItemDummy();
		
		//Tools
		aetherPickaxe = new ItemAetherPickaxe();
		aetherAxe = new ItemAetherAxe();
		aetherShovel = new ItemAetherShovel();
		aetherSword = new ItemAetherSword();
		aetherHammer = new ItemAetherHammer();
		aetherShears = new ItemAetherShears();

		// Rings
		if (Boolean.parseBoolean(AetherCraft.getOptions("enableflyring"))) {
			itemFlyingRing = new ItemFlyingRing();
		}
		itemRepairRing = new ItemRepairRing();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		
		//TODO: net handler here
		packetHandler.initialise();
		
		proxy.registerTiles();
		proxy.registerBlocks();
		proxy.addNames();
		proxy.addRecipes();
		proxy.registerHandlers();
		proxy.registerEventHandlers();
		ClientProxy.setCustomRenderers();
		LanguageRegistry.instance().addStringLocalization("itemGroup.aetherCraft", "en_US", "AetherCraft");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		packetHandler.postInitialise();
		
		//AVRegistry.reloadAllValues();
		
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		AVRegistry.reloadAllValues(); //Just to make sure some mods last-minute registered stuff gets added
		event.registerServerCommand(new ConfigCommand());
	}



	public static String getOptions(String option) {
		if (option != null && options.containsKey(option)) {
			return options.get(option);
		} else {
			return null;
		}
	}

	public static void reloadConfigFiles() {
		
		// ------------------- CONFIG -------------------
		Configuration forgeConfig = new Configuration(forgeConfigFile);
		forgeConfig.load();

		// ------------------- CONFIG OPTIONS -------------------

		// INTEGER AND DOUBLE
		options.put(
				"consumeprecision",
				Float.toString(forgeConfig
						.get("options",
								"consumeprecision",
								100,
								"How much of the AV an Aether Manipulator will consume (in percentage without the %, range 0-100)")
						.getInt()));

		options.put(
				"avmultiplier",
				Double.toString(forgeConfig
						.get("options",
								"avmultiplier",
								1.0,
								"Easily ramp up the AVs with this multiplier, added to compensate for consumeprecision changing values for stone and such to 0.")
						.getDouble(1.0)));

		options.put("ammaxstorage", Integer.toString(forgeConfig.get("options",
				"ammaxstorage", 32768,
				"How much aether an Aether Manipulator (and other machines) can store.").getInt()));

		options.put("acmaxstorage", Float.toString(forgeConfig.get("options",
				"acmaxstorage", 65536,
				"How much aether an Aether Container can store.").getInt()));

		options.put("abatterymaxstorage", Float.toString(forgeConfig.get(
				"options", "aabatterymaxstorage", 16384,
				"How much aether an Aether Cell can store.").getInt()));

		options.put("flycostpersecond", Float.toString(forgeConfig.get(
				"options", "flycostpersecond", 8,
				"How much AV the flying ring will drain per second.").getInt()));
		
		options.put("chargerate", Integer.toString(forgeConfig.get("options",
				"chargerate", 128,
				"How fast Aether Cells placed in an Aether Container charge.").getInt()));
		
		options.put("flowrate", Integer.toString(forgeConfig.get("options",
				"flowrate", 8192,
				"How much AV a basic Conduit can transfer maximum.").getInt()));
		
		options.put("toolcost", Float.toString((float)forgeConfig.get("options",
				"toolcost", 2F,
				"How much AV it costs for a basic Aether tool to break a block.").getDouble(2)));
		
		options.put("avlimit", Float.toString((float)forgeConfig.get("options",
				"avlimit", 8192F,
				"The transmutation limit for Tier 1 machines. Multiplies by 4 for each tier.").getDouble(8192)));

		// BOOLEAN
		options.put(
				"instantconsume",
				Boolean.toString(forgeConfig
						.get("options",
								"instantconsume",
								false,
								"Whether stacks will get transmuted/consumed instantly or not.")
						.getBoolean(false)));

		options.put(
				"enableflyring",
				Boolean.toString(forgeConfig.get("options", "enableflyring",
						true, "If false, there will be no Flying Ring.").getBoolean(true)));

		options.put(
				"showavalways",
				Boolean.toString(forgeConfig.get("options", "showavalways",
						false, "If true, the AV of objects will be shown even if the user is not holding shift.").getBoolean(false)));

//		// ------------------- REGISTER BLOCK/ITEM IDs -------------------
//		blockIds.add(forgeConfig.getBlock("aethermanipulator", 2690).getInt());
//		blockIds.add(forgeConfig.getBlock("aethercontainer", 2691).getInt());
//		blockIds.add(forgeConfig.getBlock("aetherconduit", 2692).getInt());
//		blockIds.add(forgeConfig.getBlock("aetherextractor", 2693).getInt());
//		blockIds.add(forgeConfig.getBlock("aethercondenser", 2694).getInt());
//		blockIds.add(forgeConfig.getBlock("aetherinfuser", 2695).getInt());
//		blockIds.add(forgeConfig.getBlock("infusedblock", 2696).getInt());
//		blockIds.add(forgeConfig.getBlock("aetherreconstructor", 2697).getInt());
//		itemIds.add(forgeConfig.getItem("aetherbattery", 2700).getInt());
//		itemIds.add(forgeConfig.getItem("flyingring", 2701).getInt());
//		itemIds.add(forgeConfig.getItem("infuseditem", 2702).getInt());
//		itemIds.add(forgeConfig.getItem("dummy", 2703).getInt());
//		itemIds.add(forgeConfig.getItem("aetherPickaxe", 2704).getInt());
//		itemIds.add(forgeConfig.getItem("aetherAxe", 2705).getInt());
//		itemIds.add(forgeConfig.getItem("aetherShovel", 2706).getInt());
//		itemIds.add(forgeConfig.getItem("aetherSword", 2707).getInt());
//		itemIds.add(forgeConfig.getItem("aetherHammer", 2708).getInt());
//		itemIds.add(forgeConfig.getItem("aetherShears", 2709).getInt());
//		itemIds.add(forgeConfig.getItem("repairRing", 2710).getInt());
		forgeConfig.save();
	}

	public static HashMap<String, String> getOptionsMap() {
		return options;
	}

	public static void setOptionsMap(HashMap<String, String> om) {
		options = om;
	}

	public static File getConfigDir() {
		return configDir;
	}
}