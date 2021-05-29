
package ch.netquick.easysurvival.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.block.Blocks;

import ch.netquick.easysurvival.EasysurvivalModElements;

@EasysurvivalModElements.ModElement.Tag
public class EasySurvivalItemGroup extends EasysurvivalModElements.ModElement {
	public EasySurvivalItemGroup(EasysurvivalModElements instance) {
		super(instance, 119);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabeasy_survival") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(Blocks.JUNGLE_SAPLING, (int) (1));
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundImageName("item_search.png");
	}
	public static ItemGroup tab;
}
