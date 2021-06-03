
package ch.netquick.easysurvival.itemgroup;

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
