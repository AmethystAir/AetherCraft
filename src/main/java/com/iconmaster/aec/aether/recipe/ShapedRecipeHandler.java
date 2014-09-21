package com.iconmaster.aec.aether.recipe;

import com.iconmaster.aec.aether.DynamicAVRegister;
import com.iconmaster.aec.util.UidUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShapedRecipeHandler implements IDynamicAVRecipeHandler {

	@Override
	public ArrayList getInputs(Object recipe) {
		for (ItemStack item : ((ShapedRecipes)recipe).recipeItems) {
			if (item!=null)
				item.stackSize = 1;
		}
		return new ArrayList(Arrays.asList(((ShapedRecipes)recipe).recipeItems));
	}

	@Override
	public ItemStack getOutput(Object recipe) {
		return ((ShapedRecipes)recipe).getRecipeOutput();
	}

	@Override
	public void populateRecipeList(HashMap recipeList) {
		for (Object recipe : CraftingManager.getInstance().getRecipeList())
		{
			try
			{
				if (DynamicAVRegister.isValidRecipe(recipe)) {
					ItemStack output = DynamicAVRegister.getOutput(recipe);
					List uid = UidUtils.getUID(output);
					if (recipeList.get(uid) == null) {
						recipeList.put(uid, new ArrayList());
					}
					((ArrayList) recipeList.get(uid)).add(recipe);
				} else {
					System.out.println("[AEC] Found new recipe class: "+recipe.getClass());
				}
			} catch (NullPointerException e) {
				//System.out.println("Invalid recipe state!");
				//avoiding crashes with MCPC+ since 2013!
			}
		}
	}
}
