
package ch.netquick.easysurvival.block;

import net.minecraft.block.material.Material;

@EasysurvivalModElements.ModElement.Tag
public class EasyEnchantTableBlock extends EasysurvivalModElements.ModElement {

	@ObjectHolder("easysurvival:easy_enchant_table")
	public static final Block block = null;

	public EasyEnchantTableBlock(EasysurvivalModElements instance) {
		super(instance, 47);

	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomBlock());
		elements.items
				.add(() -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(block.getRegistryName()));
	}

	public static class CustomBlock extends Block {

		public CustomBlock() {
			super(Block.Properties.create(Material.ROCK).sound(SoundType.GROUND).hardnessAndResistance(1f, 10f).setLightLevel(s -> 0));

			setRegistryName("easy_enchant_table");
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {

			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty())
				return dropsOriginal;
			return Collections.singletonList(new ItemStack(this, 1));
		}

	}

}
