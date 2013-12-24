package com.iconmaster.aec.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
/*
 * \t\t//[^\n]+\n
 */
public class DefaultAetherValuesConfig {
	public static void createDefaultEvConfigFile(File configFile) {
		String s = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		sb.append("tile.web=64" + s);
		sb.append("tile.gravel=8" + s);
		sb.append("tile.pumpkin=16" + s);
		sb.append("tile.sapling.oak=48" + s);
		sb.append("enchantment_21=1024" + s);
		sb.append("enchantment_20=1024" + s);
		sb.append("tile.hellsand=1" + s);
		sb.append("item.minecartHopper=2592" + s);
		sb.append("item.minecartTnt=1605" + s);
		sb.append("tile.stoneMoss=16" + s);
		sb.append("item.potatoPoisonous=32" + s);
		sb.append("item.book=48" + s);
		sb.append("tile.leaves.spruce=1" + s);
		sb.append("tile.stonebricksmooth.default=1" + s);
		sb.append("item.skull.char=8192" + s);
		sb.append("enchantment_32=1024" + s);
		sb.append("tile.flower=16" + s);
		sb.append("tile.stonebricksmooth.mossy=1" + s);
		sb.append("enchantment_35=1024" + s);
		sb.append("enchantment_34=1024" + s);
		sb.append("enchantment_33=1024" + s);
		sb.append("item.redstone=16" + s);
		sb.append("tile.log.jungle=16" + s);
		sb.append("tile.deadbush=16" + s);
		sb.append("tile.sandStone.chiseled=4" + s);
		sb.append("item.bone=160" + s);
		sb.append("item.dyePowder.brown=32" + s);
		sb.append("item.minecartFurnace=1288" + s);
		sb.append("item.flint=8" + s);
		sb.append("item.potato=32" + s);
		sb.append("tile.obsidian=64" + s);
		sb.append("item.chickenRaw=64" + s);
		sb.append("enchantment_18=1024" + s);
		sb.append("tile.stairsStone=1" + s);
		sb.append("enchantment_17=1024" + s);
		sb.append("enchantment_16=1024" + s);
		sb.append("item.clay=8" + s);
		sb.append("tile.quartzBlock.chiseled=128" + s);
		sb.append("enchantment_19=1024" + s);
		sb.append("tile.dirt=1" + s);
		sb.append("tile.sapling.jungle=48" + s);
		sb.append("item.reeds=16" + s);
		sb.append("item.string=12" + s);
		sb.append("item.spiderEye=64" + s);
		sb.append("tile.mushroom=32" + s);
		sb.append("item.yellowDust=128" + s);
		sb.append("tile.clay=32" + s);
		sb.append("item.helmetChain=360" + s);
		sb.append("tile.sandStone.smooth=4" + s);
		sb.append("tile.tallgrass.fern=16" + s);
		sb.append("item.dyePowder.black=32" + s);
		sb.append("item.leather=64" + s);
		sb.append("enchantment_48=1024" + s);
		sb.append("enchantment_49=1024" + s);
		sb.append("item.skull.creeper=8192" + s);
		sb.append("tile.grass=1" + s);
		sb.append("item.rottenFlesh=64" + s);
		sb.append("item.feather=64" + s);
		sb.append("tile.log.spruce=16" + s);
		sb.append("item.seeds=8" + s);
		sb.append("tile.quartzBlock.default=128" + s);
		sb.append("item.carrots=32" + s);
		sb.append("item.blazeRod=320" + s);
		sb.append("item.skull.zombie=8192" + s);
		sb.append("enchantment_51=1024" + s);
		sb.append("item.slimeball=64" + s);
		sb.append("enchantment_50=1024" + s);
		sb.append("item.leggingsChain=480" + s);
		sb.append("item.diamond=8192" + s);
		sb.append("item.skull.skeleton=8192" + s);
		sb.append("tile.vine=1" + s);
		sb.append("tile.sponge=1" + s);
		sb.append("item.ingotIron=256" + s);
		sb.append("item.ingotGold=4096" + s);
		sb.append("item.saddle=256" + s);
		sb.append("item.coal=128" + s);
		sb.append("item.melon=16" + s);
		sb.append("tile.melon=576" + s);
		sb.append("tile.mycel=1" + s);
		sb.append("tile.stonebricksmooth.chiseled=1" + s);
		sb.append("item.wheat=16" + s);
		sb.append("tile.hellrock=1" + s);
		sb.append("tile.tallgrass.grass=16" + s);
		sb.append("item.snowball=1" + s);
		sb.append("tile.netherStalk=128" + s);
		sb.append("tile.leaves.jungle=1" + s);
		sb.append("item.bootsChain=300" + s);
		sb.append("item.beefRaw=64" + s);
		sb.append("item.hatchetStone=7" + s);
		sb.append("item.egg=64" + s);
		sb.append("item.record=8192" + s);
		sb.append("tile.stone=1" + s);
		sb.append("tile.stonebrick=1" + s);
		sb.append("item.porkchopRaw=64" + s);
		sb.append("tile.lightgem=512" + s);
		sb.append("item.emerald=16384" + s);
		sb.append("tile.sand=1" + s);
		sb.append("item.seeds_melon=64" + s);
		sb.append("tile.sandStone.default=4" + s);
		sb.append("item.sulphur=64" + s);
		sb.append("tile.sapling.spruce=48" + s);
		sb.append("tile.log.oak=16" + s);
		sb.append("tile.dragonEgg=1024000" + s);
		sb.append("item.enderPearl=1024" + s);
		sb.append("item.dyePowder.blue=128" + s);
		sb.append("tile.stonebricksmooth.cracked=1" + s);
		sb.append("item.potatoBaked=32" + s);
		sb.append("item.netherStalkSeeds=64" + s);
		sb.append("tile.leaves.oak=1" + s);
		sb.append("tile.tallgrass.shrub=16" + s);
		sb.append("item.apple=64" + s);
		sb.append("item.netherStar=32768" + s);
		sb.append("item.ghastTear=64" + s);
		sb.append("tile.sapling.birch=48" + s);
		sb.append("tile.cactus=32" + s);
		sb.append("tile.leaves.birch=1" + s);
		sb.append("tile.quartzBlock.lines=128" + s);
		sb.append("enchantment_5=1024" + s);
		sb.append("enchantment_4=1024" + s);
		sb.append("enchantment_7=1024" + s);
		sb.append("item.bucketWater=257" + s);
		sb.append("enchantment_6=1024" + s);
		sb.append("enchantment_1=1024" + s);
		sb.append("enchantment_0=1024" + s);
		sb.append("tile.whiteStone=1" + s);
		sb.append("enchantment_3=1024" + s);
		sb.append("enchantment_2=1024" + s);
		sb.append("item.milk=776" + s);
		sb.append("tile.waterlily=1" + s);
		sb.append("item.speckledMelon=471" + s);
		sb.append("item.bucketLava=272" + s);
		sb.append("tile.glass=1" + s);
		sb.append("tile.log.birch=16" + s);
		sb.append("item.chestplateChain=540" + s);
		sb.append("tile.rose=16" + s);
		sb.append("item.skull.wither=8192" + s);
		sb.append("item.fishRaw=64" + s);
		sb.append("item.netherquartz=32" + s);
		sb.append("tile.ice=1" + s);

		try {
			PrintWriter pw = new PrintWriter(configFile);
			pw.print(sb.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
